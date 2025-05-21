package com.example.javascheduler.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteRequestDto {

    @NotBlank
    private String password;
}
