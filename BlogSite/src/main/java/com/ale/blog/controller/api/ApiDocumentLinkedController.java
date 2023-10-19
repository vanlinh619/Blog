package com.ale.blog.controller.api;

import com.ale.blog.entity.DocumentLinked;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.DocumentLinkedMapper;
import com.ale.blog.handler.mapper.pojo.request.DocumentLinkedRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.security.UserAccess;
import com.ale.blog.service.DocumentLinkedService;
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
@RequestMapping("api/authorize/linked")
public class ApiDocumentLinkedController {
    private final DocumentLinkedService linkedService;
    private final DocumentLinkedMapper linkedMapper;

    @PostMapping
    public DataResponse linkedDocument(Authentication authentication, @Valid @RequestBody DocumentLinkedRequest linkedRequest){
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        DocumentLinked linked = linkedService.linkedDocument(linkedRequest, userAccess.getUser());
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(linkedMapper.toDocumentLinkedResponse(linked))
                .build();
    }

}
