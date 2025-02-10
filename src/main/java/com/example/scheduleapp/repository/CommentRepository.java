package com.example.scheduleapp.repository;

import com.example.scheduleapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findByIdOrElseThrow(long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."));
    }
}
