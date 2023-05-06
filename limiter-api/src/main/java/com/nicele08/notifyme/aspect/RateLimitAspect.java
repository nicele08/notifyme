package com.nicele08.notifyme.aspect;

import java.lang.reflect.Method;
import java.time.YearMonth;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nicele08.notifyme.entity.Client;
import com.nicele08.notifyme.entity.RequestLimit;
import com.nicele08.notifyme.exception.BadRequest;
import com.nicele08.notifyme.exception.NotFoundException;
import com.nicele08.notifyme.exception.TooManyRequestsException;
import com.nicele08.notifyme.repository.ClientRepository;
import com.nicele08.notifyme.service.RateLimitService;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
@Order(1)
public class RateLimitAspect {

    @Value("${max-requests-per-minute:5}")
    private int maxRequestsPerMinute;

    private final RedisTemplate<String, String> redisTemplate;
    private final ClientRepository clientRepository;
    private final RateLimitService rateLimitService;

    public RateLimitAspect(RedisTemplate<String, String> redisTemplate, ClientRepository clientRepository,
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
            throw new BadRequest("Missing client API Key in request parameters");
        }

        Optional<Client> optionalClient = clientRepository.findByApiKey(apiKey);

        // Check if the client exists
        if (!optionalClient.isPresent()) {
            throw new NotFoundException("Invalid API key");
        }

        Client client = optionalClient.get();

        RequestLimit requestLimit = rateLimitService.findOrCreateRequestLimit(client);

        String monthlyKey = "rate_monthly_limit:" + apiKey;

        Integer requestMonthlyCount = 0;

        String requestMonthlyCountStr = redisTemplate.opsForValue().get(monthlyKey);
        if (requestMonthlyCountStr != null) {
            requestMonthlyCount = Integer.parseInt(requestMonthlyCountStr);
        } else {
            // Reset the monthly count
            requestMonthlyCount = rateLimitService.totalMonthlyRequests(requestLimit);
        }

        int maxMonthlyRequests = requestLimit.getMonthlyRequestLimit().getMaxRequests();
        int year = YearMonth.now().getYear();
        int month = YearMonth.now().getMonthValue();
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();

        // Check if the client is monthly rate limited
        if (requestMonthlyCount >= maxMonthlyRequests) {
            throw new TooManyRequestsException("Monthly Time Rate limit exceeded for client API key: " + apiKey);
        }

        redisTemplate.opsForValue().set(monthlyKey, String.valueOf(requestMonthlyCount + 1), daysInMonth,
                TimeUnit.DAYS);

        String windowKey = "rate_window_limit:" + apiKey;

        Integer requestWindowCount = 0;

        String requestWindowCountStr = redisTemplate.opsForValue().get(windowKey);
        if (requestWindowCountStr != null) {
            requestWindowCount = Integer.parseInt(requestWindowCountStr);
        } else {
            // Reset the window count
            requestWindowCount = rateLimitService.countRequestsWithinTimeWindow(requestLimit);
        }

        int maxRequests = requestLimit.getMaxRequests();
        long windowSeconds = requestLimit.getTimeWindow().toSeconds();

        if (requestWindowCount >= maxRequests) {
            throw new TooManyRequestsException("Window Time Rate limit exceeded for client API key: " + apiKey);
        }

        redisTemplate.opsForValue().set(windowKey, String.valueOf(requestWindowCount + 1), windowSeconds,
                TimeUnit.SECONDS);

        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            redisTemplate.opsForValue().increment(windowKey, -1);
            redisTemplate.opsForValue().increment(monthlyKey, -1);
            throw e;
        }

        return result;
    }

    @Around("execution(* com.nicele08.notifyme.controller.*.*(..))")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String key = "request_limit:" + methodName;
        long period = 60; // period in seconds

        // get the number of requests made in the current period
        Integer count = 0;
        String countStr = redisTemplate.opsForValue().get(key);
        if (countStr != null) {
            count = Integer.parseInt(countStr);
        }

        // if the maximum number of requests has been reached, throw an error response
        if (count >= maxRequestsPerMinute) {
            throw new TooManyRequestsException("Maximum limit for requests reached, please try again later after 1 minute");
        }

        // increment the request count and update the expiration time
        redisTemplate.opsForValue().increment(key, 1);
        redisTemplate.expire(key, period, TimeUnit.SECONDS);

        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            redisTemplate.opsForValue().increment(key, -1);
            throw e;
        }

        // proceed with the method execution
        return result;
    }
}
