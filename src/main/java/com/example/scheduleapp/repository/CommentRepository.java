package com.example.scheduleapp.repository;

import com.example.scheduleapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
}
