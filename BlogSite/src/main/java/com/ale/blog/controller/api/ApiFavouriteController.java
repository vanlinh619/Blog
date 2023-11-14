package com.ale.blog.controller.api;

import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.service.FavouriteService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR, UserRole.Fields.USER})
@RequiredArgsConstructor
@RequestMapping("api/authorize/favourite")
public class ApiFavouriteController {
    private final FavouriteService favouriteService;

//    @PutMapping("")
//    public DataResponse favourite() {
//
//    }
}
