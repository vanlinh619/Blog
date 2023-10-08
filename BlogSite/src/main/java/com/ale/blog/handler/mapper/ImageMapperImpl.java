package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Image;
import com.ale.blog.handler.mapper.pojo.response.ImageResponse;
import com.ale.blog.handler.utils.Format;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ImageMapperImpl implements ImageMapper {
    private final ModelMapper mapper;

    @Override
    public ImageResponse toImageResponse(Image image) {
        ImageResponse imageResponse = mapper.map(image, ImageResponse.class);
        imageResponse.setDate(image.getCreateDate());
        return imageResponse;
    }
}
