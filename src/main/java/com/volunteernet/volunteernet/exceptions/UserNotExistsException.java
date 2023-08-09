package com.volunteernet.volunteernet.exceptions;

public class UserNotExistsException extends RuntimeException{
    public UserNotExistsException() {
        super("Usuario no encontrado");
    }
}
