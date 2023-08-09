package com.volunteernet.volunteernet.exceptions;

public class ChatNotExistsException extends RuntimeException{
    public ChatNotExistsException() {
        super("Chat no encontrado");
    }    
}