package dev.desan.minipayments.infrastructure.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", updatable = false)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on", updatable = false)
    private Date updatedOn;

    @PrePersist
    public void init() {
        createdOn = Date.from(java.time.ZonedDateTime.now(ZoneOffset.UTC).toInstant());
        updatedOn = Date.from(java.time.ZonedDateTime.now(ZoneOffset.UTC).toInstant());
    }
}
