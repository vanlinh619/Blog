package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Favourite;
import com.ale.blog.handler.mapper.pojo.response.FavouriteResponse;
import com.ale.blog.repository.FavouriteRepository;

public interface FavouriteMapper extends Mapper{
    FavouriteResponse toFavouriteRepository(Favourite favourite);
}
