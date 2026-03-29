package com.cfs.websocketservice.service;


import com.cfs.websocketservice.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KafkaConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String CHAT_SERVICE_URL = "http://localhost:8082/chat/save";

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void consume(ChatMessage message) {
        // save to db
        restTemplate.postForObject(CHAT_SERVICE_URL, message, ChatMessage.class);

        messagingTemplate.convertAndSend("/topic/messages",
                message);

        System.out.println("Saved message: " + message.getContent());


    }
}
