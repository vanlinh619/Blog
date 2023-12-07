package com.ale.broadcast.service;

import com.ale.blog.repository.ImageRepository;
import com.ale.blog.service.ImageService;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl extends com.ale.blog.service.ImageServiceImpl implements ImageService {
    public ImageServiceImpl(ImageRepository imageRepository) {
        super(imageRepository);
    }
}
