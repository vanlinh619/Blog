package com.ale.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"section_id", "post_id"}))
@Data
@Builder
@FieldNameConstants(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class DocumentLinked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String slug;

    @NotNull
    @FieldNameConstants.Include
    private Integer priority;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private DocumentSection section;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private Post post;
}
