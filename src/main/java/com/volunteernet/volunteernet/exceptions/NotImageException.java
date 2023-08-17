package com.volunteernet.volunteernet.exceptions;

public class NotImageException extends RuntimeException {
    public NotImageException(String filename) {
        super("Formato de imagen no válido para el archivo " + filename);
    }  
}
