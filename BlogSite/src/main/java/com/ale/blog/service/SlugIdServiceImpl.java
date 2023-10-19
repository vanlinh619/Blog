package com.ale.blog.service;

import com.ale.blog.entity.SlugId;
import com.ale.blog.entity.state.SlugType;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.repository.SlugIdRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class SlugIdServiceImpl implements SlugIdService {
    private final SlugIdRepository slugIdRepository;

    @Override
    public Long getId(SlugType type) {
        return slugIdRepository.findSlugIdByType(type)
                .map(slugId -> {
                    slugId.setNumber(slugId.getNumber() + 1);
                    slugIdRepository.save(slugId);
                    return slugId.getNumber();
                })
                .orElseGet(() -> {
                    SlugId slugId = SlugId.builder()
                            .number(1L)
                            .type(type)
                            .build();
                    slugIdRepository.save(slugId);
                    return slugId.getNumber();
                });
    }

    @Override
    public Class<SlugId> getEntityClass() {
        return SlugId.class;
    }
}
