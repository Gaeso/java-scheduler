package com.example.javascheduler.repository;

import com.example.javascheduler.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    User findUserById(Long userId);
    int updateName(User user);
}
