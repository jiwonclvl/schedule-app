package com.example.scheduleapp.schedule.service;

import com.example.scheduleapp.schedule.dto.response.SchedulePageResponseDto;
import com.example.scheduleapp.schedule.dto.response.ScheduleResponseDto;

import java.util.List;


public interface ScheduleService {

    //일정 등록
    public ScheduleResponseDto creatSchedule(Long memberId, String title, String contents);

    //일정 전체 가져오기
    public List<SchedulePageResponseDto> getSchedules(int page, int pageSize);

    //일정 단건 가져오기
    public ScheduleResponseDto getSchedule(Long getSchedule);

    //일정 수정하기
    public ScheduleResponseDto updateSchedule(Long scheduleId, String title, String contents);

    //일정 삭제하기
    public void deleteSchedule(Long scheduleId);

}
