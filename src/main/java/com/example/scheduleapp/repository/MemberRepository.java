package com.example.scheduleapp.repository;


import com.example.scheduleapp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findUserByEmail(String email);

    default Member findUserByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    default Member findUserByEmailOrElseThrow(String email) {
        return findUserByEmail(email).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
