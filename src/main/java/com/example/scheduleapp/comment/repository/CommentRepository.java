package com.example.scheduleapp.comment.repository;

import com.example.scheduleapp.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentById(Long commentId);

    Long countByScheduleId(Long id);
}
