package com.ale.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Share {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User shareWith;

    /*Post or document share with user*/
    @ManyToOne
    private Post post;

    @ManyToOne
    private Document document;

    @Version
    @ColumnDefault("0")
    private Long version;
}
