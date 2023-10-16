package com.ale.blog.entity;

import com.ale.blog.entity.state.OAuthProvider;
import com.ale.blog.entity.state.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "author")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @NotBlank
    private String password;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9-]{4,50}$")
    @Column(unique = true,length = 50)
    private String username;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(length = 15)
    @Pattern(regexp = "^[0-9+]{10,15}$")
    private String phoneNumber;

    @NotBlank
    @Column(unique = true)
    private String email;

    private Instant registered;

    private String intro;

    @NotNull
    private UserRole role;

    @NotNull
    private OAuthProvider provider;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Category> categories;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Image> images;
}
