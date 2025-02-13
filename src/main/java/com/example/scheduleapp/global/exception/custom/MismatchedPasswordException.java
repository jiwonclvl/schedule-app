package com.example.scheduleapp.global.exception.custom;

import com.example.scheduleapp.global.exception.ErrorCode;

public class MismatchedPasswordException extends CustomException {
    public MismatchedPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
