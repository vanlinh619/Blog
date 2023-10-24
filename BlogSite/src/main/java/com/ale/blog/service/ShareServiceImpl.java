package com.ale.blog.service;

import com.ale.blog.entity.Share;

public class ShareServiceImpl implements ShareService {
    @Override
    public Class<Share> getEntityClass() {
        return Share.class;
    }
}
