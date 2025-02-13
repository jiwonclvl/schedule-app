package com.example.scheduleapp.global.docs;

import com.example.scheduleapp.comment.dto.request.CommentRequestDto;
import com.example.scheduleapp.comment.dto.response.CommentResponseDto;
import com.example.scheduleapp.global.dto.SuccessResponseDto;
import com.example.scheduleapp.global.dto.SuccessWithDataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "댓글 CRUD 관리", description = "일정 댓글의 등록, 조회, 수정, 삭제를 관리하는 API입니다.")
public interface CommentControllerDocs {
    @Operation(
            summary = "댓글 생성",
            description = "주어진 일정 ID에 대해 댓글을 생성하는 API입니다."
    )
    ResponseEntity<SuccessWithDataResponseDto<CommentResponseDto>> createComment(
            @Parameter(description = "댓글을 추가할 일정 ID", required = true) @PathVariable Long scheduleId,
            @Parameter(description = "댓글 생성 요청 DTO") @RequestBody CommentRequestDto dto,
            @Parameter(description = "요청을 보낸 사용자 세션 정보") HttpServletRequest request
    );

    @Operation(
            summary = "댓글 전체 조회",
            description = "등록된 모든 댓글을 조회하는 API입니다."
    )
    ResponseEntity<SuccessWithDataResponseDto<List<CommentResponseDto>>> getComments(
    );

    @Operation(
            summary = "댓글 단건 조회",
            description = "주어진 댓글 ID로 댓글을 조회하는 API입니다."
    )
    ResponseEntity<SuccessWithDataResponseDto<CommentResponseDto>> getComment(
            @Parameter(description = "조회할 댓글 ID", required = true) @PathVariable Long commentId
    );

    @Operation(
            summary = "댓글 수정",
            description = "주어진 댓글 ID에 대해 댓글 내용을 수정하는 API입니다."
    )
    ResponseEntity<SuccessWithDataResponseDto<CommentResponseDto>> updateComment(
            @Parameter(description = "수정할 댓글 ID", required = true) @PathVariable Long commentId,
            @Parameter(description = "수정할 댓글 내용") @RequestBody CommentRequestDto dto,
            @Parameter(description = "요청을 보낸 사용자 세션 정보") HttpServletRequest request
    );

    @Operation(
            summary = "댓글 삭제",
            description = "주어진 댓글 ID에 대해 댓글을 삭제하는 API입니다."
    )
    ResponseEntity<SuccessResponseDto> deleteComment(
            @Parameter(description = "삭제할 댓글 ID", required = true) @PathVariable Long commentId,
            @Parameter(description = "요청을 보낸 사용자 세션 정보") HttpServletRequest request
    );
}
