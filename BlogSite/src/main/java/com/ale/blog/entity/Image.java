package com.ale.blog.entity;

import com.ale.blog.entity.state.ImageExtension;
import com.ale.blog.entity.state.ImageState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String folder;

    @NotNull
    private String name;

    private Instant createDate;

    @Max(value = 4000000)
    private Long size;

    @NotNull
    private ImageExtension extension;

    @NotNull
    private ImageState state;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
}
