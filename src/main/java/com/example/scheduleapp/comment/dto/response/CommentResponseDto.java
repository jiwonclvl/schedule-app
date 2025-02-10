package com.example.scheduleapp.comment.dto.response;

import com.example.scheduleapp.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final Long id;

    private final Long memberId;

    private final Long scheduleId;

    private final String content;

    private final String createdAt;

    private final String updatedAt;

    public CommentResponseDto(Long id, Long memberId, Long scheduleId, String content, String createdAt, String updatedAt) {
        this.id = id;
        this.memberId = memberId;
        this.scheduleId = scheduleId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CommentResponseDto commentDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getMember().getId(),
                comment.getSchedule().getId(),
                comment.getContent(),
                comment.getCreatedAt().toString(),
                comment.getUpdatedAt().toString()
        );
    }
}
