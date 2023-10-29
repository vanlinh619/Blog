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
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Indexed
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"author_uuid", "title"}) )
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FieldNameConstants.Include
    @FullTextField
    @NotBlank
    private String title;

    @Column(length = 2000)
    private String introduction;

    @NotBlank
    @Column(unique = true, length = 300)
    @Pattern(regexp = "^[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])*$")
    private String slug;

    @NotNull
    private PostState state;

    @NotNull
    @FieldNameConstants.Include
    private Instant createDate;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String content;

    @FieldNameConstants.Include
    @FullTextField
    @Column(columnDefinition = "TEXT")
    private String rawContent;

    @Column(nullable = false)
    private Long view = 0L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TableOfContent> tableOfContents;

    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY)
    private DocumentLinked linked;

    @ManyToOne(fetch = FetchType.LAZY)
    private Document document;

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY)
    private List<Share> shares;
}
