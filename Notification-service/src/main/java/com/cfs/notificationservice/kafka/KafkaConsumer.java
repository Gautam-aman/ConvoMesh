package com.cfs.notificationservice.kafka;


import com.cfs.notificationservice.entity.ChatMessage;
import com.cfs.notificationservice.redis.RedisPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KafkaConsumer {

    private final RestTemplate restTemplate;
    private final RedisPublisher redisPublisher;
    private final ObjectMapper objectMapper;
    private final String chatServiceUrl;

    public KafkaConsumer(
            RedisPublisher redisPublisher,
            ObjectMapper objectMapper,
            @Value("${chat.service.url}") String chatServiceUrl
    ) {
        this.redisPublisher = redisPublisher;
        this.objectMapper = objectMapper;
        this.chatServiceUrl = chatServiceUrl;
        this.restTemplate = new RestTemplate();
    }

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void consume(ChatMessage message) {
        System.out.println("Received: " + message.getContent());

        restTemplate.postForObject(
                chatServiceUrl + "/chat/save",
                message,
                String.class
        );

        try {
            redisPublisher.publish(objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to publish message to Redis", e);
        }
    }
}
