package com.ale.blog.entity;

import com.ale.blog.handler.utils.MessageValidate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Pattern(regexp = "^[a-zA-Z0-9]$")
    @Column(unique = true, length = 50)
    private String title;

    @NotBlank
    @Column(unique = true, length = 100)
    @Pattern(regexp = "^[a-zA-Z0-9]$")
    private String slug;

    @NotNull
    private Long used = 0L;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<Post> posts;
}
