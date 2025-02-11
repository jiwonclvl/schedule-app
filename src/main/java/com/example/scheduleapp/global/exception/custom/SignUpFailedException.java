package com.example.scheduleapp.global.exception.custom;

import com.example.scheduleapp.global.exception.ErrorCode;


public class SignUpFailedException extends CustomException {
    public SignUpFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
