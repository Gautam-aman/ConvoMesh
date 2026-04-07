package com.cfs.notificationservice.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisPublisher {

    private final StringRedisTemplate redisTemplate;

    private static final String CHANNEL = "chat-channel";

    public RedisPublisher(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(String message) {
        redisTemplate.convertAndSend(CHANNEL, message);
    }
}
