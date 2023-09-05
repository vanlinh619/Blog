package com.ale.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(length = 50)
    private String firstName;
    @Column(length = 50)
    private String lastName;
    @Column(length = 15)
    private String phoneNumber;
    @Column(length = 32)
    private String password;
    @Column(length = 50)
    private String email;
    private Instant registered;
    private String intro;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;
}
