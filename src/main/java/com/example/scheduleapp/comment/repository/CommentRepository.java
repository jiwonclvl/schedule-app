package com.example.scheduleapp.comment.repository;

import com.example.scheduleapp.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countByScheduleId(Long id);
}
