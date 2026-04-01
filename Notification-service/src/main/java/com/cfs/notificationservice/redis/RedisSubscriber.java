package com.cfs.notificationservice.redis;

import com.cfs.notificationservice.entity.ChatMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class RedisSubscriber {
    private SimpMessagingTemplate simpMessagingTemplate;

    public void onMessage(String messageJson) throws Exception{
        ChatMessage message = new ObjectMapper()
                .readValue(messageJson, ChatMessage.class);
        simpMessagingTemplate.convertAndSend("/topic/message", message);
    }

}
