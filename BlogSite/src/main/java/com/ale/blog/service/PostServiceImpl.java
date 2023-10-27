package com.ale.blog.service;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.TableOfContent;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.DocumentState;
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
import com.ale.blog.handler.utils.UserUtil;
import com.ale.blog.repository.PostRepository;
import jakarta.annotation.Nonnull;
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
    private final ShareService shareService;
    private final DocumentService documentService;

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
        return postRepository.findPostBySlug(slug)
                .map(post -> postWithPermission(post, owner)
                        .orElseThrow(() -> new AppException(DataResponse.builder()
                                .code(MessageCode.UN_AUTHORIZE)
                                .status(Status.FAILED)
                                .build())
                        )
                )
                .orElseThrow(() -> new AppException(DataResponse.builder()
                        .code(MessageCode.NOT_FOUND)
                        .status(Status.FAILED)
                        .message(StaticMessage.SLUG_NOT_FOUND)
                        .build())
                );
    }

    @Override
    public Page<Post> findAllByAuthor(User author, QueryRequest queryRequest) {
        return postRepository.findAllByAuthor(author, Convert.pageRequest(queryRequest));
    }

    @Override
    public Page<Post> findAllByAuthor(@Nullable User owner, @Nonnull User author, @Nonnull PostState state, @Nonnull QueryRequest queryRequest) {
        return Optional.ofNullable(owner)
                .map(user -> author.equals(user) ? state : null)
                .or(() -> state == PostState.PUBLIC ? Optional.of(state) : Optional.empty())
                .map(st -> postRepository.findAllByAuthorAndState(author, st, Convert.pageRequest(queryRequest)))
                .orElseGet(() -> state == PostState.SHARE && owner != null
                        ? postRepository.findAllByAuthorAndShareWith(author, owner, Convert.pageRequest(queryRequest))
                        : Page.empty()
                );
    }

    @Override
    public Page<Post> findAllByCategory(Category category, QueryRequest queryRequest) {
        return postRepository.findAllByCategory(category, Convert.pageRequest(queryRequest));
    }

    @Override
    public Page<Post> findAllByCategory(@Nonnull Category category, @Nullable User owner, @Nonnull User author, @Nonnull PostState state, @Nonnull QueryRequest queryRequest) {
        return Optional.ofNullable(owner)
                .map(user -> author.equals(user) ? state : null)
                .or(() -> state == PostState.PUBLIC ? Optional.of(state) : Optional.empty())
                .map(st -> postRepository.findAllByCategoryAndState(category, st, Convert.pageRequest(queryRequest)))
                .orElseGet(() -> state == PostState.SHARE && owner != null
                        ? postRepository.findAllByCategoryAndShareWith(category, owner, Convert.pageRequest(queryRequest))
                        : Page.empty()
                );
    }

    @Override
    public Post getByIdAndAuthor(Long id, User author) {
        return postRepository.findPostByIdAndAuthor(id, author).orElseThrow(this::throwIdNotExist);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> postWithPermission(@Nonnull Post post, @Nullable User owner) {
        return Optional.ofNullable(post.getDocument())
                .flatMap(document -> documentService.documentWithPermission(document, owner))
                .map(document -> post)
                .or(() -> {
                    if (post.getState() == PostState.PUBLIC) {
                        return Optional.of(post);
                    }
                    if (owner == null) return Optional.empty();
                    return UserUtil.isOwner(owner, post.getAuthor())
                            ? Optional.of(post)
                            : shareService.isShared(post, owner)
                            ? Optional.of(post)
                            : Optional.empty();
                });
    }

    private void increaseView(Long id) {
        executorService.execute(() -> postRepository.increaseView(id));
    }

    @Override
    public Class<Post> getEntityClass() {
        return Post.class;
    }
}
