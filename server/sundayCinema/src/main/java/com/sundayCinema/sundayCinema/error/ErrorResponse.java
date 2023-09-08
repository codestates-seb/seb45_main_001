package com.sundayCinema.sundayCinema.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse { // 에러 정보 담는 클래스
    private boolean success;
    private String message;
    private List<FieldError> fieldErrors;

    public static ErrorResponse of(boolean success, String message) {
        return new ErrorResponse(success, message, null);
    }

    public static ErrorResponse of(boolean success, String message, List<FieldError> fieldErrors) {
        return new ErrorResponse(success, message, fieldErrors);
    }

}
