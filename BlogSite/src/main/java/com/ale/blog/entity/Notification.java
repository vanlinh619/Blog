package com.ale.blog.entity;

import com.ale.blog.entity.state.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @FieldNameConstants.Include
    private Boolean seen;

    @NotNull
    private Instant createDate;

    @NotNull
    @FieldNameConstants.Include
    private Instant updateDate;

    @NotNull
    private NotificationType type;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Comment comment;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User receiver;

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "notification_actor",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_uuid")
    )
    private List<User> actors;

    @Version
    @ColumnDefault("0")
    private Long version;
}
