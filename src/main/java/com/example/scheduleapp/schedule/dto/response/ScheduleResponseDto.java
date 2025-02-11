package com.example.scheduleapp.schedule.dto.response;

import com.example.scheduleapp.schedule.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    private final Long id;

    private final String username;

    private final String title;

    private final String contents;

    private final String createdAt;

    private final String updatedAt;

    public ScheduleResponseDto(Long id, String username, String title, String contents, String createdAt, String updatedAt) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static  ScheduleResponseDto ScheduleDto(Schedule schedule) {
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getMember().getUsername(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt().toString(),
                schedule.getUpdatedAt().toString()
        );
    }

}
