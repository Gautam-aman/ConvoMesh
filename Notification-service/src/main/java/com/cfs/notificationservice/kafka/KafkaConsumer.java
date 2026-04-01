package com.cfs.notificationservice.kafka;


import com.cfs.notificationservice.entity.ChatMessage;
import com.cfs.notificationservice.redis.RedisPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumer {


    private SimpMessagingTemplate messagingTemplate;

    private final RestTemplate restTemplate = new RestTemplate();

    private RedisPublisher redisPublisher;

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void consume(ChatMessage message) {

        System.out.println("Received: " + message.getContent());

        // Save to DB (chat-service)
        restTemplate.postForObject(
                "http://localhost:8082/chat/save",
                message,
                String.class
        );

        // Broadcast to clients
        messagingTemplate.convertAndSend(
                "/topic/messages",
                message
        );

        // publish to redis
        String json = new ObjectMapper().writeValueAsString(message);
        redisPublisher.publish(json);
    }
}
