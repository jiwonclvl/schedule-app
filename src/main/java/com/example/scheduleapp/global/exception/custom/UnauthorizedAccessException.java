package com.example.scheduleapp.global.exception.custom;

import com.example.scheduleapp.global.exception.ErrorCode;

public class UnauthorizedAccessException extends CustomException {
    public UnauthorizedAccessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
