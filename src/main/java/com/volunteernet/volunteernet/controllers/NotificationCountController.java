package com.volunteernet.volunteernet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.volunteernet.volunteernet.services.IServices.INotificationCountService;
import com.volunteernet.volunteernet.util.handler.ok.ResponseHandlerOk;

@RestController
public class NotificationCountController {
    
    @Autowired
    private INotificationCountService notificationCountService;

    @GetMapping("/api/notifications-count/general-count")
    public ResponseEntity<Object> getGeneralCount() {
        int count = notificationCountService.getGeneralCount();
        return ResponseHandlerOk.generateResponse("Cantidad de notificaciones", HttpStatus.OK, count);
    }

    @GetMapping("/api/notifications-count/chat-count")
    public ResponseEntity<Object> getChatCount() {
        int chatCount = notificationCountService.getChatCount();
        return ResponseHandlerOk.generateResponse("Cantidad de chats", HttpStatus.OK, chatCount);
    }

    @PostMapping("/api/notifications-count/general-count/reset")
    public ResponseEntity<Object> resetGeneralCount() {
        notificationCountService.resetGeneralCount();
        return ResponseHandlerOk.generateResponse("Contador de notificaciones reseteado", HttpStatus.OK, null);
    }

    @PostMapping("/api/notifications-count/chat-count/reset")
    public ResponseEntity<Object> resetChatCount() {
        notificationCountService.resetChatCount();
        return ResponseHandlerOk.generateResponse("Contador de notificaciones de chat reseteado", HttpStatus.OK, null);
    }

    @PostMapping("/api/notifications-count/chat-count/increment")
    public void incrementChatCount() {
        notificationCountService.incrementChatCount();
    }
}
