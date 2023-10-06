package com.ale.blog.service;

import com.ale.blog.entity.Tag;
import com.ale.blog.handler.mapper.pojo.request.TagRequest;

import java.util.List;

public interface TagService extends EntityService<Tag, Long> {
    List<Tag> createTags(List<TagRequest> tagRequests);
    public Tag createTag(TagRequest tagRequest);
}
