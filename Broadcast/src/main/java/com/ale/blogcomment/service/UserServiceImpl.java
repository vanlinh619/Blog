package com.ale.blogcomment.service;

import com.ale.blogcomment.entity.User;
import com.ale.blogcomment.reponsitory.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public Optional<User> findByUuid(UUID uuid) {
        return userRepository.findById(uuid);
    }
}
