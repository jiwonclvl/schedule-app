package com.example.scheduleapp.global.docs;

import com.example.scheduleapp.global.dto.SuccessResponseDto;
import com.example.scheduleapp.global.dto.SuccessWithDataResponseDto;
import com.example.scheduleapp.schedule.dto.request.ScheduleRequestDto;
import com.example.scheduleapp.schedule.dto.request.UpdateScheduleRequestDto;
import com.example.scheduleapp.schedule.dto.response.SchedulePageResponseDto;
import com.example.scheduleapp.schedule.dto.response.ScheduleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "일정 CRUD를 관리", description = "사용자 일정의 등록, 조회, 수정, 삭제를 관리하는 API입니다.")
public interface ScheduleControllerDocs {
    @Operation(
            summary = "일정 생성",
            description = "유저의 정보를 바탕으로 일정을 생성하는 API입니다."
    )
    ResponseEntity<SuccessWithDataResponseDto<ScheduleResponseDto>> createSchedule(
            @Parameter(description = "일정 생성 요청 DTO") @Validated @RequestBody ScheduleRequestDto dto,
            @Parameter(description = "요청을 보낸 사용자 세션 정보") HttpServletRequest request
    );

    @Operation(
            summary = "일정 전체 조회",
            description = "전체 일정 목록을 페이지네이션을 통해 조회하는 API입니다."
    )
    ResponseEntity<SuccessWithDataResponseDto<Page<SchedulePageResponseDto>>> getSchedules(
            @Parameter(description = "조회할 페이지 번호", required = true) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지당 항목 수", required = true) @RequestParam(defaultValue = "10") int pageSize
    );

    @Operation(
            summary = "일정 단건 조회",
            description = "주어진 일정 ID로 단건 일정을 조회하는 API입니다."
    )
    ResponseEntity<SuccessWithDataResponseDto<ScheduleResponseDto>> getSchedule(
            @Parameter(description = "조회할 일정 ID", required = true) @PathVariable Long scheduleId
    );

    @Operation(
            summary = "일정 수정",
            description = "주어진 일정 ID에 대해 제목과 내용을 수정하는 API입니다."
    )
    ResponseEntity<SuccessWithDataResponseDto<ScheduleResponseDto>> updateSchedule(
            @Parameter(description = "수정할 일정 ID", required = true) @PathVariable Long scheduleId,
            @Parameter(description = "수정할 일정 요청 DTO") @Valid @RequestBody UpdateScheduleRequestDto dto,
            @Parameter(description = "요청을 보낸 사용자 세션 정보") HttpServletRequest request
    );

    @Operation(
            summary = "일정 삭제",
            description = "주어진 일정 ID에 대해 해당 일정을 삭제하는 API입니다."
    )
    ResponseEntity<SuccessResponseDto> deleteSchedule(
            @Parameter(description = "삭제할 일정 ID", required = true) @PathVariable Long scheduleId,
            @Parameter(description = "요청을 보낸 사용자 세션 정보") HttpServletRequest request
    );
}
