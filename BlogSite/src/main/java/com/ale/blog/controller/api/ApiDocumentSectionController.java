package com.ale.blog.controller.api;

import com.ale.blog.entity.DocumentSection;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.DocumentSectionMapper;
import com.ale.blog.handler.mapper.pojo.request.DocumentSectionRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.security.UserAccess;
import com.ale.blog.service.DocumentSectionService;
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
@RequestMapping("api/authorize/section")
public class ApiDocumentSectionController {
    private final DocumentSectionMapper documentSectionMapper;
    private final DocumentSectionService sectionService;

    @PostMapping
    public DataResponse addSection(Authentication authentication, @Valid @RequestBody DocumentSectionRequest sectionRequest) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        DocumentSection section = sectionService.addSection(sectionRequest, userAccess.getUser());
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(documentSectionMapper.toDocumentSectionResponse(section))
                .build();
    }
}
