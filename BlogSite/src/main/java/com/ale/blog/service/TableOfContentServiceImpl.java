package com.ale.blog.service;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.TableOfContent;
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
public class TableOfContentServiceImpl implements TableOfContentService {
    private final HeadTableRepository repository;

    @Override
    public List<TableOfContent> createHeaderTable(Post post) {
        Document document = Jsoup.parse(post.getContent());
        Elements elements = document.select("h2, h3, h4");
        List<TableOfContent> tableOfContents = new LinkedList<>();
        AtomicInteger integer = new AtomicInteger(1);
        elements.forEach(element -> {
            if(!element.text().trim().isBlank()) {
                String href = integer.getAndIncrement() + "-" + Format.toHref(element.text());
                tableOfContents.add(TableOfContent.builder()
                        .tag(HtmlTag.valueOf(element.tagName()))
                        .content(element.text())
                        .href(href)
                        .post(post)
                        .build());
                element.id(href);
            }
        });
        post.setContent(document.body().html());
        return tableOfContents;
    }
}
