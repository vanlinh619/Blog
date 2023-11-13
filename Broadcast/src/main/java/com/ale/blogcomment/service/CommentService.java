package com.ale.blogcomment.service;

import com.ale.blogcomment.handler.pojo.BroadcastResponse;

public interface CommentService {
    Boolean isAdminBroadcast(BroadcastResponse broadcastResponse);
}
