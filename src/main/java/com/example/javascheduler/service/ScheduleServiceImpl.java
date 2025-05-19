package com.example.javascheduler.service;

import com.example.javascheduler.dto.ScheduleRequestDto;
import com.example.javascheduler.dto.ScheduleResponseDto;
import com.example.javascheduler.entity.Schedule;
import com.example.javascheduler.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getAuthor(), dto.getContent(), dto.getPassword());
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllByCondition(LocalDate date, String author) {
        if(date == null && author == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<Schedule> schedules = scheduleRepository.findAllByCondition(date,author);
        List<ScheduleResponseDto> dtos = new ArrayList<>();

        for(Schedule schedule : schedules) {
            dtos.add(new ScheduleResponseDto(schedule));
        }

        return dtos;
    }
}
