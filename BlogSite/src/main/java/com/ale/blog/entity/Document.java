package com.ale.blog.entity;

import com.ale.blog.entity.state.ShareState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Formula;

import java.time.Instant;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"author_uuid", "title"}) )
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(length = 2000)
    private String introduction;

    @NotNull
    private ShareState state;

    @NotBlank
    @Column(unique = true, length = 300)
    @Pattern(regexp = "^[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])*$")
    private String slug;

    @NotNull
    @FieldNameConstants.Include
    private Instant createDate;

    @NotNull
    private Integer size;

    @OneToMany(
            mappedBy = "document",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    @OrderBy("priority")
    private List<DocumentSection> sections;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
}
