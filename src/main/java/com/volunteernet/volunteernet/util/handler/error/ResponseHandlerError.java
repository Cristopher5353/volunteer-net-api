package com.volunteernet.volunteernet.util.handler.error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.volunteernet.volunteernet.exceptions.ChatNotExistsInUserChatsException;
import com.volunteernet.volunteernet.exceptions.EmailAlreadyExistsException;
import com.volunteernet.volunteernet.exceptions.FollowerAlreadyFollowToFollowing;
import com.volunteernet.volunteernet.exceptions.NotImageException;
import com.volunteernet.volunteernet.exceptions.ResourceNotFoundException;
import com.volunteernet.volunteernet.exceptions.UserIsNotChatAdministrator;
import com.volunteernet.volunteernet.exceptions.UserIsNotVolunteerGroupException;

@ControllerAdvice
public class ResponseHandlerError {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        ErrorResponse errorResponse = new ErrorResponse();

        List<String> errors = new ArrayList<String>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(null);
        errorResponse.setErrors(errors);

        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    protected ResponseEntity<Object> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(null);
        errorResponse.setErrors(Arrays.asList(ex.getMessage()));

        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChatNotExistsInUserChatsException.class)
    protected ResponseEntity<Object> handleChatNotExistsInUserChatsException(ChatNotExistsInUserChatsException ex,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrors(null);

        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FollowerAlreadyFollowToFollowing.class)
    protected ResponseEntity<Object> handleFollowerAlreadyFollowToFollowing(FollowerAlreadyFollowToFollowing ex,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrors(null);

        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrors(null);

        return new ResponseEntity<Object>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserIsNotVolunteerGroupException.class)
    protected ResponseEntity<Object> handleUserIsNotVolunteerGroupException(UserIsNotVolunteerGroupException ex,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrors(null);

        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIsNotChatAdministrator.class)
    protected ResponseEntity<Object> handleUserIsNotGroupAdministrator(UserIsNotChatAdministrator ex,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrors(null);

        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotImageException.class)
    protected ResponseEntity<Object> handleNotImageException(NotImageException ex,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(null);
        errorResponse.setErrors(Arrays.asList(ex.getMessage()));

        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}