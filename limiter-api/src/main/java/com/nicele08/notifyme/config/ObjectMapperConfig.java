package com.nicele08.notifyme.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nicele08.notifyme.serialization.DurationDeserializer;
import com.nicele08.notifyme.serialization.DurationSerializer;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Duration.class, new DurationSerializer());
        simpleModule.addDeserializer(Duration.class, new DurationDeserializer());
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

}
