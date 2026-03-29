package com.cfs.websocketservice.service;

import com.cfs.websocketservice.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private KafkaTemplate<String, ChatMessage> kafkaTemplate;
    public KafkaProducer(KafkaTemplate<String, ChatMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private static final String TOPIC = "chat-topic";

    public void sendMessage(ChatMessage message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
