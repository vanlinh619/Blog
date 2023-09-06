package com.ale.blog.entity;

import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.utils.MessageType;
import com.ale.blog.handler.utils.MessageValidate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(length = 64)
    @NotBlank(message = MessageValidate.NOT_BLANK)
    private String password;
    @Column(unique = true,length = 50)
    @NotBlank(message = MessageValidate.NOT_BLANK)
    private String username;
    @Column(length = 50)
    private String firstName;
    @Column(length = 50)
    private String lastName;
    @Column(length = 15)
    private String phoneNumber;
    @Column(length = 50)
    private String email;
    private Instant registered;
    private String intro;
    private UserRole role;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Comment> comments;
}
