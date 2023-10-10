package com.ale.blog.service;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.CategoryLevel;
import com.ale.blog.entity.state.SlugType;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.pojo.request.CategoryRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.handler.utils.Convert;
import com.ale.blog.handler.utils.Format;
import com.ale.blog.handler.utils.StaticMessage;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final SlugIdService slugIdService;
    private final ModelMapper mapper;

    @Override
    public Category createCategory(CategoryRequest categoryRequest, User author) {
        Category category = mapper.map(categoryRequest, Category.class);
        category.setAuthor(author);
        category.setSlug(slugIdService.getId(SlugType.CATEGORY)+"-"+Format.toHref(categoryRequest.getTitle()));
        if (categoryRequest.getParentId() != null) {
            Category parent = getCategoryByIdAndAuthor(categoryRequest.getParentId(), author);
            addCategoryLevel(category, parent);
            category.setParent(parent);
        } else {
            category.setLevel(CategoryLevel.LEVEL_1);
        }
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllLevelByUser(CategoryLevel level, User user) {
        return categoryRepository.findAllByLevelAndAuthor(level, user);
    }

    @Override
    public Category getCategoryBySlugAndUsername(String slug, String username) {
        AtomicReference<Category> reference = new AtomicReference<>();
        categoryRepository.findFirstBySlugAndAuthor_Username(slug, username).ifPresentOrElse(reference::set, () -> {
            throw new AppException(DataResponse.builder().code(MessageCode.NOT_FOUND).status(Status.FAILED).message(StaticMessage.SLUG_NOT_FOUND).build());
        });
        return reference.get();
    }

    @Override
    public Category getCategoryByIdAndAuthor(Long id, User author) {
        return categoryRepository.findCategoryByIdAndAuthor(id, author).orElseThrow(() -> new AppException(DataResponse.builder()
                .status(Status.FAILED)
                .code(MessageCode.NOT_FOUND)
                .message(StaticMessage.ID_DOES_NOT_EXIST)
                .build()
        ));
    }

    private void addCategoryLevel(Category child, Category parent) {
        switch (parent.getLevel()) {
            case LEVEL_1 -> child.setLevel(CategoryLevel.LEVEL_2);
            case LEVEL_2 -> child.setLevel(CategoryLevel.LEVEL_3);
            case LEVEL_3 -> throw new AppException(DataResponse.builder()
                    .code(MessageCode.BEYOND_THE_CATEGORY_LEVEL)
                    .status(Status.FAILED)
                    .build()
            );
        }
    }
}
