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
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableOfContent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private HtmlTag tag;

    @NotBlank
    private String content;

    @NotBlank
    @Column(length = 300)
    @Pattern(regexp = "^[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])*$")
    private String href;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Version
    @ColumnDefault("0")
    private Long version;
}
