package com.volunteernet.volunteernet.exceptions;

public class UserIsNotChatAdministrator extends RuntimeException{
    public UserIsNotChatAdministrator() {
        super("El usuario no es administrador del grupo");
    }     
}
