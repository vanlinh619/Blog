package com.ale.blog.controller.api;

import com.ale.blog.entity.Notification;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.NotificationMapper;
import com.ale.blog.handler.mapper.PageMapper;
import com.ale.blog.handler.mapper.pojo.request.PageRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.NotificationResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.handler.utils.SortType;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.security.UserAccess;
import com.ale.blog.service.NotificationService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutorService;

@RestController
@RolesAllowed(value = {UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR, UserRole.Fields.USER})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("api/authorize/notification")
public class ApiNotificationController {
    private final NotificationService notificationService;
    private final PageMapper<Notification, NotificationResponse> pageMapper;
    private final NotificationMapper notificationMapper;

    @GetMapping
    public DataResponse getAllNotification(Authentication authentication, @Valid PageRequest pageRequest) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        Page<Notification> notificationPage = notificationService.loadNotification(userAccess.getUser(), QueryRequest.builder()
                .page(pageRequest.getPage())
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(List.of(Notification.Fields.updateDate))
                .sortType(SortType.DESC.name())
                .build()
        );
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(pageMapper.toPageResponse(notificationPage, notificationMapper::toNotificationResponse))
                .build();
    }

    @GetMapping("/count")
    public DataResponse countNewNotification(Authentication authentication) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        Integer countNewComment = notificationService.countNewNotification(userAccess.getUser());
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(countNewComment)
                .build();
    }
}
