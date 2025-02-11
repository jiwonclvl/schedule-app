package com.example.scheduleapp.global.exception.custom;

import com.example.scheduleapp.global.exception.ErrorCode;

public class LoginFailedException extends CustomException {
    public LoginFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
