package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Favourite;
import com.ale.blog.handler.mapper.pojo.response.FavouriteResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FavouriteMapperImpl implements FavouriteMapper {
    private final ModelMapper modelMapper;
    @Override
    public FavouriteResponse toFavouriteRepository(Favourite favourite) {
        FavouriteResponse favouriteResponse = modelMapper.map(favourite, FavouriteResponse.class);
        favouriteResponse.setPostSlug(favourite.getPost().getSlug());
        return favouriteResponse;
    }
}
