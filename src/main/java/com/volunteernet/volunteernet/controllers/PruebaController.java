package com.volunteernet.volunteernet.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.volunteernet.volunteernet.util.handler.ok.ResponseHandlerOk;

@RestController
public class PruebaController {
    private final SimpMessagingTemplate messagingTemplate;

    public PruebaController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/notificaciones")
    public ResponseEntity<Object> getMessagesByChat() {
        String[] usuarios = {"admin"};

        for (String i : usuarios) {
            messagingTemplate.convertAndSendToUser(i, "/queue/messages", "notificacion a usuario" + i);
        }
        return ResponseHandlerOk.generateResponse("Notificaciones", HttpStatus.OK, null);
    }
}
