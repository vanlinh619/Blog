package com.ale.blog.controller;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.CategoryLevel;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.utils.SortType;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.service.CategoryService;
import com.ale.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("post")
public class PostController {
    private final PostService postService;
    private final CategoryService categoryService;

    @GetMapping("{postUrl}")
    public String getPost(@PathVariable String postUrl, Model model) {
        Post post = postService.getPostBySlug(postUrl);
        Page<Post> pagePost = postService.findAllByCategory(post.getCategory(), QueryRequest.builder()
                .page(0)
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(Post.Fields.createDate)
                .sortType(SortType.DESC.name())
                .build());
        model.addAttribute("post", post);
        model.addAttribute("pagePost", pagePost);
        return "post";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({Exception.class})
    public String handleValidationExceptions(Exception e) {
        e.printStackTrace();
        return "404";
    }
}
