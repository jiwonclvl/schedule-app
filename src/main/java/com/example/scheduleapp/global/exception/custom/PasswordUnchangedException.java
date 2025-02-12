package com.example.scheduleapp.global.exception.custom;

import com.example.scheduleapp.global.exception.ErrorCode;
import org.springframework.web.bind.EscapedErrors;

public class PasswordUnchangedException extends CustomException {
    public PasswordUnchangedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
