package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Post;
import com.ale.blog.handler.mapper.pojo.request.PostRequest;
import com.ale.blog.handler.mapper.pojo.response.PostResponse;

public interface PostMapper extends Mapper{
    Post toPost(PostRequest postRequest);
    PostResponse toPostResponse(Post post);
}
