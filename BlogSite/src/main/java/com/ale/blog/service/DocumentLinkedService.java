package com.ale.blog.service;


import com.ale.blog.entity.DocumentLinked;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.request.DocumentLinkedRequest;

public interface DocumentLinkedService extends EntityService<DocumentLinked, Long> {
    DocumentLinked linkedDocument(DocumentLinkedRequest linkedRequest, User author);
}
