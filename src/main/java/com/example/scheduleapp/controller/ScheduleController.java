package com.example.scheduleapp.controller;

import com.example.scheduleapp.dto.ScheduleRequestDto;
import com.example.scheduleapp.dto.ScheduleResponseDto;
import com.example.scheduleapp.service.ScheduleService;
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
    @PostMapping("/{memberid}")
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto dto
    ) {
        log.info("일정 생성 API 호출");

        //
        return new ResponseEntity<>(scheduleService.creatSchedule(id, dto.getTitle(), dto.getContents()), HttpStatus.OK);
    }

    //일정 전체 조회
//    @GetMapping ("/{id}")
//    public ResponseEntity<List<ScheduleResponseDto>> getSchedules() {
//
//    }

}
