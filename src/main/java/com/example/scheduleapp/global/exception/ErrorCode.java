package com.example.scheduleapp.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "입력한 정보가 일치하지 않습니다."), //인증 실패 즉, 비밀번호가 틀렸을 경우 사용하는 예외 상수
    CONFLICT(HttpStatus.CONFLICT, "이미 등록된 사용자입니다."), //존재하는 데이터를 또 만들려고 할 때 사용하는 예외 상수 (회원가입 시 사용)
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 데이터를 찾을 수 없습니다."); //존재하지 않는 데이터를 조회하려고 할 때

    private final HttpStatus errorCode;
    private final String errorMessage;
}


