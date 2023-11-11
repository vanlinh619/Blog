package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Comment;
import com.ale.blog.handler.mapper.pojo.response.CommentResponse;
import com.ale.blog.handler.utils.Format;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentMapperImpl implements CommentMapper {
//    private final ModelMapper modelMapper;

    @Override
    public CommentResponse toCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .username(Format.nameOfUser(comment.getAuthor()))
                .createDate(Format.toTimeDifference(comment.getCreateDate()))
                .content(comment.getContent())
                .childrenSize(comment.getChildrenSize())
                .build();
    }
}
