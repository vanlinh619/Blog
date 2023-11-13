package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Comment;
import com.ale.blog.handler.mapper.pojo.response.CommentResponse;
import com.ale.blog.handler.utils.Format;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CommentMapperImpl implements CommentMapper {
//    private final ModelMapper modelMapper;

    @Override
    public CommentResponse toCommentResponse(Comment comment) {
        CommentResponse commentResponse = CommentResponse.builder()
                .id(comment.getId())
                .username(Format.nameOfUser(comment.getAuthor()))
                .createDate(Format.toTimeDifference(comment.getCreateDate()))
                .content(comment.getContent())
                .childrenSize(comment.getChildrenSize())
                .sender(comment.getAuthor().getUsername())
                .build();
        if (comment.getSuperParent() != null) {
            commentResponse.setSupperCommentId(comment.getSuperParent().getId());
            commentResponse.setRelyUsername(comment.getReplyUsername());
        }
        return commentResponse;
    }
}
