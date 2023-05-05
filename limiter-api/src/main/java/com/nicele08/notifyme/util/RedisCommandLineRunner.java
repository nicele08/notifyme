package com.nicele08.notifyme.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RedisCommandLineRunner implements CommandLineRunner {

    @Autowired
    private RedisResetter redisResetter;

    @Override
    public void run(String... args) throws Exception {
        redisResetter.resetRedis();
    }
}

