package com.example.scheduleapp.comment.service;

import com.example.scheduleapp.comment.dto.response.CommentResponseDto;
import com.example.scheduleapp.comment.entity.Comment;
import com.example.scheduleapp.member.entity.Member;
import com.example.scheduleapp.member.service.MemberServiceImpl;
import com.example.scheduleapp.schedule.entity.Schedule;
import com.example.scheduleapp.comment.repository.CommentRepository;
import com.example.scheduleapp.schedule.service.ScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl {

    //Service가 있어야 한다.
    private final MemberServiceImpl memberService;
    private final ScheduleServiceImpl scheduleService;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto createComment(Long scheduleId, Long httpSessionId, String content) {

        Member member = memberService.getUserById(httpSessionId);
        Schedule schedule = scheduleService.findScheduleById(scheduleId);

        log.info("유저 및 일정 확인 완료");

        //댓글 생성
        Comment comment = new Comment(content, member, schedule);

        //값 저장하기
        Comment savedComment = commentRepository.save(comment);

        log.info("댓글 생성 완료");
        return new CommentResponseDto(
                savedComment.getId(),
                savedComment.getMember().getId(),
                savedComment.getSchedule().getId(),
                savedComment.getContent(),
                localDateTimeFormat(savedComment.getCreatedAt()),
                localDateTimeFormat(savedComment.getUpdatedAt())
        );
    }

    //todo: 댓글의 날짜 출력 형식 변경하기
    @Transactional
    public void updateComment(Long commentId, String comment) {
        Comment findComment = commentRepository.findByIdOrElseThrow(commentId);

        //todo: 입력한 댓글이 비어있다면 기존 값 유지
        //댓글 수정
        findComment.updateComment(comment);

        log.info("댓글 수정 완료");
    }

    public List<CommentResponseDto> getComments() {
        List<CommentResponseDto> commentList = commentRepository.findAll()
                .stream()
                .map(CommentResponseDto::commentDto)
                .toList();

        if (commentList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다.");
        }

        log.info("댓글 조회 완료");

        return commentList;
    }

    public void deleteComment(Long commentId) {
        Comment findComment = commentRepository.findByIdOrElseThrow(commentId);

        //댓글 삭제
        commentRepository.delete(findComment);
        log.info("댓글 삭제 완료");
    }

    private String localDateTimeFormat(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
