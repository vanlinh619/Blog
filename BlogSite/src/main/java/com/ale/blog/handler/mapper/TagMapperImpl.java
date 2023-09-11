package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Tag;
import com.ale.blog.handler.mapper.request.TagRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TagMapperImpl implements TagMapper {
    private final ModelMapper mapper;

    @Override
    public List<Tag> toTags(List<TagRequest> tagRequests) {
        List<Tag> tags = tagRequests.stream()
                .map(tagRequest -> mapper.map(tagRequest, Tag.class))
                .toList();
        return tags;
    }

    @Override
    public Tag toTag(TagRequest tagRequest) {
        Tag tag = mapper.map(tagRequest, Tag.class);
        return tag;
    }
}
