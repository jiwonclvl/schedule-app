package com.example.scheduleapp.global.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class SuccessWithDataResponseDto <T> {
    private final Integer status;

    private final T data;

    private final String message;

    private SuccessWithDataResponseDto(Integer status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> ResponseEntity<SuccessWithDataResponseDto<T>> successOkWithDataResponse(HttpStatus status, String message, T dto) {
        return ResponseEntity
                .status(status)
                .body(SuccessWithDataResponseDto.<T>builder()
                        .status(status.value())
                        .data(dto)
                        .message(message)
                        .build()
                );
    }

    public static <T> ResponseEntity<SuccessWithDataResponseDto<T>> successCreateResponse(HttpStatus status, String message, T dto) {
        return ResponseEntity
                .status(status)
                .body(SuccessWithDataResponseDto.<T>builder()
                        .status(status.value())
                        .data(dto)
                        .message(message)
                        .build()
                );
    }
}
