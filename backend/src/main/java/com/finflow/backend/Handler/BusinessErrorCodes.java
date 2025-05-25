package com.finflow.backend.Handler;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum BusinessErrorCodes {

    NO_CODE(0,NOT_IMPLEMENTED,"No code"),
    INCORRECT_CURRENT_PASSWORD(800,BAD_REQUEST, "Current password is incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH(301,BAD_REQUEST, "New password does not match"),

    ACCOUNT_LOCKED(302 ,FORBIDDEN, "User account is locked"),
    ACCOUNT_DISABLED(303 ,FORBIDDEN, "User account is diabled"),
    BAD_CREDENTIALS(304 ,FORBIDDEN, "Login " +
            "and / or password is incorrect"),


    ;




    private int code;
    private String description;
    private HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
