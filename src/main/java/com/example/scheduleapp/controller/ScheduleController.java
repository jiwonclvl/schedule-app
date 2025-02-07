package com.example.scheduleapp.controller;

import com.example.scheduleapp.dto.request.ScheduleRequestDto;
import com.example.scheduleapp.dto.response.ScheduleResponseDto;
import com.example.scheduleapp.service.ScheduleService;
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

    private final ScheduleService scheduleService;

    //일정 등록
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody ScheduleRequestDto dto,
            HttpServletRequest request
    ) {
        log.info("일정 생성 API 호출");

        //session으로 유저 정보 가져오기
        Long httpSessionId = getHttpSessionId(request);
        return new ResponseEntity<>(scheduleService.creatSchedule(httpSessionId, dto.getTitle(), dto.getContents()), HttpStatus.OK);
    }

    //일정 전체 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules() {
        log.info("일정 전체 조회 API 호출");

        return new ResponseEntity<>(scheduleService.getSchedules(), HttpStatus.OK);
    }

    //일정 단건 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long scheduleId) {
        log.info("일정 단건 조회 API 호출");

        return new ResponseEntity<>(scheduleService.getSchedule(scheduleId), HttpStatus.OK);
    }

    private Long getHttpSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (Long) session.getAttribute("id");
    }


}
