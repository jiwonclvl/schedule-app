package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.response.CommentResponseDto;
import com.example.scheduleapp.entity.Comment;
import com.example.scheduleapp.entity.Member;
import com.example.scheduleapp.entity.Schedule;
import com.example.scheduleapp.repository.CommentRepository;
import com.example.scheduleapp.repository.MemberRepository;
import com.example.scheduleapp.repository.ScheduleRepository;
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
public class CommentServiceImpl implements CommentService {

    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentResponseDto createComment(Long scheduleId, Long httpSessionId, String content) {
        //유저와 일정이 모두 있어야 댓글이 존재할 수 있다. (또한 유저는 일정을 선택하여 댓글 등록을 할 수 있다.)
        Member member = memberRepository.findUserByIdOrElseThrow(httpSessionId);
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

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
    @Override
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

    @Override
    @Transactional
    public void updateComment(Long commentId, String comment) {
        Comment findComment = commentRepository.findByIdOrElseThrow(commentId);

        //댓글 수정
        findComment.updateComment(comment);

        log.info("댓글 수정 완료");
    }

    private String localDateTimeFormat(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
