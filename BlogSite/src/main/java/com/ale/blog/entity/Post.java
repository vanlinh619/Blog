package com.ale.blog.entity;

import com.ale.blog.entity.state.PostState;
import jakarta.persistence.*;
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
    private String title;
    @Column(length = 160)
    private String metaTitle;
    @Column(unique = true, length = 100)
    private String slug;
    private PostState state;
    private Instant createDate;
    private Instant updateDate;
    private Instant publishedDate;
    @Column(columnDefinition = "TEXT")
    private String content;

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
}
