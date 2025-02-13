package com.example.scheduleapp.schedule.service;

import com.example.scheduleapp.global.exception.ErrorCode;
import com.example.scheduleapp.global.exception.custom.EntityNotFoundException;
import com.example.scheduleapp.global.exception.custom.ForbiddenException;
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
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl {

    private final ScheduleRepository scheduleRepository;
    private final MemberServiceImpl memberService;
    private final CommentRepository commentRepository;

    /**
     * 사용자의 일정을 생성한다.
     *
     * 주어진 사용자 ID가 유효한지 확인한 후, 새로운 일정을 생성하고 저장한다.
     *
     * @param loginMember 일정을 생성할 로그인된 사용자의 ID
     * @param title 일정의 제목
     * @param contents 일정의 내용
     * @return 생성된 일정의 정보를 담은 ScheduleResponseDto (일정 ID, 유저명, 제목, 내용, 작성일, 수정일)
     * @throws EntityNotFoundException 해당 id의 사용자가 존재하지 않는 경우 발생
     */
    @Transactional
    public ScheduleResponseDto creatSchedule(Long loginMember, String title, String contents) {

        Member member = memberService.findUserById(loginMember);
        Schedule schedule = new Schedule(title, contents);
        schedule.updateMember(member);

        /*일정 등록*/
        Schedule saveSchedule = scheduleRepository.save(schedule);

        log.info("일정 등록 성공");

        return new ScheduleResponseDto(
                saveSchedule.getId(),
                member.getUsername(),
                saveSchedule.getTitle(),
                saveSchedule.getContents(),
                saveSchedule.getCreatedAt(),
                saveSchedule.getUpdatedAt()
        );
    }

    /**
     * 일정 목록을 페이징하여 조회한다.
     *
     * 주어진 페이지와 페이지 크기에 맞는 일정을 조회하여 반환한다.
     *
     * @param page 조회할 페이지 번호
     * @param pageSize 한 페이지에 보여줄 일정의 수
     * @return 페이징된 일정 목록을 담은 Page 객체
     */
    @Transactional(readOnly = true)
    public Page<SchedulePageResponseDto> getSchedules(int page, int pageSize) {

        /*수정일을 기준으로 정렬*/
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("updatedAt").descending());
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);

        log.info("일정 전체 성공");

        /**
         * 서비스 사용 시 순환 참조 문제 발생! 시간 부족으로 해결하지 못함
         * 따라서 임의로 레포지토리에서 값을 가져오도록 하였다.
         * 추후 찾아보고 수정할 예정
         */
        return schedulePage.map(schedule -> new SchedulePageResponseDto(
                schedule.getMember().getUsername(),
                schedule.getTitle(),
                schedule.getContents(),
                commentRepository.countByScheduleId(schedule.getId()),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        ));
    }

    /**
     * 특정 일정을 조회한다.
     *
     * 주어진 일정 ID로 일정을 조회하여 해당 일정을 반환한다.
     *
     * @param scheduleId 조회할 일정의 ID
     * @return 조회된 일정의 정보를 담은 ScheduleResponseDto (일정 ID, 유저명, 제목, 내용, 작성일, 수정일)
     * @throws EntityNotFoundException 해당 일정이 존재하지 않는 경우 발생
     */
    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(Long scheduleId) {
        Schedule schedule = findScheduleById(scheduleId);

        log.info("일정 단건 조회 성공");

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getMember().getUsername(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    /**
     * 일정을 수정한다.
     *
     * 로그인된 사용자가 해당 일정을 수정할 수 있는지 확인하고, 일정의 제목과 내용을 수정한다.
     *
     * @param loginMember 로그인된 사용자의 ID
     * @param scheduleId 수정할 일정의 ID
     * @param title 변경된 제목
     * @param contents 변경된 내용
     * @return 수정된 일정의 정보를 담은 ScheduleResponseDto 객체
     * @throws ForbiddenException 로그인된 사용자가 다른 사용자의 일정을 수정하려는 경우 발생
     */
    @Transactional
    public ScheduleResponseDto updateSchedule(Long loginMember, Long scheduleId, String title, String contents) {

        Schedule schedule = findScheduleById(scheduleId);
        Long userId = schedule.getMember().getId();

        if (!Objects.equals(userId, loginMember)) {
            throw new ForbiddenException(ErrorCode.CANNOT_UPDATE_OTHERS_DATA);
        }

        // 둘 다 변경된 경우 (title은 null 허용 X, contents는 null 허용)
        if (!schedule.getTitle().equals(title) && !Objects.equals(schedule.getContents(), contents)) {
            schedule.updateTitle(title);
            schedule.updateContents(contents);
        }

        // title만 변경된 경우
        if (!schedule.getTitle().equals(title) && Objects.equals(schedule.getContents(), contents)) {
            schedule.updateTitle(title);
        }

        // contents만 변경된 경우
        if (!Objects.equals(schedule.getContents(), contents) && schedule.getTitle().equals(title)) {
            schedule.updateContents(contents);
        }

        log.info("일정 수정 조회 성공");

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getMember().getUsername(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    /**
     * 일정을 삭제한다.
     *
     * 로그인된 사용자가 해당 일정을 삭제할 수 있는지 확인한 후 일정을 삭제한다.
     *
     * @param loginMember 로그인된 사용자의 ID
     * @param scheduleId 삭제할 일정의 ID
     * @throws ForbiddenException 로그인된 사용자가 다른 사용자의 일정을 삭제하려는 경우 발생
     */
    @Transactional
    public void deleteSchedule(Long loginMember, Long scheduleId) {

        Schedule schedule = findScheduleById(scheduleId);

        if (!Objects.equals(loginMember, schedule.getMember().getId())) {
            throw new ForbiddenException(ErrorCode.CANNOT_UPDATE_OTHERS_DATA);
        }

        scheduleRepository.delete(schedule);

        log.info("일정 삭제 조회 성공");
    }

    /**
     * 일정 ID로 일정을 조회하는 메서드이다.
     *
     * 주어진 ID로 일정을 조회하며, 일정이 존재하지 않으면 예외를 발생시킨다.
     *
     * @param scheduleId 조회할 일정의 ID
     * @return 조회된 일정 객체
     * @throws EntityNotFoundException 해당 일정이 존재하지 않는 경우 발생
     */
    public Schedule findScheduleById(Long scheduleId) {
        Optional<Schedule> findScheduleById = scheduleRepository.findById(scheduleId);

        if (findScheduleById.isEmpty()) {
            throw new EntityNotFoundException(ErrorCode.NOT_FOUND);
        }
        return findScheduleById.get();
    }

}
