package com.ale.blog.entity;

import com.ale.blog.entity.state.OAuthProvider;
import com.ale.blog.entity.state.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Indexed
@Table(name = "author")
@Data
@Builder
@FieldNameConstants(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID uuid;

    @NotBlank
    private String password;

    @FieldNameConstants.Include
    @KeywordField
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9-]{4,50}$")
    @Column(unique = true,length = 50)
    private String username;

    @Column(length = 50)
    private String firstName;
    @Column(length = 50)
    private String lastName;

    @NotBlank
    @FieldNameConstants.Include
    @FullTextField
    @Column(length = 101)
    private String fullName;

    @Column(length = 15)
    @Pattern(regexp = "^[0-9+]{10,15}$")
    private String phoneNumber;

    @NotBlank
    @Column(unique = true)
    private String email;

    @NotNull
    private Instant registered;

    private String story;

    @NotNull
    private UserRole role;

    @NotNull
    private OAuthProvider provider;

    @OneToOne(fetch = FetchType.LAZY)
    private Image avatar;

    @Version
    @ColumnDefault("0")
    private Long version;
}
