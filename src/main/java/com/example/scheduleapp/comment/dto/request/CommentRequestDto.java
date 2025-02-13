package com.example.scheduleapp.comment.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    private final String content;

    @JsonCreator
    public CommentRequestDto(@JsonProperty String content) {
        this.content = content;
    }
}
