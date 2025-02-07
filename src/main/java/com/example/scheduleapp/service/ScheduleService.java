package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.response.ScheduleResponseDto;


public interface ScheduleService {

    //일정 등록
    public ScheduleResponseDto creatSchedule(Long id, String title, String contents);

}
