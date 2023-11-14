package com.ale.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Instant timestamp;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    /**
     * Ip address or User
    * */
    private String ipaddress;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
