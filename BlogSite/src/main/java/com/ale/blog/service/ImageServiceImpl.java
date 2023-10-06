package com.ale.blog.service;

import com.ale.blog.entity.Image;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.ImageExtension;
import com.ale.blog.entity.state.ImageState;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.utils.Convert;
import com.ale.blog.handler.utils.MessageCode;
import com.ale.blog.handler.utils.StaticMessage;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ExecutorService executorService;
    private final ImageRepository imageRepository;
    private final UserService userService;

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public Image saveImage(MultipartFile image, String uuid) {
        User author = userService.getById(UUID.fromString(uuid));
        Path path = Paths.get(String.format("%s/%s", StaticVariable.IMAGE_DIRECTORY, author.getUsername()));

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            String type = StringUtils.getFilenameExtension(fileName);

            Image newImage = new Image();
            newImage.setName(fileName);
            newImage.setFolder(author.getUsername());
            newImage.setExtension(ImageExtension.valueOf(Objects.requireNonNull(type).toUpperCase()));
            newImage.setSize(image.getSize());
            newImage.setAuthor(author);
            newImage.setCreateDate(Instant.now());
            newImage.setState(ImageState.PERSIST);

            imageRepository.save(newImage);
            InputStream inputStream = image.getInputStream();
            Path filePath = path.resolve(String.format("%s.%s", newImage.getId(), newImage.getExtension().toString().toLowerCase()));
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();

            return newImage;
        } catch (IOException e) {
            throw new AppException(DataResponse.builder()
                    .code(MessageCode.ERROR_IMAGE)
                    .status(DataResponse.ResponseStatus.FAILED)
                    .message(StaticMessage.UNABLE_TO_SAVE_IMAGE)
                    .build());
        }
    }

    @Override
    public Resource getImageResource(String id) {
        Optional<Resource> optional = findImageResource(id);
        if (optional.isPresent()) {
            return optional.get();
        }

        throw new AppException(DataResponse.builder()
                .code(MessageCode.NOT_FOUND)
                .message(StaticMessage.FILE_NOT_FOUND)
                .build());
    }

    @Override
    public Page<Image> getAllByAuthor(User author, QueryRequest queryRequest) {
        return imageRepository.findAllByAuthorAndState(author, ImageState.PERSIST, Convert.pageRequest(queryRequest));
    }

    private Optional<Resource> findImageResource(String id) {
        return imageRepository.findFirstByIdAndState(UUID.fromString(id), ImageState.PERSIST)
                .map(image -> {
                    Path path = Paths.get(String.format(
                            "%s/%s/%s.%s",
                            StaticVariable.IMAGE_DIRECTORY,
                            image.getFolder(),
                            image.getId(),
                            image.getExtension().toString().toLowerCase()
                    ));
                    if (Files.exists(path)) {
                        return UrlResource.from(path.toUri());
                    } else {
                        executorService.execute(() -> {
                            image.setState(ImageState.ERROR);
                            imageRepository.save(image);
                        });
                        return null;
                    }
                });
    }
}
