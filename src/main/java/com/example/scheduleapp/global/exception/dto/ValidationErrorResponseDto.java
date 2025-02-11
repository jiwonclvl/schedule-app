package com.example.scheduleapp.global.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ValidationErrorResponseDto {
    //상태 코드
    private final Integer status;

    //에러 메세지
    private final List<String> messages;

    /*정적 팩토리 메서드를 통한 validation 예외 응답 생성*/
    public static ResponseEntity<ValidationErrorResponseDto> validationErrorResponse(HttpStatusCode errorCode, List<String> errorMessages) {
        return ResponseEntity
                .status(errorCode)
                .body(ValidationErrorResponseDto.builder()
                .status(errorCode.value())
                .messages(errorMessages)
                .build());
    }
}
