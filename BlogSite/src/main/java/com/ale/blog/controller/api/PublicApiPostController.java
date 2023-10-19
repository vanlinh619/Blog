package com.ale.blog.controller.api;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.Post;
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
import com.ale.blog.service.CategoryService;
import com.ale.blog.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/public/post")
public class PublicApiPostController {
    private final PostService postService;
    private final CategoryService categoryService;
    private final PageMapper<Post, PostResponse> pageMapper;
    private final PostMapper postMapper;

    @GetMapping("{username}/{categoryUrl}")
    public DataResponse getAllPost(@PathVariable String username, @PathVariable String categoryUrl, @Valid PageRequest pageRequest) {
        Category category = categoryService.getCategoryBySlugAndUsername(categoryUrl, username);
        Page<Post> pagePost = postService.findAllByCategory(category, QueryRequest.builder()
                .page(pageRequest.getPage())
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(Post.Fields.createDate)
                .sortType(SortType.DESC.name())
                .build());
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(pageMapper.toPageResponse(pagePost, postMapper::toPostResponse))
                .build();
    }
}
