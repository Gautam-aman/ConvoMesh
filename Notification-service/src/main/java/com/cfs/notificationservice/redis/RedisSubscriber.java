package com.cfs.notificationservice.redis;

import com.cfs.notificationservice.entity.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisSubscriber {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper;

    public RedisSubscriber(SimpMessagingTemplate simpMessagingTemplate, ObjectMapper objectMapper) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.objectMapper = objectMapper;
    }

    public void onMessage(String messageJson) throws Exception{
        ChatMessage message = objectMapper.readValue(messageJson, ChatMessage.class);
        simpMessagingTemplate.convertAndSend("/topic/room/" + message.getRoomId(),
                message);
    }

}
