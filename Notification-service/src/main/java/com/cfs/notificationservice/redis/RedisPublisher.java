package com.cfs.notificationservice.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisPublisher {

    private StringRedisTemplate redisTemplate;

    private static final String CHANNEL = "chat-channel";

    public void publish(String message) {
        redisTemplate.convertAndSend(CHANNEL, message);
    }
}
