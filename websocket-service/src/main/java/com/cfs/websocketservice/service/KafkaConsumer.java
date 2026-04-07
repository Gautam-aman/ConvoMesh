package com.cfs.websocketservice.service;


import com.cfs.websocketservice.model.ChatMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KafkaConsumer {

    private final SimpMessagingTemplate messagingTemplate;
    private final RestTemplate restTemplate;
    private final String chatServiceUrl;

    public KafkaConsumer(
            SimpMessagingTemplate messagingTemplate,
            @Value("${chat.service.url}") String chatServiceUrl
    ) {
        this.messagingTemplate = messagingTemplate;
        this.chatServiceUrl = chatServiceUrl;
        this.restTemplate = new RestTemplate();
    }

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void consume(ChatMessage message) {
        restTemplate.postForObject(chatServiceUrl + "/chat/save", message, Object.class);

        messagingTemplate.convertAndSend(
                "/topic/room/" + message.getRoomId(),
                message
        );

        System.out.println("Saved message: " + message.getContent());


    }
}
