package com.ale.blog.service;

import com.ale.blog.entity.HeadTable;
import com.ale.blog.entity.Post;

import java.util.List;
import java.util.concurrent.Future;

public interface HeadTableService {
    List<HeadTable> createHeaderTable(Post post);
}
