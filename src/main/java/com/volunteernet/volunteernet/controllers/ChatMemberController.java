package com.volunteernet.volunteernet.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import com.volunteernet.volunteernet.dto.chatMember.ChatMemberResponseDto;
import com.volunteernet.volunteernet.services.IServices.IChatMemberService;
import com.volunteernet.volunteernet.util.handler.ok.ResponseHandlerOk;

@RestController
public class ChatMemberController {

    @Autowired
    private IChatMemberService chatMemberService;

    @GetMapping("/api/chats-members/requests-by-user")
    public ResponseEntity<Object> getAllRequestsByUser() {
        List<ChatMemberResponseDto> requests = chatMemberService.getAllRequestsByUser();
        return ResponseHandlerOk.generateResponse("Listado de solicitudes para unirse a tu grupo de chat", HttpStatus.OK,
                requests);
    }

    @GetMapping("/api/chats-members/request-by-user/count")
    public ResponseEntity<Object> getRequestsCountByUser() {
        int requestsCount = chatMemberService.getRequestsCountByUser();
        return ResponseHandlerOk.generateResponse("Cantidad de solicitudes para unirse a tu grupo de chat", HttpStatus.OK,
                requestsCount);
    }

    @PostMapping("/api/users/{userId}/chats-members/join")
    public ResponseEntity<Object> userRequestToJoin(@PathVariable Integer userId) {
        chatMemberService.userRequestToJoin(userId);
        return ResponseHandlerOk.generateResponse("Solicitud para unirse al grupo voluntario enviado correctamente",
                HttpStatus.OK, null);
    }

    @PutMapping("/api/chats-members/{chatMemberId}/confirm-join")
    public ResponseEntity<Object> confirmUserJoin(@PathVariable Integer chatMemberId) {
        chatMemberService.confirmUserJoin(chatMemberId);
        return ResponseHandlerOk.generateResponse("Solicitud de usuario aprobado correctamente", HttpStatus.OK, null);
    }
}
