package com.ale.blog.service;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.TableOfContent;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.PostState;
import com.ale.blog.entity.state.SlugType;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.PostMapper;
import com.ale.blog.handler.mapper.pojo.request.PostRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.handler.utils.Convert;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.utils.Format;
import com.ale.blog.handler.utils.StaticMessage;
import com.ale.blog.repository.PostRepository;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final SlugIdService slugIdService;
    private final PostMapper postMapper;
    private final HeadTableService headTableService;
    private final CategoryService categoryService;
    private final ExecutorService executorService;

    @Override
    public Post createPostArticle(PostRequest postRequest, User author) {
        Post post = postMapper.toPost(postRequest);
        post.setAuthor(author);
        Safelist safelist = Safelist.relaxed();
        safelist.addTags("figure", "figcaption");
        String clean = Jsoup.clean(post.getContent(), safelist);
        post.setContent(clean);
        post.setSlug(slugIdService.getId(SlugType.POST) + "-" + Format.toHref(post.getTitle()));

        List<TableOfContent> headTables = headTableService.createHeaderTable(post);
        post.setTableOfContents(headTables);
        post.setCategory(categoryService.getCategoryByIdAndAuthor(postRequest.getCategoryId(), author));

        postRepository.save(post);
        return post;
    }

    @Override
    public Post getPostBySlug(String slug, @Nullable User owner) {
        return Optional.ofNullable(owner)
                .flatMap(own -> postRepository.findPostByStateOrOwner(slug, PostState.PUBLIC, own))
                .or(() -> postRepository.findPostBySlugAndState(slug, PostState.PUBLIC))
                .orElseThrow(() -> {
                    return new AppException(DataResponse.builder()
                            .code(MessageCode.NOT_FOUND)
                            .status(Status.FAILED)
                            .message(StaticMessage.SLUG_NOT_FOUND)
                            .build());
                });
    }

    @Override
    public Page<Post> findAllByAuthor(User author, QueryRequest queryRequest) {
        return postRepository.findAllByAuthor(author, Convert.pageRequest(queryRequest));
    }

    @Override
    public Page<Post> findAllByCategory(Category category, QueryRequest queryRequest) {
        return postRepository.findAllByCategory(category, Convert.pageRequest(queryRequest));
    }

    @Override
    public Post getByIdAndAuthor(Long id, User author) {
        return postRepository.findPostByIdAndAuthor(id, author).orElseThrow(this::throwIdNotExist);
    }

    @Override
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    private void increaseView(Long id) {
        executorService.execute(() -> postRepository.increaseView(id));
    }

    @Override
    public Class<Post> getEntityClass() {
        return Post.class;
    }
}
