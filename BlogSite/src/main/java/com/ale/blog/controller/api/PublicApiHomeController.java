package com.ale.blog.controller.api;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.PageMapper;
import com.ale.blog.handler.mapper.PostMapper;
import com.ale.blog.handler.mapper.pojo.request.PageRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.PostResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.handler.utils.SortType;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.handler.utils.UserUtil;
import com.ale.blog.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/public/home")
public class PublicApiHomeController {
    private final PostService postService;
    private final PageMapper<Post, PostResponse> pageMapper;
    private final PostMapper postMapper;

    @GetMapping
    public DataResponse getAllPost(Authentication authentication, @Valid PageRequest pageRequest) {
        Optional<User> userOptional = UserUtil.owner(authentication);
        Page<Post> postPage = postService.getAllPost(userOptional.orElse(null), QueryRequest.builder()
                .page(pageRequest.getPage())
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(List.of(Post.Fields.createDate))
                .sortType(SortType.DESC.name())
                .build()
        );
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(pageMapper.toPageResponse(postPage, postMapper::toPostResponse))
                .build();
    }
}
