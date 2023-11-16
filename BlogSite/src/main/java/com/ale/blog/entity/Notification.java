package com.ale.blog.entity;

import com.ale.blog.entity.state.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"author_uuid", "object_id"}))
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Boolean seen;

    @NotNull
    private Instant createDate;

    @NotNull
    private NotificationType type;

    @NotNull
    @Column(name = "object_id")
    private String objectId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @Version
    @ColumnDefault("0")
    private Long version;
}
