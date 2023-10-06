package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Tag;
import com.ale.blog.handler.mapper.pojo.request.TagRequest;

import java.util.List;

public interface TagMapper {
    List<Tag> toTags(List<TagRequest> tagRequests);
    Tag toTag(TagRequest tagRequest);
}
