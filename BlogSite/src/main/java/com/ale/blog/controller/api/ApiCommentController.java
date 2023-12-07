package com.ale.blog.controller.api;

import com.ale.blog.entity.Comment;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.CommentMapper;
import com.ale.blog.handler.mapper.pojo.request.CommentRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.security.UserAccess;
import com.ale.blog.service.CommentService;
import com.ale.blog.service.PostService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR, UserRole.Fields.USER})
@AllArgsConstructor
@RequestMapping("api/authorize/comment")
public class ApiCommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final PostService postService;

    @PostMapping
    public DataResponse comment(Authentication authentication, @Valid @RequestBody CommentRequest commentRequest) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        Comment comment = commentService.comment(userAccess.getCurrentUser(), commentRequest);
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(commentMapper.toCommentResponse(comment))
                .build();
    }

    @GetMapping("{postSlug}/{commentId}")
    public DataResponse getCommentById(Authentication authentication, @PathVariable String postSlug, @PathVariable Long commentId) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        Post post = postService.getPostBySlug(postSlug, userAccess.getCurrentUser());
        Comment comment = commentService.getByIdAndPost(commentId, post);
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(commentMapper.toCommentResponse(comment))
                .build();
    }
}
