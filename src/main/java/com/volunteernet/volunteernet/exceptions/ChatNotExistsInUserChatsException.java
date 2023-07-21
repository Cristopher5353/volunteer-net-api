package com.volunteernet.volunteernet.exceptions;

public class ChatNotExistsInUserChatsException extends RuntimeException{
    public ChatNotExistsInUserChatsException() {
        super("Este usuario no puede ingresar a esta sala de chat");
    }
}
