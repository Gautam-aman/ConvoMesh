package com.cfs.websocketservice.service;

import com.cfs.websocketservice.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    //@Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;
    private static final String TOPIC = "chat-topic";

    public void sendMessage(ChatMessage message) {
        kafkaTemplate.send(TOPIC, message);
    }

}
