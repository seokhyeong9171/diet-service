package com.health.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

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

    DAILY_MEAL_NOT_FOUND(BAD_REQUEST, "daily meal on that date is not found"),
    DAILY_MEAL_AND_DT_UN_MATCH(BAD_REQUEST, "daily meal and that date is not match"),
    DAILY_MEAL_ALREADY_EXIST(BAD_REQUEST, "daily meal on that date is already exist"),

    MEAL_NOT_FOUND(BAD_REQUEST, "this meal couldn't find"),
    MEAL_USER_INVALID(BAD_REQUEST, "this meal is not owned by that user"),
    MEAL_AND_DAILY_MEAL_NOT_MATCH(BAD_REQUEST, "this meal is not owned by that daily meal"),

    FOOD_NOT_FOUND(BAD_REQUEST, "couldn't find food"),

    CONSUME_FOOD_NOT_FOUND(BAD_REQUEST, "couldn't find consumeFood"),
    CONSUME_FOOD_AND_MEAL_UN_MATCH(BAD_REQUEST, "this consumeFood and that meal is not match"),

    POST_NOT_FOUND(BAD_REQUEST, "couldn't find post"),
    POST_NOT_CREATE_USER(BAD_REQUEST, "this post is not created by that user"),

    COMMENT_NOT_FOUND(BAD_REQUEST, "couldn't find comment"),
    COMMENT_AND_USER_NOT_MATCH(BAD_REQUEST, "this comment is not owned by that user"),
    COMMENT_AND_POST_NOT_MATCH(BAD_REQUEST, "this comment is not owned by that post"),
    COMMENT_CHILD_AND_PARENT_NOT_MATCH(BAD_REQUEST, "this child comment is not owned by that comment"),
    COMMENT_ALREADY_DELETED(BAD_REQUEST, "this comment is already deleted"),
    COMMENT_PARENT_DELETED(BAD_REQUEST, "parent comment is deleted"),
    COMMENT_IS_SUB(BAD_REQUEST, "sub comment is not created to sub comment"),

    USER_INVALID_ACCESS(UNAUTHORIZED, "wrong authentication"),
    PARAMETER_INVALID(BAD_REQUEST, "this is wrong parameter"),

    REDIS_OBJECT_NOT_EXIST(BAD_REQUEST, "this redis object is not exist"),

    API_NOT_WORKING(INTERNAL_SERVER_ERROR, "api is not working now");

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
