package com.ale.blog.entity;

import com.ale.blog.entity.state.CategoryLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"author_uuid", "title"}) )
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])*$")
    @Column(unique = true, length = 300)
    private String slug;

    @Column(length = 2000)
    private String introduction;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Post> posts;

    private CategoryLevel level;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Category> children;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
}
