package com.example.scheduleapp.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "입력한 정보가 일치하지 않습니다."),
    CONFLICT(HttpStatus.CONFLICT, "이미 등록된 사용자입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 데이터를 찾을 수 없습니다.");

    private final HttpStatus errorCode;
    private final String errorMessage;
}
