package com.example.scheduleapp.schedule.service;

import com.example.scheduleapp.global.exception.ErrorCode;
import com.example.scheduleapp.global.exception.custom.EntityNotFoundException;
import com.example.scheduleapp.member.service.MemberServiceImpl;
import com.example.scheduleapp.schedule.dto.response.SchedulePageResponseDto;
import com.example.scheduleapp.schedule.dto.response.ScheduleResponseDto;
import com.example.scheduleapp.member.entity.Member;
import com.example.scheduleapp.schedule.entity.Schedule;
import com.example.scheduleapp.comment.repository.CommentRepository;
import com.example.scheduleapp.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl{

    private final ScheduleRepository scheduleRepository;
    private final MemberServiceImpl memberService;
    private final CommentRepository commentRepository;

    @Transactional
    public ScheduleResponseDto creatSchedule(Long id, String title, String contents) {

        //유저가 있어야 일정이 존재할 수 있다.
        Member member = memberService.getUserByIdOrElseThrow(id);
        Schedule schedule = new Schedule(title, contents);
        schedule.setMember(member);

        /*일정 등록*/
        Schedule saveSchedule = scheduleRepository.save(schedule);

        log.info("일정 등록 성공");

        return new ScheduleResponseDto(
                saveSchedule.getId(),
                member.getUsername(),
                saveSchedule.getTitle(),
                saveSchedule.getContents(),
                localDateTimeFormat(saveSchedule.getCreatedAt()),
                localDateTimeFormat(saveSchedule.getUpdatedAt())
        );
    }

    //todo: 일정 조회 시 날짜 출력 형식 변경하기
    //todo: 댓글 수정 후 수정하기
    public List<SchedulePageResponseDto> getSchedules(int page, int pageSize) {

        //페이지 객체 생성
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("updatedAt").descending());
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);

        List<SchedulePageResponseDto> schedulePagelist = schedulePage.getContent().stream()
                .map(schedule -> {
                    Long totalComment = commentRepository.countByScheduleId(schedule.getId());
                    return new SchedulePageResponseDto(
                            schedule.getMember().getUsername(),
                            schedule.getTitle(),
                            schedule.getContents(),
                            totalComment,
                            schedule.getCreatedAt(),
                            schedule.getUpdatedAt()
                    );
                })
                .toList();

        log.info("일정 전체 성공");
        return schedulePagelist;
    }

    public ScheduleResponseDto getSchedule(Long scheduleId) {
        Schedule schedule = findScheduleById(scheduleId);

        log.info("일정 단건 조회 성공");

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getMember().getUsername(),
                schedule.getTitle(),
                schedule.getContents(),
                localDateTimeFormat(schedule.getCreatedAt()),
                localDateTimeFormat(schedule.getUpdatedAt())
        );
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long scheduleId, String title, String contents) {
        Schedule schedule = findScheduleById(scheduleId);

        /* todo: 예외처리 추가하기
        //둘 다 비어 있는 경우 기존 값 유지
        if(!StringUtils.hasText(title) && !StringUtils.hasText(contents)) {

        }
        //title만 있는 경우
        if(!StringUtils.hasText(title) && !StringUtils.hasText(contents)) {

        }
        //contents만 있는 경우
        if(!StringUtils.hasText(title) && !StringUtils.hasText(contents)) {

        } */

        //todo: 여러 사용자의 일정이 등록되어 있는 경우 다른 사용자의 수정을 하려고 하면 예외 처리해야함
        schedule.updateTitle(title);
        schedule.updateContents(contents);

        log.info("일정 수정 조회 성공");

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getMember().getUsername(),
                schedule.getTitle(),
                schedule.getContents(),
                localDateTimeFormat(schedule.getCreatedAt()),
                localDateTimeFormat(schedule.getUpdatedAt())
        );
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {

        Schedule schedule = findScheduleById(scheduleId);
        scheduleRepository.delete(schedule);

        log.info("일정 삭제 조회 성공");
    }

    private Schedule findScheduleById(Long scheduleId) {
        Optional<Schedule> findScheduleById = scheduleRepository.findById(scheduleId);

        /*일정이 없는 경우 예외*/
        if(findScheduleById.isEmpty()) {
            throw new EntityNotFoundException(ErrorCode.NOT_FOUND);
        }

        return findScheduleById.get();
    }
    private String localDateTimeFormat(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
