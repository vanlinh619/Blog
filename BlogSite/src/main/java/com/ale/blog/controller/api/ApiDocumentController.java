package com.ale.blog.controller.api;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.DocumentMapper;
import com.ale.blog.handler.mapper.pojo.request.DocumentRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.security.UserAccess;
import com.ale.blog.service.DocumentService;
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
@RequestMapping("api/authorize/document")
public class ApiDocumentController {
    private final DocumentService documentService;
    private final DocumentMapper documentMapper;

    @PostMapping
    public DataResponse createDocument(Authentication authentication, @Valid @RequestBody DocumentRequest documentRequest) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        Document document = documentService.createDocument(documentRequest, userAccess.getCurrentUser());
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(documentMapper.toDocumentResponse(document))
                .build();
    }
}
