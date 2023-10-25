package com.ale.blog.service;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.Share;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.request.ShareRequest;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public interface ShareService extends EntityService<Share, Long> {
    boolean isShared(@Nonnull Post post, @Nonnull User user);
    boolean isShared(@Nonnull Document document, @Nonnull User user);

    Share shareWithUser(@Nonnull ShareRequest shareRequest, @Nonnull User author);
    Post postShared(@Nonnull Long postId, @Nonnull User author);
    Document documentShared(@Nonnull Long documentId, @Nonnull User author);

}
