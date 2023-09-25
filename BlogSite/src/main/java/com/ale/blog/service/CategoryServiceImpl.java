package com.ale.blog.service;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.CategoryLevel;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.request.CategoryRequest;
import com.ale.blog.handler.mapper.response.DataResponse;
import com.ale.blog.handler.utils.MessageCode;
import com.ale.blog.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    @Lazy
    private UserService userService;
    private final ModelMapper mapper;
    @Override
    public Category createCategory(CategoryRequest categoryRequest) {
        Category category = mapper.map(categoryRequest, Category.class);
        User author = userService.getById(UUID.fromString(categoryRequest.getAuthor()));
        category.setAuthor(author);
        category.setSlug(category.getSlug());
        if(categoryRequest.getParentId() != null) {
            Category parent = defaultGetById(categoryRequest.getParentId(), repository);
            addCategoryLevel(category, parent);
            category.setParent(parent);
        } else {
            category.setLevel(CategoryLevel.LEVEL_1);
        }
        return repository.save(category);
    }

    @Override
    public List<Category> getAllLevelByUser(CategoryLevel level, User user) {
        return repository.findAllByLevelAndAuthor(level, user);
    }

    @Override
    public Category getCategory(Long id) {
        return defaultGetById(id, repository);
    }

    @Override
    public Category getDefaultCategory() {
        return repository.findFirstBySlug("all").get();
    }

    private void addCategoryLevel(Category child, Category parent) {
        switch (parent.getLevel()) {
            case LEVEL_1 -> {
                child.setLevel(CategoryLevel.LEVEL_2);
                break;
            }
            case LEVEL_2 -> {
                child.setLevel(CategoryLevel.LEVEL_3);
                break;
            }
            case LEVEL_3 -> {
                throw new AppException(DataResponse.builder()
                        .code(MessageCode.BEYOND_THE_CATEGORY_LEVEL)
                        .status(DataResponse.ResponseStatus.FAILED)
                        .build());
            }
        }
    }
}
