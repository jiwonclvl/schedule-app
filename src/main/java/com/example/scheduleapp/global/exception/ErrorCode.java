package com.example.scheduleapp.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "입력한 정보가 일치하지 않습니다.");

    private final HttpStatus errorCode;
    private final String errorMessage;

}
