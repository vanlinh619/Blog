package com.ale.blog.controller;

import com.ale.blog.controller.state.CategoryPage;
import com.ale.blog.entity.Category;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.request.PageRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.utils.SortType;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.service.CategoryService;
import com.ale.blog.service.PostService;
import com.ale.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final PostService postService;
    private final UserService userService;

    @GetMapping("{username}")
    public String getAllPost(@PathVariable String username, @Valid PageRequest pageRequest, Model model) {
        User author = userService.getByUsername(username);
        Page<Post> postPage = postService.findAllByAuthor(author, QueryRequest.builder()
                .page(pageRequest.getPage() - 1)
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(Post.Fields.createDate)
                .sortType(SortType.DESC.name())
                .build());
        model.addAttribute("postPage", postPage);
        model.addAttribute("author", author);
        model.addAttribute("type", CategoryPage.ALL);
        model.addAttribute("categoryName", "Tất cả bài viết");
        return "category";
    }

    @GetMapping("{username}/{categoryUrl}")
    public String getAllPostOfCategory(@PathVariable String username, @PathVariable String categoryUrl, @Valid PageRequest pageRequest, Model model) {
        User author = userService.getByUsername(username);
        Category category = categoryService.getCategoryBySlugAndAuthor(categoryUrl, author);
        Page<Post> postPage = postService.findAllByCategory(category, QueryRequest.builder()
                .page(pageRequest.getPage() - 1)
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(Post.Fields.createDate)
                .sortType(SortType.DESC.name())
                .build());
        model.addAttribute("postPage", postPage);
        model.addAttribute("author", author);
        model.addAttribute("type", CategoryPage.CATEGORY);
        model.addAttribute("categoryName", category.getTitle());
        return "category";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({Exception.class})
    public String handleValidationExceptions(Exception e) {
        e.printStackTrace();
        return "404";
    }
}
