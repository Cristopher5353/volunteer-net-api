package com.volunteernet.volunteernet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.volunteernet.volunteernet.dto.user.UserSaveDto;
import com.volunteernet.volunteernet.services.IServices.IUserService;
import com.volunteernet.volunteernet.util.handler.ok.ResponseHandlerOk;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<Object> saveUser(@RequestBody UserSaveDto userSaveDto) {
        userService.saveUser(userSaveDto);
        return ResponseHandlerOk.generateResponse("Usuario registrado correctamente", HttpStatus.CREATED, null);
    }
}
