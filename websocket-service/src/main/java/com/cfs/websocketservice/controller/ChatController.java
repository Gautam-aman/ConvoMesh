package com.cfs.websocketservice.controller;

import com.cfs.websocketservice.model.ChatMessage;
import com.cfs.websocketservice.service.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @MessageMapping("/sendMessage")
    public void chatMessage(ChatMessage chatMessage) {
        kafkaProducer.sendMessage(chatMessage);
    }

}
