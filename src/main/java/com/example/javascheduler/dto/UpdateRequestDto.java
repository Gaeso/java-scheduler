package com.example.javascheduler.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateRequestDto {

    private String name;
    private String content;
    @NotBlank
    private String password;
}
