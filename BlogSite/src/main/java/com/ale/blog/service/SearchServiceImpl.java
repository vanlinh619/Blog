package com.ale.blog.service;

import com.ale.blog.entity.*;
import com.ale.blog.handler.mapper.SearchMapper;
import com.ale.blog.handler.mapper.pojo.request.SearchRequest;
import com.ale.blog.handler.mapper.pojo.response.SearchResponse;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final EntityManager entityManager;
    private final SearchMapper searchMapper;

    @Override
    public SearchSession getSearchSession() {
        return Search.session(entityManager);
    }

    @Transactional
    @Override
    public void manuallyIndexData() {
        SearchSession searchSession = Search.session(entityManager);
        searchSession.massIndexer(Post.class, User.class)
                .threadsToLoadObjects(7)
                .start();
    }

    @Transactional
    @Override
    public List<SearchResponse> search(String pattern) {
        List<Post> posts = searchDefault(
                Post.class,
                pattern,
                Post.Fields.title, Post.Fields.rawContent
        );
        List<User> users = searchDefault(
                User.class,
                pattern,
                User.Fields.username, User.Fields.fullName
        );
        List<SearchResponse> searchResponses = new ArrayList<>(posts.size() + users.size());
        searchResponses.addAll(searchMapper.postsToSearchResponses(posts));
        searchResponses.addAll(searchMapper.usersUoSearchResponses(users));
        return searchResponses;
    }
}
