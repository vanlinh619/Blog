package com.ale.blogcomment.service;

import com.ale.blogcomment.entity.UserRole;
import com.ale.blogcomment.handler.pojo.BroadcastResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserService userService;

    @Override
    public Boolean isAdminBroadcast(BroadcastResponse broadcastResponse) {
        return userService.findByUuid(UUID.fromString(broadcastResponse.getUserId()))
                .map(user -> user.getRole() == UserRole.ADMIN)
                .orElse(false);
    }
}
