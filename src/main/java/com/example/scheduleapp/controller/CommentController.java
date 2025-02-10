package com.example.scheduleapp.controller;

import com.example.scheduleapp.dto.request.CommentRequestDto;
import com.example.scheduleapp.dto.response.CommentResponseDto;
import com.example.scheduleapp.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        //세션에 담긴 값 넘겨주기 (유저의 id값이 담겨있다.)
        Long httpSessionId = getHttpSessionId(request);
        return new ResponseEntity<>(commentService.createComment(scheduleId, httpSessionId, dto.getContent()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments() {

        return new ResponseEntity<>(commentService.getComments(), HttpStatus.OK);
    }

    private Long getHttpSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (Long) session.getAttribute("id");
    }
}
