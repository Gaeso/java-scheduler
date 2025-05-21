package com.example.javascheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    @Setter
    private Long id;
    private Long userId;
    private String content;
    private String password;
    private LocalDateTime created_at;
    @Setter
    private LocalDateTime updated_at;

    public Schedule(Long userId, String content, String password, LocalDateTime created_at, LocalDateTime updated_at) {
        this.userId = userId;
        this.content = content;
        this.password = password;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
