package com.example.scheduleapp.global;

import com.example.scheduleapp.global.exception.dto.ErrorResponseDto;
import com.example.scheduleapp.member.dto.response.MemberResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class SuccessResponseDto {

    private final Integer status;

    private final MemberResponseDto data;

    private final String message;

    public static ResponseEntity<SuccessResponseDto> successResponse(String message) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .build()
        );
    }

    public static ResponseEntity<SuccessResponseDto> successCreateResponse(HttpStatus status, String message, MemberResponseDto dto) {
        return ResponseEntity
                .status(status)
                .body(SuccessResponseDto.builder()
                        .status(status.value())
                        .data(dto)
                        .message(message)
                        .build()
                );
    }
}
