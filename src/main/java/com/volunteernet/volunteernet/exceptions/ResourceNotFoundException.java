package com.volunteernet.volunteernet.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() {
        super("Recurso no encontrado");
    }  
}
