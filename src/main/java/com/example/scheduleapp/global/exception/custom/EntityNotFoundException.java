package com.example.scheduleapp.global.exception.custom;

import com.example.scheduleapp.global.exception.ErrorCode;

public class EntityNotFoundException extends CustomException {
    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
