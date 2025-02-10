package com.example.scheduleapp.comment.dto.request;

import lombok.Getter;

@Getter
public class UpdateCommentRequestDto {
    private final String content;

    public UpdateCommentRequestDto(String content) {
        this.content = content;
    }
}
