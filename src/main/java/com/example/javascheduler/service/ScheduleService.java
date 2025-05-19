package com.example.javascheduler.service;

import com.example.javascheduler.dto.ScheduleRequestDto;
import com.example.javascheduler.dto.ScheduleResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
}
