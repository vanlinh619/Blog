package com.ale.blog.entity;

import com.ale.blog.entity.state.DocumentState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"author_uuid", "title"}) )
@Data
@Builder
@FieldNameConstants(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(length = 2000)
    private String introduction;

    @NotNull
    private DocumentState state;

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

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
    private List<Share> shares;

    @Version
    @ColumnDefault("0")
    private Long version;
}
