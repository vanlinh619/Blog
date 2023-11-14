package com.ale.blog.service;

import com.ale.blog.entity.Favourite;
import com.ale.blog.repository.FavouriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {
    private final FavouriteRepository favouriteRepository;

    @Override
    public Class<Favourite> getEntityClass() {
        return Favourite.class;
    }
}
