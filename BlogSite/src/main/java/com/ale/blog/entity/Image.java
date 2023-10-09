package com.ale.blog.entity;

import com.ale.blog.entity.state.ImageExtension;
import com.ale.blog.entity.state.ImageState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"folder", "hash"}) )
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public class Image {
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
