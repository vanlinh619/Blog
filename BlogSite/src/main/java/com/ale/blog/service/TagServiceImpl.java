package com.ale.blog.service;

import com.ale.blog.entity.Tag;
import com.ale.blog.handler.mapper.TagMapper;
import com.ale.blog.handler.mapper.request.TagRequest;
import com.ale.blog.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {
    private TagMapper tagMapper;
    private TagRepository tagRepository;

    @Override
    public List<Tag> createTags(List<TagRequest> tagRequests) {
        List<Tag> tags = tagMapper.toTags(tagRequests);
        tagRepository.saveAll(tags);
        return tags;
    }

    @Override
    public Tag createTag(TagRequest tagRequest) {
        Tag tag = tagMapper.toTag(tagRequest);
        tagRepository.save(tag);
        return tag;
    }
}
