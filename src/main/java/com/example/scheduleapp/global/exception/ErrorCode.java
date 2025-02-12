package com.example.scheduleapp.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "입력한 정보가 일치하지 않습니다."),
    CONFLICT(HttpStatus.CONFLICT, "이미 등록된 사용자입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 데이터를 찾을 수 없습니다."),
    CANNOT_UPDATE_OTHERS_DATA(HttpStatus.FORBIDDEN, "다른 사람의 정보는 수정 및 삭제가 불가능합니다."),
    UNCHANGED_EMAIL(HttpStatus.BAD_REQUEST, "기존과 동일한 이메일입니다."),
    UNCHANGED_PASSWORD(HttpStatus.BAD_REQUEST, "기존과 동일한 비밀번호입니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "로그인 해주세요.");

    private final HttpStatus errorCode;
    private final String errorMessage;
}
