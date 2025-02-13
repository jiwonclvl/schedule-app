package com.example.scheduleapp.global.exception.custom;

import com.example.scheduleapp.global.exception.ErrorCode;

public class NoSuchEmailException extends CustomException {
    public NoSuchEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
