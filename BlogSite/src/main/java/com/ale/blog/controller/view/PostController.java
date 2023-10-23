package com.ale.blog.controller.view;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.PageMapper;
import com.ale.blog.handler.mapper.PostMapper;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.PostResponse;
import com.ale.blog.handler.utils.SortType;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.handler.utils.UtilMethod;
import com.ale.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("post")
public class PostController {
    private final PostService postService;
    private final PageMapper<Post, PostResponse> pageMapper;
    private final PostMapper postMapper;

    @GetMapping("{postUrl}")
    public String getPost(@PathVariable String postUrl, Model model, Authentication authentication) {
        Optional<User> userOptional = UtilMethod.owner(authentication);
        Post post = postService.getPostBySlug(postUrl, userOptional.orElse(null));
        Page<Post> postPage = postService.findAllByCategory(post.getCategory(), QueryRequest.builder()
                .page(0)
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(Post.Fields.createDate)
                .sortType(SortType.DESC.name())
                .build());
        model.addAttribute("author", post.getAuthor());
        model.addAttribute("category", post.getCategory());
        model.addAttribute("post", post);
        model.addAttribute("postPage", pageMapper.toPageResponse(postPage, postMapper::toPostResponse));
        model.addAttribute("document", post.getDocument());
        model.addAttribute("user", userOptional.orElse(null));
        return "post";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({Exception.class})
    public String handleValidationExceptions(Exception e) {
        e.printStackTrace();
        return "404";
    }
}
