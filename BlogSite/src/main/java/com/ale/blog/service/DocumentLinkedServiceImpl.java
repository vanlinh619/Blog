package com.ale.blog.service;

import com.ale.blog.entity.DocumentLinked;
import com.ale.blog.entity.DocumentSection;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.request.DocumentLinkedRequest;
import com.ale.blog.repository.DocumentLinkedRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DocumentLinkedServiceImpl implements DocumentLinkedService {
    private final PostService postService;
    private final DocumentSectionService sectionService;
    private final DocumentLinkedRepository linkedRepository;

    @Override
    public Class<DocumentLinked> getEntityClass() {
        return DocumentLinked.class;
    }

    @Override
    public DocumentLinked linkedDocument(DocumentLinkedRequest linkedRequest, User author) {
        Post post = postService.getByIdAndAuthor(linkedRequest.getPostId(), author);
        DocumentSection section = sectionService.getByIdAndAuthor(linkedRequest.getSectionId(), author);
        DocumentLinked linked = DocumentLinked.builder()
                .title(post.getTitle())
                .slug(post.getSlug())
                .section(section)
                .priority(section.getPriority())
                .post(post)
                .build();
        linkedRepository.save(linked);

        sectionService.increaseSize(section);
        return linked;
    }
}
