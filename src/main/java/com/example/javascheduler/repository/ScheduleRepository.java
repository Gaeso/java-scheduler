package com.example.javascheduler.repository;

import com.example.javascheduler.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository {
    Schedule saveSchedule(Schedule schedule);
    List<Schedule> findAllByCondition(LocalDate date, Long userId);
    List<Schedule> findPageByCondition(LocalDate date, Long userId, int offset, int limit);
    Schedule findScheduleById(Long id);
    int updateScheduleById(Long id, String content, LocalDateTime now);
    int deleteScheduleById(Long id);
    long countAll(LocalDate date, Long userId);
}
