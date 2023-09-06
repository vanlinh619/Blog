package com.ale.blog.service;

import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.utils.MessageType;
import com.ale.blog.repository.EntityRepository;

import java.util.concurrent.atomic.AtomicReference;

public interface EntityService<T, ID> {
    default T defaultFindById(ID id, EntityRepository<T, ID> entityRepository) {
        AtomicReference<T> atomicReference = new AtomicReference<>();
        entityRepository.findById(id).ifPresentOrElse(atomicReference::set, () -> {
            throw new AppException(MessageType.ID_DOES_NOT_EXIST);
        });
        return atomicReference.get();
    }
}
