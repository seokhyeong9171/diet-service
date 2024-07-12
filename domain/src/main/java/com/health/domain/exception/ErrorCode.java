package com.health.domain.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "couldn't find user"),
    USER_NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "this nickname is duplicated"),
    USER_BLACKLIST(HttpStatus.BAD_REQUEST, "this user was put on a blacklist"),

    EXERCISE_RECORD_NOT_FOUND(HttpStatus.BAD_REQUEST, "couldn't find exercise record"),
    EXERCISE_RECORD_NOT_OWNED_USER(HttpStatus.BAD_REQUEST, "this user couldn't access this record"),
    EXERCISE_RECORD_ALREADY_POSTED(HttpStatus.BAD_REQUEST, "exercise record already posted today"),
    EXERCISE_RECORD_EXCEED_DELETE_DATE(HttpStatus.BAD_REQUEST, "couldn't delete exercise record"),

    WEIGHT_RECORD_ALREADY_POSTED(HttpStatus.BAD_REQUEST, "today's weight info already posted"),
    WEIGHT_RECORD_NOT_FOUND(HttpStatus.BAD_REQUEST, "couldn't find weight info"),
    WEIGHT_RECORD_NOT_OWNED_USER(HttpStatus.BAD_REQUEST, "this user couldn't access this record"),

    DAILY_MEAL_NOT_FOUND(HttpStatus.BAD_REQUEST, "daily meal on that date is not found"),
    DAILY_MEAL_AND_DT_UN_MATCH(HttpStatus.BAD_REQUEST, "daily meal and that date is not match"),
    DAILY_MEAL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "daily meal on that date is already exist"),

    MEAL_NOT_FOUND(HttpStatus.BAD_REQUEST, "this meal couldn't find"),
    MEAL_USER_INVALID(HttpStatus.BAD_REQUEST, "this meal is not owned by that user"),
    MEAL_AND_DAILY_MEAL_NOT_MATCH(HttpStatus.BAD_REQUEST, "this meal is not owned by that daily meal"),

    FOOD_NOT_FOUND(HttpStatus.BAD_REQUEST, "couldn't find food"),

    CONSUME_FOOD_NOT_FOUND(HttpStatus.BAD_REQUEST, "couldn't find consumeFood"),
    CONSUME_FOOD_AND_MEAL_UN_MATCH(HttpStatus.BAD_REQUEST, "this consumeFood and that meal is not match"),

    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "couldn't find post"),
    POST_NOT_CREATE_USER(HttpStatus.BAD_REQUEST, "this post is not created by that user"),
    POST_LIKE_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "this user already liked that post"),
    POST_LIKE_NOT_EXIST(HttpStatus.BAD_REQUEST, "this user didn't like that post yet"),

    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "couldn't find comment"),
    COMMENT_AND_USER_NOT_MATCH(HttpStatus.BAD_REQUEST, "this comment is not owned by that user"),
    COMMENT_AND_POST_NOT_MATCH(HttpStatus.BAD_REQUEST, "this comment is not owned by that post"),
    COMMENT_CHILD_AND_PARENT_NOT_MATCH(HttpStatus.BAD_REQUEST, "this child comment is not owned by that comment"),
    COMMENT_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "this comment is already deleted"),
    COMMENT_PARENT_DELETED(HttpStatus.BAD_REQUEST, "parent comment is deleted"),
    COMMENT_IS_CHILD(HttpStatus.BAD_REQUEST, "child comment can not created to other child comment"),

    MEETING_NOT_FOUND(HttpStatus.BAD_REQUEST, "couldn't find meeting"),
    MEETING_CREATOR_NOT_MATCH(HttpStatus.BAD_REQUEST, "this meeting is not created by that user"),
    MEETING_CREATOR_CAN_NOT_ENROLL(HttpStatus.BAD_REQUEST,"meeting creators can't enroll their own meeting"),

    MEETING_PARTICIPANT_FULL(HttpStatus.BAD_REQUEST,"The number of participants has been exceeded"),
    MEETING_PARTICIPANT_NOT_FOUND(HttpStatus.BAD_REQUEST,"couldn't find meeting participant"),
    MEETING_PARTICIPANT_STATUS_NOT_PENDING(HttpStatus.BAD_REQUEST,"this meeting participant's status is not pending."),
    MEETING_PARTICIPANT_STATUS_NOT_APPROVAL(HttpStatus.BAD_REQUEST,"this meeting participant is not approved."),
    MEETING_PARTICIPANT_STATUS_NOT_VALID(HttpStatus.BAD_REQUEST,"this meeting participant status is not valid."),
    MEETING_PARTICIPANT_AND_USER_NOT_MATCH(HttpStatus.BAD_REQUEST,"user and participant are not matching."),
    MEETING_PARTICIPANT_AND_MEETING_NOT_MATCH(HttpStatus.BAD_REQUEST,"meeting and participant are not matching."),

    USER_INVALID_ACCESS(HttpStatus.UNAUTHORIZED, "wrong authentication"),
    PARAMETER_INVALID(HttpStatus.BAD_REQUEST, "this is wrong parameter"),

    REDIS_OBJECT_NOT_EXIST(HttpStatus.BAD_REQUEST, "this redis object is not exist"),
    REDIS_LOCK_UNAVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "redis lock is unavailable now"),
    REDIS_LOCK_TIMEOUT(HttpStatus.INTERNAL_SERVER_ERROR, "redis lock is timeout"),

    API_NOT_WORKING(HttpStatus.INTERNAL_SERVER_ERROR, "api is not working now");

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
