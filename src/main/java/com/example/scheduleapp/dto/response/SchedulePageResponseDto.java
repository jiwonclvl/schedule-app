package com.example.scheduleapp.dto.response;

import com.example.scheduleapp.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulePageResponseDto {

    private final String memberName;

    private final String title;

    private final String content;

    private final Long commentCount;

    private final LocalDateTime scheduleCreatedTime;

    private final LocalDateTime scheduleUpdatedTime;


    public SchedulePageResponseDto(String memberName, String title, String content, Long commentCount, LocalDateTime scheduleCreatedTime, LocalDateTime scheduleUpdatedTime) {
        this.memberName = memberName;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.scheduleCreatedTime = scheduleCreatedTime;
        this.scheduleUpdatedTime = scheduleUpdatedTime;
    }
}
