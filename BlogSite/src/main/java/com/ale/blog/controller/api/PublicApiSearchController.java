package com.ale.blog.controller.api;

import com.ale.blog.handler.mapper.pojo.request.SearchRequest;
import com.ale.blog.handler.mapper.pojo.response.SearchResponse;
import com.ale.blog.service.SearchService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/public/search")
public class PublicApiSearchController {
    private final SearchService searchService;
    
    @GetMapping
    public List<SearchResponse> search(@Valid SearchRequest searchRequest) {
        return searchService.search(searchRequest.getPattern());
    }
}
