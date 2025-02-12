package com.example.scheduleapp.schedule.controller;

import com.example.scheduleapp.global.dto.SuccessResponseDto;
import com.example.scheduleapp.global.dto.SuccessWithDataResponseDto;
import com.example.scheduleapp.schedule.dto.request.ScheduleRequestDto;
import com.example.scheduleapp.schedule.dto.request.UpdateScheduleRequestDto;
import com.example.scheduleapp.schedule.dto.response.SchedulePageResponseDto;
import com.example.scheduleapp.schedule.dto.response.ScheduleResponseDto;
import com.example.scheduleapp.schedule.service.ScheduleServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.scheduleapp.global.dto.SuccessResponseDto.successOkResponse;


@Slf4j
@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleServiceImpl scheduleService;

    @PostMapping
    public ResponseEntity<SuccessWithDataResponseDto<ScheduleResponseDto>> createSchedule(
            @Valid @RequestBody ScheduleRequestDto dto,
            HttpServletRequest request
    ) {
        log.info("일정 생성 API 호출");

        /*session으로 유저 정보 가져오기*/
        Long httpSessionId = getHttpSessionId(request);

        ScheduleResponseDto scheduleResponseDto = scheduleService.creatSchedule(httpSessionId, dto.getTitle(), dto.getContents());
        return SuccessWithDataResponseDto.successCreateResponse(HttpStatus.CREATED, "일정이 정상적으로 등록되었습니다.",scheduleResponseDto);
    }

    //todo: URI 이름 정해서 로그인을 하지 않아도 일정을 볼 수 있도록 수정하기 --> URI 부분 이름 변경
    @GetMapping
    public ResponseEntity<SuccessWithDataResponseDto<List<SchedulePageResponseDto>>> getSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        log.info("일정 전체 조회 API 호출");
        List<SchedulePageResponseDto> schedules = scheduleService.getSchedules(page, pageSize);
        return SuccessWithDataResponseDto.successOkWithDataResponse(HttpStatus.OK, "일정 전체 조회에 성공하였습니다.",schedules);
    }

    //todo: URI 이름 정해서 로그인을 하지 않아도 일정을 볼 수 있도록 수정하기 --> URI 부분 이름 변경
    @GetMapping("/{scheduleId}")
    public ResponseEntity<SuccessWithDataResponseDto<ScheduleResponseDto>> getSchedule(@PathVariable Long scheduleId) {
        log.info("일정 단건 조회 API 호출");

        ScheduleResponseDto schedule = scheduleService.getSchedule(scheduleId);
        return SuccessWithDataResponseDto.successOkWithDataResponse(HttpStatus.OK,"일정 단건 조회에 성공하였습니다.", schedule);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<SuccessWithDataResponseDto<ScheduleResponseDto>> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequestDto dto,
            HttpServletRequest request
    ) {
        log.info("일정 수정 API 호출");

        /*session으로 유저 정보 가져오기*/
        Long httpSessionId = getHttpSessionId(request);
        ScheduleResponseDto scheduleResponseDto = scheduleService.updateSchedule(httpSessionId, scheduleId, dto.getTitle(), dto.getContents());

        return SuccessWithDataResponseDto.successOkWithDataResponse(HttpStatus.OK, "일정 성공적으로 수정되었습니다.", scheduleResponseDto);
    }

    //todo: 일정 삭제 시 댓글 삭제
    @DeleteMapping("/delete/{scheduleId}")
    public ResponseEntity<SuccessResponseDto> updateSchedule(
            @PathVariable Long scheduleId,
            HttpServletRequest request
    ) {
        log.info("일정 삭제 API 호출");

        /*session으로 유저 정보 가져오기*/
        Long httpSessionId = getHttpSessionId(request);
        scheduleService.deleteSchedule(httpSessionId, scheduleId);
        return successOkResponse("일정이 정상적으로 삭제되었습니다.");
    }

    private Long getHttpSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (Long) session.getAttribute("id");
    }


}
