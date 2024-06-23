package com.health.common.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(BAD_REQUEST, "couldn't find user"),
    NICKNAME_DUPLICATED(BAD_REQUEST, "this nickname is duplicated"),

    EXERCISE_RECORD_NOT_FOUND(BAD_REQUEST, "couldn't find exercise record"),
    EXERCISE_RECORD_NOT_OWNED_USER(BAD_REQUEST, "this user couldn't access this record"),
    EXERCISE_RECORD_ALREADY_POSTED(BAD_REQUEST, "exercise record already posted today"),
    EXERCISE_RECORD_EXCEED_DELETE_DATE(BAD_REQUEST, "couldn't delete exercise record"),

    WEIGHT_RECORD_ALREADY_POSTED(BAD_REQUEST, "today's weight info already posted"),
    WEIGHT_RECORD_NOT_FOUND(BAD_REQUEST, "couldn't find weight info"),
    WEIGHT_RECORD_NOT_OWNED_USER(BAD_REQUEST, "this user couldn't access this record"),


    USER_INVALID_ACCESS(UNAUTHORIZED, "wrong authentication"),
    PARAMETER_INVALID(BAD_REQUEST, "this is wrong parameter");

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
