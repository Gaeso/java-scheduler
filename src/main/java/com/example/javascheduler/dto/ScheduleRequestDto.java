package com.example.javascheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {

    @NotNull(message = "사용자 ID는 필수 입력값 입니다.")
    @Positive(message = "양수만 입력해주세요.")
    private Long userId;

    @Size(max = 200, message = "할일 내용은 최대 200자까지 입력 가능합니다")
    @NotBlank(message = "할일 내용은 필수 입력값입니다.")
    private String content;

    @NotBlank
    private String password;
}
