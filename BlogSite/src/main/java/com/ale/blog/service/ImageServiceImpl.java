package com.ale.blog.service;

import com.ale.blog.entity.Image;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.ImageExtension;
import com.ale.blog.entity.state.ImageState;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.handler.utils.Convert;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.utils.StaticMessage;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.repository.ImageRepository;
import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutorService;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ExecutorService executorService;
    private final ImageRepository imageRepository;

    @Override
    public Image saveImage(MultipartFile multipartFile, User author) {
        try {
            String hash = Hashing.sha256().hashBytes(multipartFile.getBytes()).toString();
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            String type = StringUtils.getFilenameExtension(fileName);
            ImageExtension extension = ImageExtension.valueOf(Objects.requireNonNull(type).toUpperCase());

            return imageRepository.findFirstByHashAndAuthor(hash, author).map(image -> {
                if (image.getState() == ImageState.DELETED) {
                    image.setState(ImageState.PERSIST);
                    image.setName(fileName);
                    image.setCreateDate(Instant.now());
                    image.setExtension(extension);
                    imageRepository.save(image);
                } else {
                    throw new AppException(DataResponse.builder()
                            .status(Status.FAILED)
                            .code(MessageCode.DUPLICATE_ENTRY)
                            .message(image.getId().toString())
                            .build());
                }
                return image;
            }).orElseGet(() -> {
                Image image = new Image();
                image.setName(fileName);
                image.setHash(hash);
                image.setFolder(author.getUsername());
                image.setExtension(extension);
                image.setSize(multipartFile.getSize());
                image.setAuthor(author);
                image.setCreateDate(Instant.now());
                image.setState(ImageState.PERSIST);
                imageRepository.save(image);
                saveToFile(multipartFile, image, author);
                return image;
            });
        } catch (IOException e) {
            throw new AppException(DataResponse.builder()
                    .code(MessageCode.ERROR_IMAGE)
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

    @Override
    public Boolean deleteImageById(String id, User author) {
        UUID uuid = UUID.fromString(id);
        imageRepository.findFirstByIdAndStateAndAuthor(uuid, ImageState.PERSIST, author).ifPresentOrElse(image -> {
            imageRepository.updateStateById(uuid, ImageState.DELETED);
        }, () -> {
            throw new AppException(DataResponse.builder()
                    .status(Status.FAILED)
                    .code(MessageCode.NOT_FOUND)
                    .message(StaticMessage.FILE_NOT_FOUND)
                    .build());
        });
        return true;
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
                        return null;
                    }
                });
    }

    private void saveToFile(MultipartFile multipartFile, Image image, User author) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path path = Paths.get(String.format("%s/%s", StaticVariable.IMAGE_DIRECTORY, author.getUsername()));
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Path filePath = path.resolve(String.format("%s.%s", image.getId(), image.getExtension().toString().toLowerCase()));
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new AppException(DataResponse.builder()
                    .code(MessageCode.ERROR_IMAGE)
                    .status(Status.FAILED)
                    .message(StaticMessage.UNABLE_TO_SAVE_IMAGE)
                    .build());
        }
    }
}
