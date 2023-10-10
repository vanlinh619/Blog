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
    public void createDefault() {
        if (slugIdRepository.findFirstByOrderById().isEmpty()) {
            slugIdRepository.saveAll(Arrays.stream(SlugType.values())
                    .map(type -> SlugId.builder()
                            .number(0L)
                            .type(type)
                            .build()
                    )
                    .toList()
            );
        }
    }

    @Override
    public Long getId(SlugType type) {
        return slugIdRepository.findSlugIdByType(type)
                .map(slugId -> {
                    slugId.setNumber(slugId.getNumber() + 1);
                    slugIdRepository.save(slugId);
                    return slugId.getNumber();
                })
                .orElseThrow(() -> new AppException(DataResponse.builder()
                        .status(Status.FAILED)
                        .code(MessageCode.NOT_SUPPORT)
                        .build())
                );
    }
}
