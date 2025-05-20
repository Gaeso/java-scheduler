package com.example.javascheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {
    private Long id;
    @Setter
    private String name;
    private String email;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
