package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.response.ScheduleResponseDto;
import com.example.scheduleapp.entity.Member;
import com.example.scheduleapp.entity.Schedule;
import com.example.scheduleapp.repository.MemberRepository;
import com.example.scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public ScheduleResponseDto creatSchedule(Long id, String title, String contents) {

        //유저가 있어야 일정이 존재할 수 있다.
        Member member = memberRepository.findUserByIdOrElseThrow(id);
        Schedule schedule = new Schedule(title, contents);
        schedule.setMember(member);

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

    @Override
    public List<ScheduleResponseDto> getSchedules() {
        List<ScheduleResponseDto> scheduleList = scheduleRepository.findAll()
                .stream()
                .map(ScheduleResponseDto::ScheduleDto)
                .toList();

        if(scheduleList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다.");
        }

        log.info("일정 전체 성공");

        return scheduleList;
    }

    @Override
    public ScheduleResponseDto getSchedule(Long ScheduleId) {
        Schedule findschedule = scheduleRepository.findByIdOrElseThrow(ScheduleId);

        log.info("일정 단건 조회 성공");

        return new ScheduleResponseDto(
                findschedule.getId(),
                findschedule.getMember().getUsername(),
                findschedule.getTitle(),
                findschedule.getContents(),
                localDateTimeFormat(findschedule.getCreatedAt()),
                localDateTimeFormat(findschedule.getUpdatedAt())
        );
    }

    @Override
    @Transactional
    public ScheduleResponseDto updateSchedule(Long scheduleId, String title, String contents) {
        Schedule findschedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

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

        findschedule.updateTitle(title);
        findschedule.updateContents(contents);

        System.out.println("findschedule.getTitle() = " + findschedule.getTitle());
        System.out.println("findschedule.getContent() = " + findschedule.getContents());

        log.info("일정 수정 조회 성공");

        return new ScheduleResponseDto(
                findschedule.getId(),
                findschedule.getMember().getUsername(),
                findschedule.getTitle(),
                findschedule.getContents(),
                localDateTimeFormat(findschedule.getCreatedAt()),
                localDateTimeFormat(findschedule.getUpdatedAt())
        );
    }


    private String localDateTimeFormat(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
