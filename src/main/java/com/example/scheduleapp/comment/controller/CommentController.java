package com.example.scheduleapp.comment.controller;

import com.example.scheduleapp.comment.dto.request.CommentRequestDto;
import com.example.scheduleapp.comment.dto.response.CommentResponseDto;
import com.example.scheduleapp.comment.service.CommentServiceImpl;
import com.example.scheduleapp.global.docs.CommentControllerDocs;
import com.example.scheduleapp.global.dto.SuccessResponseDto;
import com.example.scheduleapp.global.dto.SuccessWithDataResponseDto;
import com.example.scheduleapp.global.filter.SessionConst;
import com.example.scheduleapp.member.entity.Member;
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
public class CommentController implements CommentControllerDocs {

    private final CommentServiceImpl commentService;

    @PostMapping("/{scheduleId}")
    public ResponseEntity<SuccessWithDataResponseDto<CommentResponseDto>> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CommentRequestDto dto,
            HttpServletRequest request
    ) {
        log.info("댓글 생성 API 호출");

        CommentResponseDto comment = commentService.createComment(scheduleId, getLoginMemberId(request), dto.getContent());
        return SuccessWithDataResponseDto.successCreateResponse(HttpStatus.CREATED, "댓글이 등록 되었습니다.", comment);
    }

    @GetMapping
    public ResponseEntity<SuccessWithDataResponseDto<List<CommentResponseDto>>> getComments() {
        log.info("댓글 전체 조회 API 호출");
        List<CommentResponseDto> comments = commentService.getComments();
        return SuccessWithDataResponseDto.successOkWithDataResponse(HttpStatus.OK, "댓글 전체 조회에 성공하였습니다.", comments);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<SuccessWithDataResponseDto<CommentResponseDto>> getComment(@PathVariable Long commentId) {
        log.info("댓글 단건 조회 API 호출");

        CommentResponseDto comment = commentService.getComment(commentId);
        return SuccessWithDataResponseDto.successOkWithDataResponse(HttpStatus.OK, "댓글 단건 조회에 성공하였습니다.", comment);
    }

    @PatchMapping("/{commentId}/update")
    public ResponseEntity<SuccessWithDataResponseDto<CommentResponseDto>> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto dto,
            HttpServletRequest request
    ) {
        log.info("댓글 수정 API 호출");

        CommentResponseDto commentResponseDto = commentService.updateComment(getLoginMemberId(request), commentId, dto.getContent());
        return SuccessWithDataResponseDto.successOkWithDataResponse(HttpStatus.OK, "댓글이 수정 되었습니다.", commentResponseDto);
    }

    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<SuccessResponseDto> deleteComment(
            @PathVariable Long commentId,
            HttpServletRequest request
    ) {
        log.info("댓글 삭제 API 호출");
        commentService.deleteComment(getLoginMemberId(request), commentId);
        return SuccessResponseDto.successOkResponse("댓글이 삭제 되었습니다.");
    }

    private Long getLoginMemberId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return loginMember.getId();
    }
}

