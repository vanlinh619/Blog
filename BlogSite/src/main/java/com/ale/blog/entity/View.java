package com.ale.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class View implements Serializable {
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

    @Version
    @ColumnDefault("0")
    private Long version;
}
