package com.example.javascheduler.dto;

import com.example.javascheduler.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

// 목록 조회용 DTO, 작성자, 할일, 시간만 반환하기 위함.
@Getter
@AllArgsConstructor
public class ScheduleListDto {
    private final Long userId;
    private final String content;
    private final LocalDateTime updated_at;

    public ScheduleListDto(Schedule schedule) {
        this.userId = schedule.getUserId();
        this.content = schedule.getContent();
        this.updated_at = schedule.getUpdated_at();
    }
}
