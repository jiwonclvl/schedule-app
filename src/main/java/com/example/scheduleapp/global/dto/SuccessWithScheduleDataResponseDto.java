package com.example.scheduleapp.global.dto;

import com.example.scheduleapp.schedule.dto.response.ScheduleResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class SuccessWithScheduleDataResponseDto {

    private final Integer status;

    private final ScheduleResponseDto data;

    private final String message;

    private SuccessWithScheduleDataResponseDto(Integer status, ScheduleResponseDto data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    /*상태 코드, 데이터, 메세지를 출력하는 경우(일정 생성)*/
    public static ResponseEntity<SuccessWithScheduleDataResponseDto> successCreateScheduleResponse(HttpStatus status, String message, ScheduleResponseDto dto) {
        return ResponseEntity
                .status(status)
                .body(SuccessWithScheduleDataResponseDto.builder()
                        .status(status.value())
                        .data(dto)
                        .message(message)
                        .build()
                );
    }
}
