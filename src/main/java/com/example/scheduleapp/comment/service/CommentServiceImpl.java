package com.example.scheduleapp.comment.service;

import com.example.scheduleapp.comment.dto.response.CommentResponseDto;
import com.example.scheduleapp.comment.entity.Comment;
import com.example.scheduleapp.global.exception.ErrorCode;
import com.example.scheduleapp.global.exception.custom.EntityNotFoundException;
import com.example.scheduleapp.global.exception.custom.ForbiddenException;
import com.example.scheduleapp.member.entity.Member;
import com.example.scheduleapp.member.service.MemberServiceImpl;
import com.example.scheduleapp.schedule.entity.Schedule;
import com.example.scheduleapp.comment.repository.CommentRepository;
import com.example.scheduleapp.schedule.service.ScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl {

    private final MemberServiceImpl memberService;
    private final ScheduleServiceImpl scheduleService;
    private final CommentRepository commentRepository;

    /**
     * 댓글을 생성한다.
     *
     * @param scheduleId 댓글이 작성될 일정의 ID
     * @param loginMember 로그인된 사용자의 ID
     * @param content 댓글 내용
     * @return 생성된 댓글 정보가 담긴 CommentResponseDto (댓글 ID, 유저 ID, 일정 ID, 내용, 작성일, 수정일)
     * @throws EntityNotFoundException 해당 id의 사용자가 존재하지 않거나 일정이 존재하지 않는 경우 발생
     */
    @Transactional
    public CommentResponseDto createComment(Long scheduleId, Long loginMember, String content) {

        Member member = memberService.findUserById(loginMember);
        Schedule schedule = scheduleService.findScheduleById(scheduleId);

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
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt()
        );
    }

    /**
     * 모든 댓글을 조회한다.
     *
     * @return 댓글 리스트
     */
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments() {
        List<CommentResponseDto> commentList = commentRepository.findAll()
                .stream()
                .map(CommentResponseDto::commentDto)
                .toList();

        log.info("댓글 전체 조회 완료");

        return commentList;
    }

    /**
     * 특정 댓글을 조회한다.
     *
     * @param commentId 조회할 댓글의 ID
     * @return 조회된 댓글 정보가 담긴 CommentResponseDto (댓글 ID, 유저 ID, 일정 ID, 내용, 작성일, 수정일)
     * @throws EntityNotFoundException 댓글이 존재하지 않는 경우 예외 발생
     */
    @Transactional(readOnly = true)
    public CommentResponseDto getComment(Long commentId) {
        Comment commentById = findCommentById(commentId);

        log.info("댓글 단건 조회 완료");

        return new CommentResponseDto(
                commentById.getId(),
                commentById.getMember().getId(),
                commentById.getSchedule().getId(),
                commentById.getContent(),
                commentById.getCreatedAt(),
                commentById.getUpdatedAt()
        );

    }

    /**
     * 댓글을 수정한다.
     *
     * @param loginMember 로그인된 사용자의 ID
     * @param commentId 수정할 댓글의 ID
     * @param comment 수정할 내용
     * @return 수정된 댓글 정보가 담긴 CommentResponseDto (댓글 ID, 유저 ID, 일정 ID, 내용, 작성일, 수정일)
     * @throws EntityNotFoundException 해당 아이디의 댓글이 존재하지 않는 경우 발생
     * @throws ForbiddenException 댓글을 수정할 권한이 없는 경우 예외 발생
     */
    @Transactional
    public CommentResponseDto updateComment(Long loginMember, Long commentId, String comment) {
        Comment findComment = findCommentById(commentId);

        /*권한이 없는 경우*/
        if (!Objects.equals(loginMember, findComment.getMember().getId())) {
            throw new ForbiddenException(ErrorCode.CANNOT_UPDATE_OTHERS_DATA);
        }

        if (!findComment.getContent().equals(comment)) {
            findComment.updateComment(comment);
        }

        log.info("댓글 수정 완료");

        return new CommentResponseDto(
                findComment.getId(),
                findComment.getMember().getId(),
                findComment.getSchedule().getId(),
                findComment.getContent(),
                findComment.getCreatedAt(),
                findComment.getUpdatedAt()
        );
    }

    /**
     * 댓글을 삭제하는 메소드
     *
     * @param loginMember 로그인된 사용자의 ID
     * @param commentId 삭제할 댓글의 ID
     * @throws ForbiddenException 댓글을 삭제할 권한이 없는 경우 예외 발생
     */
    public void deleteComment(Long loginMember,Long commentId) {
        Comment findComment = findCommentById(commentId);

        if(!Objects.equals(loginMember, findComment.getMember().getId())) {
            throw new ForbiddenException(ErrorCode.CANNOT_UPDATE_OTHERS_DATA);
        }

        //댓글 삭제
        commentRepository.delete(findComment);
        log.info("댓글 삭제 완료");
    }

    /**
     * 댓글 ID를 이용해 댓글을 조회하는 메소드이다.
     *
     * @param commentId 조회할 댓글의 ID
     * @return 조회된 댓글 객체
     * @throws EntityNotFoundException 댓글이 존재하지 않는 경우 예외 발생
     */
    private Comment findCommentById(Long commentId) {
        Optional<Comment> commentbyId = commentRepository.findById(commentId);

        if (commentbyId.isEmpty()) {
            throw new EntityNotFoundException(ErrorCode.NOT_FOUND);
        }

        return commentbyId.get();
    }
}
