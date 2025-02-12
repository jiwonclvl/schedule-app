package com.example.scheduleapp.comment.controller;

import com.example.scheduleapp.comment.dto.request.CommentRequestDto;
import com.example.scheduleapp.comment.dto.request.UpdateCommentRequestDto;
import com.example.scheduleapp.comment.dto.response.CommentResponseDto;
import com.example.scheduleapp.comment.service.CommentServiceImpl;
import com.example.scheduleapp.global.dto.SuccessWithDataResponseDto;
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

    private final CommentServiceImpl commentService;

    @PostMapping("/{scheduleId}")
    public ResponseEntity<SuccessWithDataResponseDto<CommentResponseDto>> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CommentRequestDto dto,
            HttpServletRequest request
    ) {
        log.info("댓글 생성 API 호출");
        //세션에 담긴 값 넘겨주기 (유저의 id값이 담겨있다.)
        Long httpSessionId = getHttpSessionId(request);

        CommentResponseDto comment = commentService.createComment(scheduleId, httpSessionId, dto.getContent());
        return SuccessWithDataResponseDto.successCreateResponse(HttpStatus.CREATED, "댓글이 등록 되었습니다.", comment);
    }

    /*todo, 로그인 하지 않아도 볼 수 있도록 수정한다.*/
    @GetMapping
    public ResponseEntity<SuccessWithDataResponseDto<List<CommentResponseDto>>> getComments() {
        log.info("댓글 전체 조회 API 호출");
        List<CommentResponseDto> comments = commentService.getComments();
        return SuccessWithDataResponseDto.successOkWithDataResponse(HttpStatus.OK, "댓글 전체 조회에 성공하였습니다.", comments);
    }

    /*todo, 로그인 하지 않아도 볼 수 있도록 수정한다.*/
    @GetMapping("/{commentId}")
    public ResponseEntity<SuccessWithDataResponseDto<CommentResponseDto>> getComment(@PathVariable Long commentId) {
        log.info("댓글 단건 조회 API 호출");

        CommentResponseDto comment = commentService.getComment(commentId);
        return SuccessWithDataResponseDto.successOkWithDataResponse(HttpStatus.OK, "댓글 단건 조회에 성공하였습니다.", comment);
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
