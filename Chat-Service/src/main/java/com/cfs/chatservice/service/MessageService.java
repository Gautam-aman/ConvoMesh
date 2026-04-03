package com.cfs.chatservice.service;


import com.cfs.chatservice.entity.Message;
import com.cfs.chatservice.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> findAllByRoomId(String roomId) {
        return messageRepository.findByRoomId(roomId);
    }

}
