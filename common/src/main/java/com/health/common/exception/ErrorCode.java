package com.health.common.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(BAD_REQUEST, "couldn't find user"),

    USER_INVALID_ACCESS(UNAUTHORIZED, "wrong authentication");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorCode findByString(String str) {
        return ErrorCode.valueOf(str);
    }

}
