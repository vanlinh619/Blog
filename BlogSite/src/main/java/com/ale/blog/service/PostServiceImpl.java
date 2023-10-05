package com.ale.blog.service;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.HeadTable;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.PostMapper;
import com.ale.blog.handler.mapper.request.PostRequest;
import com.ale.blog.handler.mapper.request.QueryRequest;
import com.ale.blog.handler.mapper.response.DataResponse;
import com.ale.blog.handler.utils.Convert;
import com.ale.blog.handler.utils.MessageCode;
import com.ale.blog.handler.utils.StaticMessage;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;
    private final HeadTableService headTableService;
    private final CategoryService categoryService;
    private final ExecutorService executorService;

    @Override
    public Post createPostArticle(PostRequest postRequest) {
        Post post = postMapper.toPost(postRequest);
        User author = userService.getById(UUID.fromString(postRequest.getAuthor()));
        post.setAuthor(author);
        String clean = Jsoup.clean(post.getContent(), Safelist.relaxed());
        post.setContent(clean);
        List<HeadTable> headTables = headTableService.createHeaderTable(post);
        post.setHeadTables(headTables);

        Category defaultCategory = categoryService.getCategoryBySlugAndUsername(StaticVariable.ALL.toLowerCase(), author.getUsername());
        defaultCategory.getPosts().add(post);
        List<Category> categories = new LinkedList<>();
        categories.add(defaultCategory);
        if (postRequest.getCategories() != null) {
            postRequest.getCategories().forEach(id -> {
                if (!defaultCategory.getId().equals(id)) {
                    Category category = categoryService.getCategoryById(id);
                    category.getPosts().add(post);
                    categories.add(category);
                }
            });
        }
        post.setCategories(categories);

        postRepository.save(post);
        return post;
    }

    @Override
    public Post getPostBySlug(String slug) {
        AtomicReference<Post> reference = new AtomicReference<>();
        postRepository.findFirstBySlug(slug).ifPresentOrElse(reference::set, () -> {
            throw new AppException(DataResponse.builder()
                    .code(MessageCode.NOT_FOUND)
                    .status(DataResponse.ResponseStatus.FAILED)
                    .message(StaticMessage.SLUG_NOT_FOUND)
                    .build());
        });
        Post post = reference.get();
        post.setView(post.getView() + 1);
        increaseView(post.getId());
        return post;
    }

    @Override
    public Page<Post> findAllByUsername(String username, QueryRequest queryRequest) {
        AtomicReference<Page<Post>> reference = new AtomicReference<>();
        userService.findByUsername(username).ifPresentOrElse(user -> {
            reference.set(postRepository.findAllByAuthor(user, Convert.pageRequest(queryRequest)));
        }, () -> {
            throw new AppException(DataResponse.builder()
                    .code(MessageCode.NOT_FOUND)
                    .status(DataResponse.ResponseStatus.FAILED)
                    .message(StaticMessage.USERNAME_NOT_FOUND)
                    .build());
        });
        return reference.get();
    }

    @Override
    public Page<Post> findAllByCategory(Category category, QueryRequest queryRequest) {
        return postRepository.findAllByCategoriesContaining(category, Convert.pageRequest(queryRequest));
    }

    private void increaseView(Long id) {
        executorService.execute(() -> postRepository.increaseView(id));
    }
}
