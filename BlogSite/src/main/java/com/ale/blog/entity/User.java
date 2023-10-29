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
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import java.io.Serializable;
import java.time.Instant;
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

    @KeywordField
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9-]{4,50}$")
    @Column(unique = true,length = 50)
    private String username;

    @KeywordField
    @Column(length = 50)
    private String firstName;

    @KeywordField
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
}
