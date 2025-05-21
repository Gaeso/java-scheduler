package com.example.javascheduler.controller;

import com.example.javascheduler.dto.DeleteRequestDto;
import com.example.javascheduler.dto.ScheduleRequestDto;
import com.example.javascheduler.dto.ScheduleResponseDto;
import com.example.javascheduler.dto.UpdateRequestDto;
import com.example.javascheduler.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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

    // 일정 생성 (POST)
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody @Valid ScheduleRequestDto dto
    ) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    // 조건별 일정 목록 조회 (수정일 & 작성자 기준)
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> readAllByCondition(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(required = false) Long userId
    ) {
        return new ResponseEntity<>(scheduleService.findAllByCondition(date, userId), HttpStatus.OK);
    }

    // 페이징 처리된 일정 조회 (GET)
    @GetMapping("/page")
    public ResponseEntity<Page<ScheduleResponseDto>> readScheduleWithPage(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                                          @RequestParam(required = false) Long userId,
                                                                          @RequestParam(required = false, defaultValue = "1") Integer page, // 기본값 1페이지
                                                                          @RequestParam(required = false, defaultValue = "5") Integer size // 페이지당 5개 항목
    ) {
        return new ResponseEntity<>(scheduleService.findSchedulePage(date, userId, page, size), HttpStatus.OK);
    }

    // 단일 일정 조회 (GET)
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> readScheduleById(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    // 일정 수정 (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody @Valid UpdateRequestDto dto
    ) {
        return new ResponseEntity<>(scheduleService.updateScheduleById(id, dto), HttpStatus.OK);
    }

    // 일정 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody @Valid DeleteRequestDto dto
    ) {
        scheduleService.deleteScheduleById(id, dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
