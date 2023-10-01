package com.ale.blog.service;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.HeadTable;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.handler.exception.NotFoundException;
import com.ale.blog.handler.mapper.PostMapper;
import com.ale.blog.handler.mapper.request.PostRequest;
import com.ale.blog.handler.mapper.request.QueryRequest;
import com.ale.blog.handler.mapper.response.DataResponse;
import com.ale.blog.handler.utils.Convert;
import com.ale.blog.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;
    private final HeadTableService headTableService;
    private final CategoryService categoryService;

    @Override
    public Post createPostArticle(PostRequest postRequest) {
        Post post = postMapper.toPost(postRequest);
        post.setAuthor(userService.getById(UUID.fromString(postRequest.getAuthor())));
        String clean = Jsoup.clean(post.getContent(), Safelist.relaxed());
        post.setContent(clean);
        List<HeadTable> headTables = headTableService.createHeaderTable(post);
        post.setHeadTables(headTables);

        Category defaultCategory = categoryService.getDefaultCategory();
        defaultCategory.getPosts().add(post);
        post.setCategories(List.of(defaultCategory));
        if (postRequest.getCategories() != null) {
            postRequest.getCategories().forEach(id -> {
                if (!defaultCategory.getId().equals(id)) {
                    Category category = categoryService.getCategory(id);
                    category.getPosts().add(post);
                    post.getCategories().add(category);
                }
            });
        }

        postRepository.save(post);
        return post;
    }

    @Override
    public Post getPostBySlug(String slug) {
        AtomicReference<Post> reference = new AtomicReference<>();
        postRepository.findFirstBySlug(slug).ifPresentOrElse(reference::set, () -> {
            throw new NotFoundException(DataResponse.builder().build());
        });
        return reference.get();
    }

    @Override
    public List<Post> findAllByUsername(String username, QueryRequest queryRequest) {
        AtomicReference<List<Post>> reference = new AtomicReference<>();
        userService.findByUsername(username).ifPresentOrElse(user -> {
            reference.set(postRepository.findAllByAuthor(user, Convert.pageRequest(queryRequest))
                    .map(post -> post)
                    .toList());
        }, () -> {
            throw new NotFoundException(DataResponse.builder().build());
        });
        return reference.get();
    }
}
