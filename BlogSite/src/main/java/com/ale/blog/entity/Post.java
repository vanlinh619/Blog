package com.ale.blog.entity;

import com.ale.blog.entity.state.PostState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 60)
    private String title;

    @NotBlank
    @Column(length = 160)
    private String metaTitle;

    @Column(length = 1000)
    private String introduction;

    @NotBlank
    @Column(unique = true, length = 100)
    @Pattern(regexp = "^[a-zA-Z](?:[a-zA-Z0-9-]*[a-zA-Z0-9])*$")
    private String slug;

    private PostState state;

    @NotNull
    @FieldNameConstants.Include
    private Instant createDate;

    private Instant updateDate;

    private Instant publishedDate;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "BIGINT(19) default 0")
    private Long view = 0L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HeadTable> headTables;
}
