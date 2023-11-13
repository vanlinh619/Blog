package com.ale.blog.controller.api;

import com.ale.blog.entity.Comment;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.CommentMapper;
import com.ale.blog.handler.mapper.PageMapper;
import com.ale.blog.handler.mapper.pojo.request.PageRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.CommentResponse;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.handler.utils.SortType;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.handler.utils.UserUtil;
import com.ale.blog.service.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/public/comment")
public class PublicApiCommentController {
    private final CommentService commentService;
    private final PageMapper<Comment, CommentResponse> pageMapper;
    private final CommentMapper commentMapper;

    @GetMapping("{postSlug}")
    public DataResponse getAllComment(Authentication authentication, @PathVariable String postSlug, @Valid PageRequest pageRequest) {
        Optional<User> userOptional = UserUtil.owner(authentication);
        Page<Comment> commentPage = commentService.findAllByPostSlug(userOptional.orElse(null), postSlug, QueryRequest.builder()
                .page(pageRequest.getPage())
                .size(StaticVariable.COMMENT_PAGE_SIZE)
                .sortBy(List.of(Comment.Fields.childrenSize, Comment.Fields.createDate))
                .sortType(SortType.DESC.name())
                .build()
        );
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(pageMapper.toPageResponse(commentPage, commentMapper::toCommentResponse))
                .build();
    }

    @GetMapping("{postSlug}/{superCommentId}")
    public DataResponse getAllChildrenComment(
            Authentication authentication,
            @PathVariable String postSlug,
            @PathVariable Long superCommentId,
            @Valid PageRequest pageRequest
    ) {
        Optional<User> userOptional = UserUtil.owner(authentication);
        Page<Comment> commentPage = commentService.findAllByPostSlugAndSupperComment(
                userOptional.orElse(null),
                postSlug,
                superCommentId,
                QueryRequest.builder()
                        .page(pageRequest.getPage())
                        .size(StaticVariable.COMMENT_PAGE_SIZE)
                        .sortBy(List.of(Comment.Fields.createDate))
                        .sortType(SortType.ASC.name())
                        .build()
        );
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(pageMapper.toPageResponse(commentPage, commentMapper::toCommentResponse))
                .build();
    }
}
