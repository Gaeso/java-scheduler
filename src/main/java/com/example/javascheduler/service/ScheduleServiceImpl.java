package com.example.javascheduler.service;

import com.example.javascheduler.dto.ScheduleListDto;
import com.example.javascheduler.dto.ScheduleRequestDto;
import com.example.javascheduler.dto.ScheduleResponseDto;
import com.example.javascheduler.entity.Schedule;
import com.example.javascheduler.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public List<ScheduleListDto> findAllByCondition(LocalDate date, String author) {

        List<Schedule> schedules = scheduleRepository.findAllByCondition(date,author);
        List<ScheduleListDto> dtos = new ArrayList<>();

        for(Schedule schedule : schedules) {
            dtos.add(new ScheduleListDto(schedule));
        }

        return dtos;
    }

    @Override
    public ScheduleListDto findScheduleById(Long id) {
        return new ScheduleListDto(scheduleRepository.findScheduleById(id));
    }

    @Override
    public ScheduleListDto updateScheduleById(Long id, ScheduleRequestDto dto) {

        if(dto.getAuthor() == null || dto.getContent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        LocalDateTime now = LocalDateTime.now();

        Schedule schedule = scheduleRepository.findScheduleById(id);

        if(!schedule.getPassword().equals(dto.getPassword()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        int updatedRow = scheduleRepository.updateScheduleById(id, dto.getAuthor(), dto.getContent(), now);
        if(updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        schedule = scheduleRepository.findScheduleById(id);
        schedule.setUpdated_at(now);

        return new ScheduleListDto(schedule);
    }

    @Override
    public void deleteScheduleById(Long id, ScheduleRequestDto dto) {

        Schedule schedule = scheduleRepository.findScheduleById(id);
        if(!schedule.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        int deletedRow = scheduleRepository.deleteScheduleById(id);
        if(deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


    }
}
