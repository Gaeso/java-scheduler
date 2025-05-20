package com.example.javascheduler.dto;

import com.example.javascheduler.entity.Schedule;
import com.example.javascheduler.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

// 전체적인 데이터를 반환하는 DTO
@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public ScheduleResponseDto(Schedule schedule, User user) {
        this.id = schedule.getId();
        this.userId = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.content = schedule.getContent();
        this.created_at = schedule.getCreated_at();
        this.updated_at = schedule.getUpdated_at();
    }
}
