package com.volunteernet.volunteernet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.volunteernet.volunteernet.dto.chatNotification.ChatNotificationResetUnReadCountDto;
import com.volunteernet.volunteernet.services.IServices.INotificationCountService;
import com.volunteernet.volunteernet.util.handler.ok.ResponseHandlerOk;

@RestController
public class NotificationCountController {
    @Autowired
    private INotificationCountService notificationCountService;

    @GetMapping("/api/notificationscount")
    public ResponseEntity<Object> getNotificationCount() {
        int count = notificationCountService.getNotificationCount();
        return ResponseHandlerOk.generateResponse("Cantidad de notificaciones", HttpStatus.OK, count);
    }

    @GetMapping("/api/notificationschatcount")
    public ResponseEntity<Object> getNotificationChatCount() {
        int chatCount = notificationCountService.getNotificationChatCount();
        return ResponseHandlerOk.generateResponse("Cantidad de chats", HttpStatus.OK, chatCount);
    }

    @PostMapping("/api/resetnotificationcount")
    public ResponseEntity<Object> resetNotificationCount() {
        notificationCountService.resetNotificationCount();
        return ResponseHandlerOk.generateResponse("Contador de notificaciones reseteado", HttpStatus.OK, null);
    }

    @PostMapping("/api/resetnotificationchatcount")
    public ResponseEntity<Object> resetNotificationChatCount() {
        notificationCountService.resetNotificationChatCount();
        return ResponseHandlerOk.generateResponse("Contador de notificaciones de chat reseteado", HttpStatus.OK, null);
    }

    @PostMapping("/api/resetchatnotification")
    public ResponseEntity<Object> resetChatNotification(@RequestBody ChatNotificationResetUnReadCountDto chatNotificationResetDto) {
        notificationCountService.resetChatNotification(chatNotificationResetDto);
        return ResponseHandlerOk.generateResponse("Contador de mensajes no vistos por chat resetado", HttpStatus.OK, null);
    }

    @PostMapping("/api/incrementnotificationchatcount")
    public void incrementNotificationChatCount() {
        notificationCountService.incrementNotificationChatCount();
    }
}
