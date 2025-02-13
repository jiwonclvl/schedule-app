package com.example.scheduleapp.schedule.repository;


import com.example.scheduleapp.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /*일정 고유 식별자를 통해 일정 조회*/
    Optional<Schedule> findById(Long scheduleId);
}
