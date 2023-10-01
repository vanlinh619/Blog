package com.ale.blog.entity;

import com.ale.blog.handler.utils.MessageValidate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 60)
    private String title;

    @NotBlank
    @Column(length = 160)
    private String metaTitle;

    @NotBlank
    @Column(unique = true, length = 100)
    @Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z0-9-]*[a-zA-Z0-9])*$")
    private String slug;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<Post> posts;
}
