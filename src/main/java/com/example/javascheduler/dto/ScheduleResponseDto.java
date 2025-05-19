package com.example.javascheduler.dto;

import com.example.javascheduler.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

// 전체적인 데이터를 반환하는 DTO
@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String author;
    private String content;
    private String password;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.author = schedule.getAuthor();
        this.content = schedule.getContent();
        this.password = schedule.getPassword();
        this.created_at = schedule.getCreated_at();
        this.updated_at = schedule.getUpdated_at();
    }
}
