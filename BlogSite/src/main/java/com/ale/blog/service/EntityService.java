package com.ale.blog.service;

import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.response.DataResponse;
import com.ale.blog.handler.utils.MessageCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public interface EntityService<T, ID> {
    default T defaultGetById(ID id, JpaRepository<T, ID> entityRepository) {
        AtomicReference<T> atomicReference = new AtomicReference<>();
        entityRepository.findById(id).ifPresentOrElse(atomicReference::set, () -> {
            throw new AppException(DataResponse.builder()
                    .code(MessageCode.ID_DOES_NOT_EXIST)
                    .build());
        });
        return atomicReference.get();
    }

    default Optional<T> defaultFindById(ID id, JpaRepository<T, ID> entityRepository) {
        return entityRepository.findById(id);
    }
}
