package com.volunteernet.volunteernet.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.volunteernet.volunteernet.dto.chat.ChatResponseDto;
import com.volunteernet.volunteernet.services.IServices.IChatService;
import com.volunteernet.volunteernet.util.handler.ok.ResponseHandlerOk;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ChatController {
    @Autowired
    private IChatService chatService;

    @GetMapping("/api/users/chats")
    public ResponseEntity<Object> getChatsByUser() {
        List<ChatResponseDto> chatsByUser = chatService.findChatsByUser();
        return ResponseHandlerOk.generateResponse("Chats de usuario", HttpStatus.OK, chatsByUser);
    }
}
