package com.example.scheduleapp.schedule.repository;


import com.example.scheduleapp.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    default Schedule findByIdOrElseThrow(Long scheduleId) {
        return findById(scheduleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));
    }

    Page<Schedule> findAll(Pageable pageable);
}
