package com.example.scheduleapp.global.exception.custom;

import com.example.scheduleapp.global.exception.ErrorCode;

public class EmailUnchangedException extends CustomException {
    public EmailUnchangedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
