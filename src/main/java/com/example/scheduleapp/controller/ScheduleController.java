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
        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("id");
        return new ResponseEntity<>(scheduleService.creatSchedule(id, dto.getTitle(), dto.getContents()), HttpStatus.OK);
    }

    //일정 전체 조회
//    @GetMapping ()
//    public ResponseEntity<List<ScheduleResponseDto>> getSchedules() {
//
//    }

}
