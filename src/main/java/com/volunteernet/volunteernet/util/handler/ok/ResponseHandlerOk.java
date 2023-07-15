package com.volunteernet.volunteernet.util.handler.ok;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandlerOk {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        CorrectResponse correctResponse = new CorrectResponse();
        correctResponse.setMessage(message);
        correctResponse.setStatus(status.value());
        correctResponse.setData(responseObj);

        return new ResponseEntity<Object>(correctResponse, status);
    }
}
