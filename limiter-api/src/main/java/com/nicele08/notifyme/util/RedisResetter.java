package com.nicele08.notifyme.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisResetter {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void resetRedis() {
        redisTemplate.execute((RedisCallback<String>) connection -> {
            connection.flushAll();
            return "OK";
        });
    }
}
