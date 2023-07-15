package com.volunteernet.volunteernet.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException() {
        super("El correo electronico ingresado ya esta registrado en nuestro sistema");
    }
}
