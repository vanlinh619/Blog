package com.ale.blog.controller.api;

import com.ale.blog.entity.User;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.UserMapper;
import com.ale.blog.handler.mapper.pojo.request.UserInfoRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.security.UserAccess;
import com.ale.blog.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR, UserRole.Fields.USER})
@RequestMapping("api/authorize/user")
@AllArgsConstructor
public class ApiUserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PutMapping("info")
    public DataResponse updateInfo(Authentication authentication, @Valid @ModelAttribute UserInfoRequest userInfoRequest) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        User user = userService.getByUsername(userAccess.getCurrentUser().getUsername());
        User userNew = userService.updateInfo(user, userInfoRequest);
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(userMapper.toUserInfoResponse(userNew))
                .build();
    }
}
