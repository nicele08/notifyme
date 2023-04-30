package com.nicele08.notifyme.aspect;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nicele08.notifyme.entity.Client;
import com.nicele08.notifyme.exception.RateLimitExceededException;
import com.nicele08.notifyme.repository.ClientRepository;
import com.nicele08.notifyme.service.RateLimitService;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
@Order(1)
public class RateLimitAspect {

    private final RedisTemplate<String, Integer> redisTemplate;
    private final ClientRepository clientRepository;
    private final RateLimitService rateLimitService;

    @Autowired
    public RateLimitAspect(RedisTemplate<String, Integer> redisTemplate, ClientRepository clientRepository,
            RateLimitService rateLimitService) {
        this.redisTemplate = redisTemplate;
        this.clientRepository = clientRepository;
        this.rateLimitService = rateLimitService;
    }

    @Around("@annotation(com.nicele08.notifyme.aspect.RateLimited)")
    public Object enforceRateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimited rateLimited = method.getAnnotation(RateLimited.class);
        String apiKeyArgName = rateLimited.apiKeyArgName();

        System.out.println("Rate limit aspect: " + apiKeyArgName);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String apiKey = request.getParameter(apiKeyArgName);
        System.out.println(apiKeyArgName + ": " + apiKey);

        if (StringUtils.isEmpty(apiKey)) {
            throw new IllegalArgumentException("Missing client API Key in request body");
        }

        Optional<Client> optionalClient = clientRepository.findByApiKey(apiKey);

        // Check if the client exists
        if (!optionalClient.isPresent()) {
            throw new IllegalArgumentException("Invalid API key");
        }

        Client client = optionalClient.get();

        // Check if the client is rate limited
        if (rateLimitService.isRateLimited(client)) {
            throw new RateLimitExceededException("Rate limit exceeded for client API key: " + apiKey);
        }

        String key = "rate_limit:" + apiKey;
        Integer requestCount = redisTemplate.opsForValue().get(key);

        if (requestCount == null) {
            requestCount = 0;
        }

        int maxRequests = client.getRequestLimit().getMaxRequests();
        long windowSeconds = client.getRequestLimit().getTimeWindow().toSeconds();

        if (requestCount >= maxRequests) {
            throw new RateLimitExceededException("Rate limit exceeded for client API key: " + apiKey);
        }

        redisTemplate.opsForValue().set(key, requestCount + 1, windowSeconds, TimeUnit.SECONDS);

        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            redisTemplate.opsForValue().increment(key, -1);
            throw e;
        }

        return result;
    }
}
