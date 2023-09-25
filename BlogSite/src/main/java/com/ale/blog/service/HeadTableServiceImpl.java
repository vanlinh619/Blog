package com.ale.blog.service;

import com.ale.blog.entity.HeadTable;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.state.HtmlTag;
import com.ale.blog.handler.utils.Format;
import com.ale.blog.repository.HeadTableRepository;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class HeadTableServiceImpl implements HeadTableService {
    private final HeadTableRepository repository;

    @Override
    public List<HeadTable> createHeaderTable(Post post) {
        Document document = Jsoup.parse(post.getContent());
        Elements elements = document.select("h2, h3, h4");
        List<HeadTable> headTables = new LinkedList<>();
        AtomicInteger integer = new AtomicInteger(1);
        elements.forEach(element -> {
            if(!element.text().trim().isBlank()) {
                String href = Format.toHref(element.text()) + "-" + integer.getAndIncrement();
                headTables.add(HeadTable.builder()
                        .tag(HtmlTag.valueOf(element.tagName()))
                        .content(element.text())
                        .href(href)
                        .post(post)
                        .build());
                element.id(href);
            }
        });
        post.setContent(document.body().html());
        return headTables;
    }
}
