package com.example.scheduleapp.global.dto;

import com.example.scheduleapp.member.dto.response.MemberResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class SuccessWithDataResponseDto {
    private final Integer status;

    private final MemberResponseDto data;

    private final String message;

    private SuccessWithDataResponseDto(Integer status, MemberResponseDto data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    /*상태 코드, 데이터, 메세지를 출력하는 경우(유저 단건 조회)*/
    public static ResponseEntity<SuccessWithDataResponseDto> successOkWithDataResponse(HttpStatus status, String message, MemberResponseDto dto) {
        return ResponseEntity
                .status(status)
                .body(SuccessWithDataResponseDto.builder()
                        .status(status.value())
                        .data(dto)
                        .message(message)
                        .build()
                );
    }

    /*상태 코드, 데이터, 메세지를 출력하는 경우(유저 생성)*/
    public static ResponseEntity<SuccessWithDataResponseDto> successCreateResponse(HttpStatus status, String message, MemberResponseDto dto) {
        return ResponseEntity
                .status(status)
                .body(SuccessWithDataResponseDto.builder()
                        .status(status.value())
                        .data(dto)
                        .message(message)
                        .build()
                );
    }
}
