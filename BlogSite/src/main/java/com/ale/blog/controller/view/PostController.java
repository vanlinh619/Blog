package com.ale.blog.controller.view;

import com.ale.blog.entity.Favourite;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.PageMapper;
import com.ale.blog.handler.mapper.PostMapper;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.PostResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.utils.SortType;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.handler.utils.UserUtil;
import com.ale.blog.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {
    private final PostService postService;
    private final PageMapper<Post, PostResponse> pageMapper;
    private final PostMapper postMapper;
    private final DocumentService documentService;
    private final ViewService viewService;
    private final FavouriteService favouriteService;
    private final ImageService imageService;

    @GetMapping("{postUrl}")
    public String getPost(
            @PathVariable String postUrl,
            Model model,
            Authentication authentication,
            HttpServletRequest request
    ) {
        Optional<User> userOptional = UserUtil.owner(authentication);
        Post post = postService.getPostBySlug(postUrl, userOptional.orElse(null));
        Page<Post> postPage = postService.findAllByCategory(post.getCategory(), QueryRequest.builder()
                .page(0)
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(List.of(Post.Fields.createDate))
                .sortType(SortType.DESC.name())
                .build());
        model.addAttribute("author", post.getAuthor());
        model.addAttribute("category", post.getCategory());
        model.addAttribute("post", post);
        model.addAttribute("postPage", pageMapper.toPageResponse(postPage, postMapper::toPostResponse));
        documentService.setEntriesOfDocument(post.getDocument());
        model.addAttribute("document", post.getDocument());
        userOptional
                .map(user -> {
                    model.addAttribute("user", user);
                    return user;
                })
                .flatMap(imageService::getAvatar)
                .ifPresent(image -> {
                    model.addAttribute("avatar", image);
                });
        model.addAttribute("favourite",favouriteService.ifFavourite(userOptional.orElse(null), post));

        viewService.increaseView(userOptional.orElse(null), post, request);

        return "post";
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Exception.class})
    public String handleValidationExceptions(Exception e, Model model) {
        e.printStackTrace();
        if(e instanceof AppException appException && appException.getResponse().getCode() == MessageCode.UN_AUTHORIZE) {
            model.addAttribute("message", "Un Authorize");
        } else {
            model.addAttribute("message", "Page not found");
        }
        return "404";
    }
}
