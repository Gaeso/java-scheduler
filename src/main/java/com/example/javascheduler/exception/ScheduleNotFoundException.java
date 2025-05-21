package com.example.javascheduler.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(Long id) {
        super("ID " + id + "에 해당하는 일정을 찾을 수 없습니다");
    }
}
