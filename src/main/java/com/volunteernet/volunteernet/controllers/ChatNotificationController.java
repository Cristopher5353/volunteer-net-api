package com.volunteernet.volunteernet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.volunteernet.volunteernet.dto.chatNotification.ChatNotificationResetUnReadCountDto;
import com.volunteernet.volunteernet.services.IServices.IChatNotificationService;
import com.volunteernet.volunteernet.util.handler.ok.ResponseHandlerOk;

@RestController
public class ChatNotificationController {

    @Autowired
    private IChatNotificationService chatNotificationService;

    @PostMapping("/api/resetunreadcountchatnotification")
    public ResponseEntity<Object> resetUnreadCountChatNotification(@RequestBody ChatNotificationResetUnReadCountDto chatNotificationResetDto) {
        chatNotificationService.resetUnreadCountChatNotification(chatNotificationResetDto);
        return ResponseHandlerOk.generateResponse("Contador de mensajes no vistos por chat resetado", HttpStatus.OK, null);
    }
}
