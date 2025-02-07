package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.response.ScheduleResponseDto;

import java.util.List;


public interface ScheduleService {

    //일정 등록
    public ScheduleResponseDto creatSchedule(Long memberId, String title, String contents);

    //일정 전체 가져오기
    public List<ScheduleResponseDto> getSchedules();

    //일정 단건 가져오기
    public ScheduleResponseDto getSchedule(Long getSchedule);


}
