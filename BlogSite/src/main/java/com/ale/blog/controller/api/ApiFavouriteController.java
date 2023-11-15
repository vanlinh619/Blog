package com.ale.blog.controller.api;

import com.ale.blog.entity.Favourite;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.FavouriteMapper;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.security.UserAccess;
import com.ale.blog.service.FavouriteService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR, UserRole.Fields.USER})
@RequiredArgsConstructor
@RequestMapping("api/authorize/favourite")
public class ApiFavouriteController {
    private final FavouriteService favouriteService;
    private final FavouriteMapper favouriteMapper;

    @PutMapping("{postSlug}")
    public DataResponse favourite(Authentication authentication, @PathVariable String postSlug) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        Favourite favourite = favouriteService.updateFavouritePost(userAccess.getUser(), postSlug);
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(favouriteMapper.toFavouriteRepository(favourite))
                .build();
    }
}
