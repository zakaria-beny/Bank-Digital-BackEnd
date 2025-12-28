package com.example.taf.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter  @NoArgsConstructor @AllArgsConstructor @Builder

public class Client {
    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    private String nom;
    private String email;

    @OneToMany(mappedBy = "client",fetch=FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<CompteBancaire> comptes = new ArrayList<>();

    @PrePersist
    public void ensureId() {
        if (this.id == null || this.id.isBlank()) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
