package com.example.scheduleapp.global.exception.custom;

import com.example.scheduleapp.global.exception.ErrorCode;

public class NotLoggedInException extends CustomException {
    public NotLoggedInException(ErrorCode errorCode) {
        super(errorCode);
    }
}
