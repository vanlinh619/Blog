package com.ale.blog.entity;

import jakarta.persistence.*;
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
public class Category {
    @Id
    private Long id;
    @Column(length = 60)
    private String title;
    @Column(length = 160)
    private String metaTitle;
    @Column(unique = true, length = 100)
    private String slug;
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "category_post",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> posts;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Category> categories;
}
