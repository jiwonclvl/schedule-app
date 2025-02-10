package com.example.scheduleapp.comment.controller;

import com.example.scheduleapp.comment.dto.request.CommentRequestDto;
import com.example.scheduleapp.comment.dto.request.UpdateCommentRequestDto;
import com.example.scheduleapp.comment.dto.response.CommentResponseDto;
import com.example.scheduleapp.comment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/schedules/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{scheduleId}")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CommentRequestDto dto,
            HttpServletRequest request
    ) {
        log.info("댓글 생성 API 호출");
        //세션에 담긴 값 넘겨주기 (유저의 id값이 담겨있다.)
        Long httpSessionId = getHttpSessionId(request);
        return new ResponseEntity<>(commentService.createComment(scheduleId, httpSessionId, dto.getContent()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments() {
        log.info("댓글 전체 조회 API 호출");
        return new ResponseEntity<>(commentService.getComments(), HttpStatus.OK);
    }

    @PatchMapping("/content/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequestDto dto
    ) {
        log.info("댓글 수정 API 호출");
        commentService.updateComment(commentId, dto.getContent());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        log.info("댓글 삭제 API 호출");
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Long getHttpSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (Long) session.getAttribute("id");
    }
}
