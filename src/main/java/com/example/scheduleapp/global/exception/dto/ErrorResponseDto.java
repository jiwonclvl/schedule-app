package com.example.scheduleapp.global.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponseDto {

    //상태 코드
    private final Integer status;

    //에러 메세지
    private final String message;

    /*정적 팩토리 메서드를 통한 예외 응답 생성*/
    public static ResponseEntity<ErrorResponseDto> errorResponse(HttpStatus errorCode, String errorMessage) {
        return ResponseEntity
                .status(errorCode)
                .body(ErrorResponseDto.builder()
                .status(errorCode.value())
                .message(errorMessage)
                .build());
    }
}
