package com.example.javascheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String author;
    private String content;
    private String password;
    private LocalDateTime created_at;
    @Setter
    private LocalDateTime updated_at;

    public Schedule(String author, String content, String password) {
        this.author = author;
        this.content = content;
        this.password = password;
    }

    public Schedule(String author, String content, String password, LocalDateTime updated_at) {
        this.author = author;
        this.content = content;
        this.password = password;
        this.updated_at = updated_at;
    }


}
