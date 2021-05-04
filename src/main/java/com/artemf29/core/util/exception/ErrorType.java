package com.artemf29.core.util.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    APP_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),//500
    DATA_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY),//422
    DATA_ERROR(HttpStatus.CONFLICT), //409
    VALIDATION_ERROR(HttpStatus.UNPROCESSABLE_ENTITY),//422
    WRONG_REQUEST(HttpStatus.BAD_REQUEST);//400

    private final HttpStatus status;

    ErrorType(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}