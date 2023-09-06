package com.ale.blog.service;

import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.utils.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.concurrent.atomic.AtomicReference;

public interface EntityService<T, ID> {
    default T defaultGetById(ID id, JpaRepository<T, ID> entityRepository) {
        AtomicReference<T> atomicReference = new AtomicReference<>();
        entityRepository.findById(id).ifPresentOrElse(atomicReference::set, () -> {
            throw new AppException(MessageType.ID_DOES_NOT_EXIST);
        });
        return atomicReference.get();
    }
}
