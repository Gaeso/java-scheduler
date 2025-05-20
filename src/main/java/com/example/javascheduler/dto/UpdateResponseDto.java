package com.example.javascheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateResponseDto {
    private String name;
    private String content;
    private String password;
}
