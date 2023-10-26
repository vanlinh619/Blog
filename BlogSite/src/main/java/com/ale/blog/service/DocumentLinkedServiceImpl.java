package com.ale.blog.service;

import com.ale.blog.entity.DocumentLinked;
import com.ale.blog.entity.DocumentSection;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.request.DocumentLinkedRequest;
import com.ale.blog.repository.DocumentLinkedRepository;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentLinkedServiceImpl implements DocumentLinkedService {
    private final DocumentSectionService sectionService;
    private final DocumentLinkedRepository linkedRepository;
    private PostService postService;
    @Autowired
    public void setPostService(@Lazy PostService postService) {
        this.postService = postService;
    }

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
                .priority(section.getSize())
                .post(post)
                .build();
        linkedRepository.save(linked);

        post.setDocument(section.getDocument());
        postService.save(post);
        sectionService.increaseSize(section.getId());
        return linked;
    }

    @Override
    public List<DocumentLinked> findAllBySection(@Nonnull DocumentSection section) {
        return linkedRepository.findAllBySection(section);
    }
}
