package com.example.javascheduler.service;

import com.example.javascheduler.dto.ScheduleRequestDto;
import com.example.javascheduler.dto.ScheduleResponseDto;
import com.example.javascheduler.dto.UpdateResponseDto;
import com.example.javascheduler.entity.Schedule;
import com.example.javascheduler.entity.User;
import com.example.javascheduler.repository.ScheduleRepository;
import com.example.javascheduler.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    // 일정 저장 및 생성
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        User user = userRepository.findUserById(dto.getUserId());
        LocalDateTime now = LocalDateTime.now();

        Schedule savedSchedule = new Schedule(dto.getUserId(), dto.getContent(), dto.getPassword(), now, now);
        savedSchedule = scheduleRepository.saveSchedule(savedSchedule);
        return new ScheduleResponseDto(savedSchedule, user);
    }

    // 조건별 일정 목록 조회
    @Override
    public List<ScheduleResponseDto> findAllByCondition(LocalDate date, Long userId) {
        List<Schedule> schedules = scheduleRepository.findAllByCondition(date, userId);
        List<ScheduleResponseDto> dtos = new ArrayList<>();

        if (userId != null) {
            userRepository.findUserById(userId);
        }

        for (Schedule schedule : schedules) {
            User user = userRepository.findUserById(schedule.getUserId());
            dtos.add(new ScheduleResponseDto(schedule, user));
        }

        return dtos;
    }

    /*
        페이징 처리된 일정 조회
        page (페이지 번호): 현재 페이지 위치 (보통 0 또는 1부터 시작)
        size (페이지 크기): 한 페이지에 표시할 아이템 수 (ex: 10개, 20개)
        limit: 한 페이지에 표시할 아이템 수 (= size)
        offset: 건너뛸 아이템 수 (시작 위치)
     */
    @Override
    public Page<ScheduleResponseDto> findSchedulePage(LocalDate date, Long userId, Integer page, Integer size) {
        long totalCount = scheduleRepository.countAll(date, userId);
        // 시작 페이지는 1
        int offset = (page - 1) * size;
        List<Schedule> schedules = scheduleRepository.findPageByCondition(date, userId, offset, size);

        List<ScheduleResponseDto> dtos = new ArrayList<>();

        if (userId != null) {
            userRepository.findUserById(userId);
        }

        for (Schedule schedule : schedules) {
            User user = userRepository.findUserById(schedule.getUserId());
            dtos.add(new ScheduleResponseDto(schedule, user));
        }

        return new PageImpl<>(
                dtos,
                PageRequest.of(page - 1, size),
                totalCount
        );
    }

    // 단일 일정 조회
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        final User user = userRepository.findUserById(scheduleRepository.findScheduleById(id).getUserId());
        return new ScheduleResponseDto(scheduleRepository.findScheduleById(id), user);
    }

    // 일정 수정
    @Override
    public ScheduleResponseDto updateScheduleById(Long id, UpdateResponseDto dto) {

        if (dto.getContent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        LocalDateTime now = LocalDateTime.now();

        Schedule schedule = scheduleRepository.findScheduleById(id);

        // 비밀번호 불일치 406 에러
        if (!schedule.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        // 일정 내용 업데이트
        int updatedRow = scheduleRepository.updateScheduleById(id, dto.getContent(), now);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


        schedule = scheduleRepository.findScheduleById(id);
        schedule.setUpdated_at(now);

        // 사용자 이름 업데이트
        User user = userRepository.findUserById(schedule.getUserId());
        user.setName(dto.getName());
        int updatedUserRow = userRepository.updateName(user);
        if (updatedUserRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return new ScheduleResponseDto(schedule, user);
    }

    // 일정 삭제
    @Override
    public void deleteScheduleById(Long id, ScheduleRequestDto dto) {

        Schedule schedule = scheduleRepository.findScheduleById(id);
        if (!schedule.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        int deletedRow = scheduleRepository.deleteScheduleById(id);
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
