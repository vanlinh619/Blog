package com.ale.blog.service;

import com.ale.blog.entity.SlugId;
import com.ale.blog.entity.state.SlugType;

public interface SlugIdService extends EntityService<SlugId, Long> {
    void createDefault();
    Long getId(SlugType type);
}
