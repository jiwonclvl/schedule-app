package com.example.scheduleapp.schedule.controller;

import com.example.scheduleapp.global.docs.ScheduleControllerDocs;
import com.example.scheduleapp.global.dto.SuccessResponseDto;
import com.example.scheduleapp.global.dto.SuccessWithDataResponseDto;
import com.example.scheduleapp.global.filter.SessionConst;
import com.example.scheduleapp.member.entity.Member;
import com.example.scheduleapp.schedule.dto.request.ScheduleRequestDto;
import com.example.scheduleapp.schedule.dto.request.UpdateScheduleRequestDto;
import com.example.scheduleapp.schedule.dto.response.SchedulePageResponseDto;
import com.example.scheduleapp.schedule.dto.response.ScheduleResponseDto;
import com.example.scheduleapp.schedule.service.ScheduleServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.example.scheduleapp.global.dto.SuccessResponseDto.successOkResponse;

@Slf4j
@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController implements ScheduleControllerDocs {

    private final ScheduleServiceImpl scheduleService;

    @PostMapping
    public ResponseEntity<SuccessWithDataResponseDto<ScheduleResponseDto>> createSchedule(
            @Valid @RequestBody ScheduleRequestDto dto,
            HttpServletRequest request
    ) {
        log.info("일정 생성 API 호출");

        /*session으로 유저 정보 가져오기*/
        ScheduleResponseDto scheduleResponseDto = scheduleService.creatSchedule(getLoginMemberId(request), dto.getTitle(), dto.getContents());
        return SuccessWithDataResponseDto.successCreateResponse(HttpStatus.CREATED, "일정이 정상적으로 등록되었습니다.",scheduleResponseDto);
    }

    @GetMapping
    public ResponseEntity<SuccessWithDataResponseDto<Page<SchedulePageResponseDto>>> getSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        log.info("일정 전체 조회 API 호출");
        Page<SchedulePageResponseDto> schedules = scheduleService.getSchedules(page, pageSize);
        return SuccessWithDataResponseDto.successOkWithDataResponse(HttpStatus.OK, "일정 전체 조회에 성공하였습니다.",schedules);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<SuccessWithDataResponseDto<ScheduleResponseDto>> getSchedule(@PathVariable Long scheduleId) {
        log.info("일정 단건 조회 API 호출");

        ScheduleResponseDto schedule = scheduleService.getSchedule(scheduleId);
        return SuccessWithDataResponseDto.successOkWithDataResponse(HttpStatus.OK,"일정 단건 조회에 성공하였습니다.", schedule);
    }

    @PatchMapping("/{scheduleId}/update")
    public ResponseEntity<SuccessWithDataResponseDto<ScheduleResponseDto>> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequestDto dto,
            HttpServletRequest request
    ) {
        log.info("일정 수정 API 호출");

        ScheduleResponseDto scheduleResponseDto = scheduleService.updateSchedule(getLoginMemberId(request), scheduleId, dto.getTitle(), dto.getContents());

        return SuccessWithDataResponseDto.successOkWithDataResponse(HttpStatus.OK, "일정 성공적으로 수정되었습니다.", scheduleResponseDto);
    }

    @DeleteMapping("/{scheduleId}/delete")
    public ResponseEntity<SuccessResponseDto> updateSchedule(
            @PathVariable Long scheduleId,
            HttpServletRequest request
    ) {
        log.info("일정 삭제 API 호출");

        scheduleService.deleteSchedule(getLoginMemberId(request), scheduleId);
        return successOkResponse("일정이 정상적으로 삭제되었습니다.");
    }

    private Long getLoginMemberId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return loginMember.getId();
    }
}
