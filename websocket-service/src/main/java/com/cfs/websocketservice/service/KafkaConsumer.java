package com.cfs.websocketservice.service;


import com.cfs.websocketservice.model.ChatMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void consume(ChatMessage message) {
        System.out.println("Received: " + message.getContent());

        // later:
        // save to DB
        // broadcast via WebSocket
    }
}
