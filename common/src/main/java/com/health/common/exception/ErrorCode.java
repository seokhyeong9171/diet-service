package com.health.common.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(BAD_REQUEST, "couldn't find user"),

    NICKNAME_DUPLICATED(BAD_REQUEST, "this nickname is duplicated"),

    USER_INVALID_ACCESS(UNAUTHORIZED, "wrong authentication"),

    EXERCISE_RECORD_NOT_FOUND(BAD_REQUEST, "couldn't find exercise record");

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
