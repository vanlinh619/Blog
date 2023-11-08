package com.ale.blogcomment.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Override
    public void sendMessage(String message, String topicName) {
    }
}
