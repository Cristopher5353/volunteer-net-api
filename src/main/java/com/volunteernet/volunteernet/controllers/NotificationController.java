package com.volunteernet.volunteernet.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.volunteernet.volunteernet.dto.notification.NotificationResponseDto;
import com.volunteernet.volunteernet.services.IServices.INotificationService;
import com.volunteernet.volunteernet.util.handler.ok.ResponseHandlerOk;

@RestController
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    @GetMapping("/api/users/notifications")
    public ResponseEntity<Object> getAllByUser() {
        List<NotificationResponseDto> notifications = notificationService.getAllByUser();
        return ResponseHandlerOk.generateResponse("Listado de notificaciones", HttpStatus.OK, notifications);
    }
}
