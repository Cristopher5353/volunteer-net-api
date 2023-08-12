package com.volunteernet.volunteernet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.volunteernet.volunteernet.dto.user.UserResponseDto;
import com.volunteernet.volunteernet.dto.user.UserSaveDto;
import com.volunteernet.volunteernet.services.IServices.IUserService;
import com.volunteernet.volunteernet.util.handler.ok.ResponseHandlerOk;
import jakarta.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/api/users/save")
    public ResponseEntity<Object> saveUser(@Valid @RequestBody UserSaveDto userSaveDto) {
        userService.saveUser(userSaveDto);
        return ResponseHandlerOk.generateResponse("Usuario registrado correctamente", HttpStatus.CREATED, null);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        UserResponseDto userResponseDto = userService.findUserById(id);
        return ResponseHandlerOk.generateResponse("Perfil del usuario", HttpStatus.OK, userResponseDto);
    }

    @PostMapping("/api/users/{id}/follow")
    public ResponseEntity<Object> userFollow(@PathVariable Integer id) {
        userService.userFollow(id);
        return ResponseHandlerOk.generateResponse("Comenzar a seguir a usuario", HttpStatus.OK, null);
    }

    @PostMapping("/api/users/{id}/unfollow")
    public ResponseEntity<Object> userUnFollow(@PathVariable Integer id) {
        userService.userUnFollow(id);
        return ResponseHandlerOk.generateResponse("Se dejó de seguir al usuario", HttpStatus.OK, null);
    }

    @PostMapping("/api/users/{userId}/join")
    public ResponseEntity<Object> userRequestToJoinGroup(@PathVariable Integer userId) {
        userService.userRequestToJoinGroup(userId);
        return ResponseHandlerOk.generateResponse("Solicitud para unirse al grupo voluntario enviado correctamente", HttpStatus.OK, null);
    }

    @PostMapping("/api/users/connect")
    public void connectUser() {
        userService.connectUser();
    }

    @PostMapping("/api/users/disconnect")
    public void disconnectUser() {
        userService.disconnectUser();
    }
}
