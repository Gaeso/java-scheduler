package com.example.javascheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String author;
    private String content;
    private String password;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Schedule(String author, String content, String password) {
        this.author = author;
        this.content = content;
        this.password = password;
    }
}
