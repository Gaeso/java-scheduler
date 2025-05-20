package com.example.javascheduler.service;

import com.example.javascheduler.dto.ScheduleListDto;
import com.example.javascheduler.dto.ScheduleRequestDto;
import com.example.javascheduler.dto.ScheduleResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    List<ScheduleListDto> findAllByCondition(LocalDate date, String author);
    ScheduleListDto findScheduleById(Long id);
    ScheduleListDto updateScheduleById(Long id, ScheduleRequestDto dto);
}
