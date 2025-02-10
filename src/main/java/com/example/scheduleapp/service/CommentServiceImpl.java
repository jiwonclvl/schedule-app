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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

        //댓글 생성
        Comment comment = new Comment(content, member, schedule);

        //값 저장하기
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                savedComment.getId(),
                savedComment.getMember().getId(),
                savedComment.getSchedule().getId(),
                savedComment.getContent(),
                localDateTimeFormat(savedComment.getCreatedAt()),
                localDateTimeFormat(savedComment.getUpdatedAt())
        );
    }

    private String localDateTimeFormat(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
