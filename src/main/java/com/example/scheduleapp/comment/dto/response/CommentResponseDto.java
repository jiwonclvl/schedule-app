package com.example.scheduleapp.comment.dto.response;

import com.example.scheduleapp.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long id;

    private final Long memberId;

    private final Long scheduleId;

    private final String content;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public CommentResponseDto(Long id, Long memberId, Long scheduleId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
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
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
