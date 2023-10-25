package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Share;
import com.ale.blog.handler.mapper.pojo.request.ShareRequest;
import com.ale.blog.handler.mapper.pojo.response.ShareResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ShareMapperImpl implements ShareMapper {
    private final ModelMapper mapper;

    @Override
    public ShareResponse toShareResponse(Share share, ShareRequest shareRequest) {
        ShareResponse shareResponse = mapper.map(shareRequest, ShareResponse.class);
        shareResponse.setId(share.getId());
        return shareResponse;
    }
}
