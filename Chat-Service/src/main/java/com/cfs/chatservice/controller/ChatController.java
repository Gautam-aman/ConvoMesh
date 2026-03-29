package com.cfs.chatservice.controller;


import com.cfs.chatservice.entity.Message;
import com.cfs.chatservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/save")
    public Message save(@RequestBody Message msg) {
        return messageService.saveMessage(msg);
    }

    @GetMapping("/messages/{roomId}")
    public List<Message> getMessages(@PathVariable Long roomId) {
        return messageService.findAllByRoomId(roomId);
    }

}
