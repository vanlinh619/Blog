package com.ale.blog.controller.api;

import com.ale.blog.entity.Share;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.ShareMapper;
import com.ale.blog.handler.mapper.pojo.request.ShareRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.security.UserAccess;
import com.ale.blog.service.ShareService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR})
@AllArgsConstructor
@RequestMapping("api/authorize/share")
public class ApiShareController {
    private final ShareService shareService;
    private final ShareMapper shareMapper;

    @PostMapping
    public DataResponse shareUser(Authentication authentication, @Valid @RequestBody ShareRequest shareRequest) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        Share share = shareService.shareWithUser(shareRequest, userAccess.getCurrentUser());
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(shareMapper.toShareResponse(share, shareRequest))
                .build();
    }
}
