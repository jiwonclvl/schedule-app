package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.response.ScheduleResponseDto;
import com.example.scheduleapp.entity.Member;
import com.example.scheduleapp.entity.Schedule;
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

    private String localDateTimeFormat(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
