package com.example.scheduleapp.global.exception.custom;

import com.example.scheduleapp.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getErrorMessage();
    }
}
