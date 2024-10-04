package com.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public abstract class Basic {

    @Id
    private String id;

    private String name;

    @Lob
    private String description;

    private OffsetDateTime dateCreated;

    private OffsetDateTime dateUpdated;

    private boolean softDelete;

    @PrePersist
    public void generateUUID() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
