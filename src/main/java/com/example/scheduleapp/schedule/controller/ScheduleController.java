package com.example.scheduleapp.schedule.controller;

import com.example.scheduleapp.schedule.dto.request.ScheduleRequestDto;
import com.example.scheduleapp.schedule.dto.request.UpdateScheduleRequestDto;
import com.example.scheduleapp.schedule.dto.response.SchedulePageResponseDto;
import com.example.scheduleapp.schedule.dto.response.ScheduleResponseDto;
import com.example.scheduleapp.schedule.service.ScheduleServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleServiceImpl scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody ScheduleRequestDto dto,
            HttpServletRequest request
    ) {
        log.info("일정 생성 API 호출");

        //session으로 유저 정보 가져오기
        Long httpSessionId = getHttpSessionId(request);
        scheduleService.creatSchedule(httpSessionId, dto.getTitle(), dto.getContents());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //todo: URI 이름 정해서 로그인을 하지 않아도 일정을 볼 수 있도록 수정하기 --> URI 부분 이름 변경
    @GetMapping
    public ResponseEntity<List<SchedulePageResponseDto>> getSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        log.info("일정 전체 조회 API 호출");

        return new ResponseEntity<>(scheduleService.getSchedules(page,pageSize), HttpStatus.OK);
    }

    //todo: URI 이름 정해서 로그인을 하지 않아도 일정을 볼 수 있도록 수정하기 --> URI 부분 이름 변경
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long scheduleId) {
        log.info("일정 단건 조회 API 호출");

        return new ResponseEntity<>(scheduleService.getSchedule(scheduleId), HttpStatus.OK);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequestDto dto,
            HttpServletRequest request
    ) {
        log.info("일정 수정 API 호출");
        HttpSession session = request.getSession(false);
        return new ResponseEntity<>(scheduleService.updateSchedule(session, scheduleId, dto.getTitle(), dto.getContents()), HttpStatus.OK);
    }

    //todo: 일정 삭제 시 댓글 삭제
    @DeleteMapping("/delete/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long scheduleId
    ) {
        log.info("일정 삭제 API 호출");

        scheduleService.deleteSchedule(scheduleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private Long getHttpSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (Long) session.getAttribute("id");
    }


}
