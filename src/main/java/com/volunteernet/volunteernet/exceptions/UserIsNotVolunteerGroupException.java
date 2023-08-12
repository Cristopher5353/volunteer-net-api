package com.volunteernet.volunteernet.exceptions;

public class UserIsNotVolunteerGroupException extends RuntimeException{
    public UserIsNotVolunteerGroupException() {
        super("El usuario no es un grupo voluntario");
    }
}
