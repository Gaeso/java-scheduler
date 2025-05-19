package com.example.javascheduler.repository;

import com.example.javascheduler.dto.ScheduleResponseDto;
import com.example.javascheduler.entity.Schedule;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
}
