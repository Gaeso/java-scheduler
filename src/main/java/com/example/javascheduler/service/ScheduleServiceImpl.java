package com.example.javascheduler.service;

import com.example.javascheduler.dto.ScheduleRequestDto;
import com.example.javascheduler.dto.ScheduleResponseDto;
import com.example.javascheduler.dto.UpdateResponseDto;
import com.example.javascheduler.entity.Schedule;
import com.example.javascheduler.entity.User;
import com.example.javascheduler.repository.ScheduleRepository;
import com.example.javascheduler.repository.UserRepository;
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
    private final UserRepository userRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        User user = userRepository.findUserById(dto.getUserId());
        LocalDateTime now = LocalDateTime.now();

        Schedule savedSchedule = new Schedule(dto.getUserId(), dto.getContent(), dto.getPassword(), now, now);
        savedSchedule = scheduleRepository.saveSchedule(savedSchedule);
        return new ScheduleResponseDto(savedSchedule, user);
    }

    @Override
    public List<ScheduleResponseDto> findAllByCondition(LocalDate date, Long userId) {
        List<Schedule> schedules = scheduleRepository.findAllByCondition(date,userId);
        List<ScheduleResponseDto> dtos = new ArrayList<>();

        if(userId != null)
        {
            userRepository.findUserById(userId);
        }

        for(Schedule schedule : schedules) {
            User user = userRepository.findUserById(schedule.getUserId());
            dtos.add(new ScheduleResponseDto(schedule, user));
        }

        return dtos;
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        final User user = userRepository.findUserById(scheduleRepository.findScheduleById(id).getUserId());
        return new ScheduleResponseDto(scheduleRepository.findScheduleById(id), user);
    }

    @Override
    public ScheduleResponseDto updateScheduleById(Long id, UpdateResponseDto dto) {

        if(dto.getContent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        LocalDateTime now = LocalDateTime.now();

        Schedule schedule = scheduleRepository.findScheduleById(id);

        if(!schedule.getPassword().equals(dto.getPassword()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        int updatedRow = scheduleRepository.updateScheduleById(id, dto.getContent(), now);
        if(updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        schedule = scheduleRepository.findScheduleById(id);
        schedule.setUpdated_at(now);

        User user = userRepository.findUserById(schedule.getUserId());
        user.setName(dto.getName());
        int updatedUserRow = userRepository.updateName(user);
        if(updatedUserRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return new ScheduleResponseDto(schedule, user);
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
