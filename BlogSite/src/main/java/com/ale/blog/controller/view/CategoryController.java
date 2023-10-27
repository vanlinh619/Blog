package com.ale.blog.controller.view;

import com.ale.blog.controller.state.CategoryPage;
import com.ale.blog.entity.Category;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.PostState;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.pojo.request.PageDocumentRequest;
import com.ale.blog.handler.mapper.pojo.request.PageRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.utils.SortType;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.handler.utils.TextUtil;
import com.ale.blog.handler.utils.UserUtil;
import com.ale.blog.service.CategoryService;
import com.ale.blog.service.PostService;
import com.ale.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final PostService postService;
    private final UserService userService;

    @GetMapping("{username}")
    public String getAllPost(@PathVariable String username, @Valid PageDocumentRequest pageRequest, Model model, Authentication authentication) {
        Optional<User> userOptional = UserUtil.owner(authentication);
        User author = userService.getByUsername(username);
        Page<Post> postPage = postService.findAllByAuthor(
                userOptional.orElse(null),
                author,
                PostState.valueOf(pageRequest.getScope().toUpperCase()),
                QueryRequest.builder()
                        .page(pageRequest.getPage() - 1)
                        .size(StaticVariable.PAGE_SIZE)
                        .sortBy(Post.Fields.createDate)
                        .sortType(SortType.DESC.name())
                        .build()
        );
        model.addAttribute("postPage", postPage);
        model.addAttribute("author", author);
        model.addAttribute("type", CategoryPage.ALL);
        model.addAttribute("categoryName", "Tất cả bài viết");
        model.addAttribute("user", userOptional.orElse(null));
        model.addAttribute("scope", pageRequest.getScope().toLowerCase());
        model.addAttribute("breadcrumb", List.of(
                List.of(TextUtil.ALL_CATEGORY, "")
        ));
        return "category";
    }

    @GetMapping("{username}/{categoryUrl}")
    public String getAllPostOfCategory(
            @PathVariable String username,
            @PathVariable String categoryUrl,
            @Valid PageDocumentRequest pageRequest,
            Model model,
            Authentication authentication
    ) {
        Optional<User> userOptional = UserUtil.owner(authentication);
        User author = userService.getByUsername(username);
        Category category = categoryService.getCategoryBySlugAndAuthor(categoryUrl, author);
        Page<Post> postPage = postService.findAllByCategory(
                category,
                userOptional.orElse(null),
                author,
                PostState.valueOf(pageRequest.getScope().toUpperCase()),
                QueryRequest.builder()
                        .page(pageRequest.getPage() - 1)
                        .size(StaticVariable.PAGE_SIZE)
                        .sortBy(Post.Fields.createDate)
                        .sortType(SortType.DESC.name())
                        .build()
        );
        model.addAttribute("postPage", postPage);
        model.addAttribute("author", author);
        model.addAttribute("type", CategoryPage.CATEGORY);
        model.addAttribute("categoryName", category.getTitle());
        model.addAttribute("user", userOptional.orElse(null));
        model.addAttribute("scope", pageRequest.getScope().toLowerCase());
        model.addAttribute("breadcrumb", List.of(
                List.of(category.getTitle(), "")
        ));
        return "category";
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Exception.class})
    public String handleValidationExceptions(Exception e, Model model) {
        e.printStackTrace();
        if (e instanceof AppException appException && appException.getResponse().getCode() == MessageCode.UN_AUTHORIZE) {
            model.addAttribute("message", "Un Authorize");
        } else {
            model.addAttribute("message", "Page not found");
        }
        return "404";
    }
}
