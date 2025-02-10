package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.request.CommentRequestDto;
import com.example.scheduleapp.dto.response.CommentResponseDto;

public interface CommentService {

    //댓글 등록
    public CommentResponseDto createComment(Long scheduleId, Long httpSessionId, String comment);
}
