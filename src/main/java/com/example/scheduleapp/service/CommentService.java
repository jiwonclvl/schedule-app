package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.response.CommentResponseDto;

import java.util.List;

public interface CommentService {

    //댓글 등록
    public CommentResponseDto createComment(Long scheduleId, Long httpSessionId, String comment);

    //댓글 전체 조회
    public List<CommentResponseDto> getComments();

    //댓글 수정
    public void updateComment(Long commentId, String content);

}
