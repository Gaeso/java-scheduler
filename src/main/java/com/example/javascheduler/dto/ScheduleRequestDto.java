package com.example.javascheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {
    private String author;
    private String content;
    private String password;
}
