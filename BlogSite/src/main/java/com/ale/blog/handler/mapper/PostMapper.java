package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Post;
import com.ale.blog.handler.mapper.pojo.PostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public interface PostMapper extends Mapper{
    Post toPost(PostRequest postRequest);
}
