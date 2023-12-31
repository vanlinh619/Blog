package com.ale.blog.controller.view;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.DocumentState;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.pojo.request.PageDocumentRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.utils.SortType;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.handler.utils.TextUtil;
import com.ale.blog.handler.utils.UserUtil;
import com.ale.blog.service.DocumentService;
import com.ale.blog.service.ImageService;
import com.ale.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("doc")
public class DocumentController {
    private final UserService userService;
    private final DocumentService documentService;
    private final ImageService imageService;

    @GetMapping("{username}")
    public String viewAllDocument(
            @PathVariable String username,
            @Valid PageDocumentRequest pageRequest,
            Model model,
            Authentication authentication
    ) {
        Optional<User> userOptional = UserUtil.owner(authentication);
        User author = userService.getByUsername(username);
        Page<Document> documentPage = documentService.findAllByAuthor(author, userOptional.orElse(null), DocumentState.valueOf(pageRequest.getScope().toUpperCase()), QueryRequest.builder()
                .page(pageRequest.getPage() - 1)
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(List.of(Document.Fields.createDate))
                .sortType(SortType.DESC.name())
                .build());
        model.addAttribute("documentPage", documentPage);
        model.addAttribute("author", author);
        userOptional
                .map(user -> {
                    model.addAttribute("user", user);
                    return user;
                })
                .flatMap(imageService::getAvatar)
                .ifPresent(image -> {
                    model.addAttribute("avatar", image);
                });
        model.addAttribute("breadcrumb", List.of(
                List.of(TextUtil.ALL_DOCUMENT, "")
        ));
        model.addAttribute("scope", pageRequest.getScope().toLowerCase());
        return "all-document";
    }

    @GetMapping("{username}/{slug}")
    public String viewDocument(@PathVariable String username, @PathVariable String slug, Model model, Authentication authentication) {
        Optional<User> userOptional = UserUtil.owner(authentication);
        User author = userService.getByUsername(username);
        Document document = documentService.getDocumentBySlug(slug, author, userOptional.orElse(null));
        documentService.setEntriesOfDocument(document);
        model.addAttribute("document", document);
        model.addAttribute("author", author);
        userOptional
                .map(user -> {
                    model.addAttribute("user", user);
                    return user;
                })
                .flatMap(imageService::getAvatar)
                .ifPresent(image -> {
                    model.addAttribute("avatar", image);
                });
        model.addAttribute("breadcrumb", List.of(
                List.of(TextUtil.ALL_DOCUMENT, TextUtil.getLinkAllDocument(author)),
                List.of(document.getTitle(), "")
        ));
        return "document";
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Exception.class})
    public String handleValidationExceptions(Exception e, Model model) {
        e.printStackTrace();
        if (e instanceof AppException appException && appException.getResponse().getCode() == MessageCode.UN_AUTHORIZE) {
            model.addAttribute("message", "Un Authorize");
        } else {
            model.addAttribute("message", "Page not found");
        }
        return "404";
    }
}
