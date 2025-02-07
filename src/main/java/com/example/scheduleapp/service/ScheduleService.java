package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.ScheduleResponseDto;
import com.example.scheduleapp.entity.Schedule;
import org.springframework.stereotype.Service;


public interface ScheduleService {

    //일정 등록
    public ScheduleResponseDto creatSchedule(Long id, String title, String contents);

}
