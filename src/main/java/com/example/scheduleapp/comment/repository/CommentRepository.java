package com.example.scheduleapp.comment.repository;

import com.example.scheduleapp.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    //todo: 예외 처리부분은 service로 따로 빼기 (아이디 부분은 따로 정의를 하지 않아도 된다.
    default Comment findByIdOrElseThrow(long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."));
    }

    Long countByScheduleId(Long id);
}
