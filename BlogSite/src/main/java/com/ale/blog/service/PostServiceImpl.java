package com.ale.blog.service;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.HeadTable;
import com.ale.blog.entity.Post;
import com.ale.blog.handler.exception.NotFoundException;
import com.ale.blog.handler.mapper.PostMapper;
import com.ale.blog.handler.mapper.request.PostRequest;
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
        post.setCategories(List.of(defaultCategory));
        if (postRequest.getCategories() != null) {
            postRequest.getCategories().forEach(id -> {
                if(!defaultCategory.getId().equals(id)) {
                    post.getCategories().add(categoryService.getCategory(id));
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
            throw new NotFoundException();
        });
        return reference.get();
    }
}
