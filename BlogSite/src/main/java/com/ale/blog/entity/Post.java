package com.ale.blog.entity;

import com.ale.blog.entity.state.PostState;
import com.ale.blog.handler.utils.MessageValidate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60)
    @NotBlank(message = MessageValidate.NOT_BLANK)
    private String title;

    @Column(length = 160)
    @NotBlank(message = MessageValidate.NOT_BLANK)
    private String metaTitle;

    @Column(unique = true, length = 100)
    @NotBlank(message = MessageValidate.NOT_BLANK)
    @Pattern(regexp = "^(?:[a-zA-Z0-9]+(?:-[a-zA-Z0-9])*)+$")
    private String slug;

    private PostState state;

    @NotNull
    private Instant createDate;

    private Instant updateDate;

    private Instant publishedDate;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = MessageValidate.NOT_BLANK)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private User author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToMany(mappedBy = "posts", fetch = FetchType.LAZY)
    private List<Category> categories;
}
