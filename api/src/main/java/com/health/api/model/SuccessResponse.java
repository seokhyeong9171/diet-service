package com.health.api.model;

import lombok.Getter;

@Getter
public class SuccessResponse<T> {

    private final String status;

    private final T data;

    private final String message;


    public SuccessResponse(T data) {
        this.status = "success";
        this.data = data;
        this.message = "Request processed successfully";
    }

    public static <T> SuccessResponse<T> of(T data) {
        return new SuccessResponse<>(data);
    }

}
