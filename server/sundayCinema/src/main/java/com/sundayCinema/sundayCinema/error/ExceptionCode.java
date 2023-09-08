package com.sundayCinema.sundayCinema.error;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(false,404, "Member Not Found"),
    DUPLICATE_MEMBER(false, 400, "Duplicate Member"),
    MEMBER_EXISTS(false,200,"OK");

    @Getter
    private boolean success;
    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(boolean success, int status, String message) {
        this.success = success;
        this.status = status;
        this.message = message;
    }
}
