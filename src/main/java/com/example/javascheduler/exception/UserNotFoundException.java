package com.example.javascheduler.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long userId) {
        super("유저 ID(" + userId + ")에 해당하는 유저를 찾을 수 없습니다.");
    }
}
