package com.volunteernet.volunteernet.exceptions;

public class RoleNotExistsException extends RuntimeException {
    public RoleNotExistsException() {
        super("El rol seleccionado no es válido");
    }
}
