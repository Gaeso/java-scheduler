package com.example.javascheduler.controller;

import com.example.javascheduler.dto.ScheduleRequestDto;
import com.example.javascheduler.dto.ScheduleResponseDto;
import com.example.javascheduler.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class SchedulerController {
    private final ScheduleService scheduleService;

    public SchedulerController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 생성 API
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody ScheduleRequestDto dto
    ) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    // 일정 조회 API (수정일 & 작성자 기준)
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> readAllByCondition(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(required = false) String author
    ) {
        return new ResponseEntity<>(scheduleService.findAllByCondition(date, author), HttpStatus.OK);
    }
}
