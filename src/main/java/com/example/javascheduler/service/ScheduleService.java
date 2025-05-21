package com.example.javascheduler.service;

import com.example.javascheduler.dto.DeleteRequestDto;
import com.example.javascheduler.dto.ScheduleRequestDto;
import com.example.javascheduler.dto.ScheduleResponseDto;
import com.example.javascheduler.dto.UpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    List<ScheduleResponseDto> findAllByCondition(LocalDate date, Long userId);
    ScheduleResponseDto findScheduleById(Long id);
    ScheduleResponseDto updateScheduleById(Long id, UpdateRequestDto dto);
    void deleteScheduleById(Long id, DeleteRequestDto dto);
    Page<ScheduleResponseDto> findSchedulePage(LocalDate date, Long userId, Integer page, Integer size);
}
