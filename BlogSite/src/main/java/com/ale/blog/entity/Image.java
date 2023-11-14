package com.ale.blog.entity;

import com.ale.blog.entity.state.ImageExtension;
import com.ale.blog.entity.state.ImageState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"folder", "hash"}) )
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String hash;

    /**
     * Use username as folder
     * */
    @NotBlank
    @Column(length = 50)
    private String folder;

    @NotBlank
    private String name;

    @NotNull
    @FieldNameConstants.Include
    private Instant createDate;

    @Range(min = 1, max = 5000000)
    @NotNull
    private Long size;

    @NotNull
    private ImageExtension extension;

    @NotNull
    private ImageState state;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
}
