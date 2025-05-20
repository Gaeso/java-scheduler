package com.example.javascheduler.repository;

import com.example.javascheduler.dto.ScheduleResponseDto;
import com.example.javascheduler.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<Schedule> findAllByCondition(LocalDate date, String author);
    Schedule findScheduleById(Long id);
    int updateScheduleById(Long id, String author, String content, LocalDateTime now);
    int deleteScheduleById(Long id);
}
