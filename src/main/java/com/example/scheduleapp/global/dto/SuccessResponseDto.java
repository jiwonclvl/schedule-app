package com.example.scheduleapp.global.dto;

import com.example.scheduleapp.member.dto.response.MemberResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class SuccessResponseDto {

    private final Integer status;

    private final String message;

    /*상태 코드와 메세지만 출력하는 경우*/
    public static ResponseEntity<SuccessResponseDto> successOkResponse(String message) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .build()
        );
    }
}
