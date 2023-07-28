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
import com.volunteernet.volunteernet.exceptions.RoleNotExistsException;

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

    @ExceptionHandler(RoleNotExistsException.class)
    protected ResponseEntity<Object> handleRoleNotExistsException(RoleNotExistsException ex, WebRequest request) {
            
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(null);
        errorResponse.setErrors(Arrays.asList(ex.getMessage()));

        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    protected ResponseEntity<Object> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, WebRequest request) {
            
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(null);
        errorResponse.setErrors(Arrays.asList(ex.getMessage()));

        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChatNotExistsInUserChatsException.class)
    protected ResponseEntity<Object> handleChatNotExistsInUserChatsException(ChatNotExistsInUserChatsException ex, WebRequest request) {
            
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrors(null);

        return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
