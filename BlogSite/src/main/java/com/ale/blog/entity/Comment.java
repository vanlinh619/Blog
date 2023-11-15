package com.ale.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    @NotNull
    @FieldNameConstants.Include
    private Instant createDate;

    private String replyUsername;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment replyFor;

    @OneToMany(mappedBy = "replyFor",fetch = FetchType.LAZY)
    private List<Comment> replies;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment superParent;

    @OneToMany(mappedBy = "superParent",fetch = FetchType.LAZY)
    private List<Comment> children;

    @NotNull
    @FieldNameConstants.Include
    private Long childrenSize;

    @Version
    @ColumnDefault("0")
    private Long version;
}
