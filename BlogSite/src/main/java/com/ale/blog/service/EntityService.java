package com.ale.blog.service;

import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.handler.utils.StaticMessage;
import org.jsoup.select.Evaluator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public interface EntityService<T, ID> {
    Class<T> getEntityClass();

    default AppException throwIdNotExist() {
        return new AppException(DataResponse.builder()
                .status(Status.FAILED)
                .code(MessageCode.ID_DOES_NOT_EXIST)
                .message(getEntityClass().getName())
                .build());
    }

    default T defaultGetById(ID id, JpaRepository<T, ID> entityRepository) {
        return entityRepository.findById(id)
                .orElseThrow(() -> {
                    return new AppException(DataResponse.builder()
                            .status(Status.FAILED)
                            .code(MessageCode.ID_DOES_NOT_EXIST)
                            .message(getEntityClass().getName())
                            .build());
                });
    }

    default Optional<T> defaultFindById(ID id, JpaRepository<T, ID> entityRepository) {
        return entityRepository.findById(id);
    }
}
