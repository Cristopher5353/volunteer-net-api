package com.volunteernet.volunteernet.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.volunteernet.volunteernet.dto.message.MessageResponseDto;
import com.volunteernet.volunteernet.dto.message.SaveMessageDto;
import com.volunteernet.volunteernet.services.IServices.IMessageService;
import com.volunteernet.volunteernet.util.handler.ok.ResponseHandlerOk;

@RestController
public class MessageController {
    @Autowired
    private IMessageService messageService;

    @GetMapping("/api/chats/{chatId}/messages")
    public ResponseEntity<Object> getMessagesByChat(@PathVariable Integer chatId) {
        List<MessageResponseDto> messagesByChat = messageService.findAllMessagesByChat(chatId);
        return ResponseHandlerOk.generateResponse("Mensajes del chat", HttpStatus.OK, messagesByChat);
    }

    @PostMapping("/api/chats/{chatId}/messages")
    public ResponseEntity<Object> saveMessage(@PathVariable Integer chatId,
            @RequestBody SaveMessageDto saveMessageDto) {
        MessageResponseDto messageResponseDto = messageService.saveMessage(chatId, saveMessageDto);
        return ResponseHandlerOk.generateResponse("Mensaje registrado correctamente", HttpStatus.CREATED,
                messageResponseDto);
    }
}