package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Image;
import com.ale.blog.handler.mapper.pojo.response.ImageResponse;
import com.ale.blog.handler.mapper.pojo.response.PageResponse;
import org.springframework.data.domain.Page;

public interface ImageMapper extends Mapper{
    ImageResponse toImageResponse(Image image);
}
