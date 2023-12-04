package com.ale.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"document_id", "title"}))
@Data
@Builder
@FieldNameConstants(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class DocumentSection implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    @FieldNameConstants.Include
    private Integer priority;

    @NotNull
    private Integer size;

    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
    @OrderBy("priority")
    private List<DocumentLinked> linked;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Document document;

    @Version
    @ColumnDefault("0")
    private Long version;
}
