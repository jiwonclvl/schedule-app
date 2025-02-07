package com.example.scheduleapp.dto.response;

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
}
