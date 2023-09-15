package com.sundayCinema.sundayCinema.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    MEMBER_NOT_SIGNED_IN(401, "Member not signed in"),
    MOVIE_NOT_FOUND(404, "Movie not found"),
    CANNOT_CHANGE_COMMENT(403, "Comment cannot change"),
    NOT_IMPLEMENTATION(501, "Not Implementation"),
    INVALID_MEMBER_STATUS(400, "Invalid member status");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}