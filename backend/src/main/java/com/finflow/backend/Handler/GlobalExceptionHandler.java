package com.finflow.backend.Handler;


import jakarta.mail.MessagingException;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashSet;
import java.util.Set;

import static com.finflow.backend.Handler.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp){

        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setBusinessErrorCode(ACCOUNT_LOCKED.getCode());
        exceptionResponse.setBusinessErrorDescription(ACCOUNT_LOCKED.getDescription());
        exceptionResponse.setError(exp.getMessage());

        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(exceptionResponse);

    }






    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp){

        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setBusinessErrorCode(ACCOUNT_DISABLED.getCode());
        exceptionResponse.setBusinessErrorDescription(ACCOUNT_DISABLED.getDescription());
        exceptionResponse.setError(exp.getMessage());

        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(exceptionResponse);

    }




    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setBusinessErrorCode(BAD_CREDENTIALS.getCode());
        exceptionResponse.setBusinessErrorDescription(BAD_CREDENTIALS.getDescription());
        exceptionResponse.setError("Login and / or Password is incorrect");

        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(exceptionResponse);


    }





    @ExceptionHandler(MessagingException.class) //if we're not able to send emails
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setError(exp.getMessage());

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(exceptionResponse);


    }




    //this would be sent by the @valid in the post /authenticate method
    @ExceptionHandler(MethodArgumentNotValidException.class) //if user sends invalid data
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp) {

        Set<String> errors = new HashSet<>(); //set because email has @Notempty and @Notblank and error the message would be sent twice if not for sent

        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setValidationErrors(errors);

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(exceptionResponse);


    }






    @ExceptionHandler(Exception.class) //any type of exception not handled by my app
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        //good to do : log the exception
        exp.printStackTrace(); //to show in console
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setBusinessErrorDescription("Internal error, please contact admin");
        exceptionResponse.setError(exp.getMessage());

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(exceptionResponse);


    }


}

