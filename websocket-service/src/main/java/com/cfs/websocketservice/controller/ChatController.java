package com.cfs.websocketservice.controller;

import com.cfs.websocketservice.model.ChatMessage;
import com.cfs.websocketservice.service.KafkaProducer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final KafkaProducer kafkaProducer;

    public ChatController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @MessageMapping("/sendMessage")
    public void chatMessage(ChatMessage chatMessage) {
        kafkaProducer.sendMessage(chatMessage);
    }

}
