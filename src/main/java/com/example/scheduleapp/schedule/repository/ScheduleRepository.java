package com.example.scheduleapp.schedule.repository;


import com.example.scheduleapp.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /*일정 고유 식별자를 통해 일정 조회*/
    Optional<Schedule> findById(Long scheduleId);

    /*페이징*/
    Page<Schedule> findAll(Pageable pageable);
}
