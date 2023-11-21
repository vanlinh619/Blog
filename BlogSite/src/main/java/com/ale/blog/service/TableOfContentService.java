package com.ale.blog.service;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.TableOfContent;
import java.util.List;

public interface TableOfContentService {
    List<TableOfContent> createHeaderTable(Post post);
}
