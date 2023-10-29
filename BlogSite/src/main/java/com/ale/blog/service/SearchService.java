package com.ale.blog.service;

import com.ale.blog.handler.mapper.pojo.request.SearchRequest;
import com.ale.blog.handler.mapper.pojo.response.SearchResponse;
import org.hibernate.search.mapper.orm.session.SearchSession;

import java.util.List;

public interface SearchService {
    SearchSession getSearchSession();

    public void manuallyIndexData();

    List<SearchResponse> search(String pattern);

    default <T> List<T> searchDefault(Class<T> clazz, String pattern, String... fields) {
        return getSearchSession().search(clazz)
                .where(searchPredicateFactory -> searchPredicateFactory.match()
                        .fields(fields)
                        .matching(pattern)
                )
                .fetchHits(10);
    }
}
