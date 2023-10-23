package com.ale.blog.controller.view;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.request.PageRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.utils.SortType;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.handler.utils.UtilMethod;
import com.ale.blog.service.DocumentService;
import com.ale.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("doc")
public class DocumentController {
    private final UserService userService;
    private final DocumentService documentService;

    @GetMapping("{username}")
    public String viewAllDocument(
            @PathVariable String username,
            @Valid PageRequest pageRequest,
            Model model,
            Authentication authentication
    ) {
        User author = userService.getByUsername(username);
        Page<Document> documentPage = documentService.findAllByAuthor(author, QueryRequest.builder()
                .page(pageRequest.getPage() - 1)
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(Document.Fields.createDate)
                .sortType(SortType.DESC.name())
                .build());
        model.addAttribute("documentPage", documentPage);
        model.addAttribute("author", author);
        model.addAttribute("user", UtilMethod.authenticated(authentication));
        return "all-document";
    }

    @GetMapping("{username}/{slug}")
    public String viewDocument(@PathVariable String username, @PathVariable String slug, Model model, Authentication authentication) {
        User author = userService.getByUsername(username);
        Document document = documentService.getDocumentBySlug(slug, author);
        model.addAttribute("document", document);
        model.addAttribute("author", author);
        model.addAttribute("user", UtilMethod.authenticated(authentication));
        return "document";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({Exception.class})
    public String handleValidationExceptions(Exception e) {
        e.printStackTrace();
        return "404";
    }
}
