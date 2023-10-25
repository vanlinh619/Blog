package com.ale.blog.service;


import com.ale.blog.entity.DocumentLinked;
import com.ale.blog.entity.DocumentSection;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.request.DocumentLinkedRequest;
import jakarta.annotation.Nonnull;

import java.util.List;

public interface DocumentLinkedService extends EntityService<DocumentLinked, Long> {
    DocumentLinked linkedDocument(DocumentLinkedRequest linkedRequest, User author);
    List<DocumentLinked> findAllBySection(@Nonnull DocumentSection section);
}
