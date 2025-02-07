package com.example.scheduleapp.repository;


import com.example.scheduleapp.entity.Member;
import com.example.scheduleapp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
