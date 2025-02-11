package com.example.scheduleapp.global.exception.custom;

import com.example.scheduleapp.global.exception.ErrorCode;

public class PasswordException extends CustomException {
    public PasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
