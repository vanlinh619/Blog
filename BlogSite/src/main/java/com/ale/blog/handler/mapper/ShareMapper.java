package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Share;
import com.ale.blog.handler.mapper.pojo.request.ShareRequest;
import com.ale.blog.handler.mapper.pojo.response.ShareResponse;

public interface ShareMapper extends Mapper {

    ShareResponse toShareResponse(Share share, ShareRequest shareRequest);
}
