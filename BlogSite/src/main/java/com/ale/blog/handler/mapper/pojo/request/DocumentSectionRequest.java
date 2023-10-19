package com.ale.blog.handler.mapper.pojo.request;

import com.ale.blog.entity.Document;
import com.ale.blog.entity.DocumentLinked;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentSectionRequest {
    @NotBlank
    private String title;
    @NotNull
    private Long documentId;
}
