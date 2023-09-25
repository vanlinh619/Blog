package com.ale.blog.entity;

import com.ale.blog.entity.state.HtmlTag;
import com.ale.blog.handler.utils.MessageValidate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeadTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private HtmlTag tag;

    @NotBlank
    private String content;

    @NotBlank
    @Column(unique = true, length = 100)
    @Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z0-9-]*[a-zA-Z0-9])*$")
    private String href;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
