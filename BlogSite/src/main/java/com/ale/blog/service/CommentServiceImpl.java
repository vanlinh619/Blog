package com.ale.blog.service;

import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public void listen(String message) {
        System.out.println("Received Message in group foo: " + message);
    }
}
