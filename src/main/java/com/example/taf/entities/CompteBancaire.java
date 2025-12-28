package com.example.taf.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class CompteBancaire {
    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;
    private Date dateCreation ;
    private double solde;
    private String numeroCompte;

    @Enumerated(EnumType.STRING)
    private StatCompte statut;
    private  String devise;

    @OneToMany(mappedBy = "compteBancaire",fetch = FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Operation> operations = new ArrayList<>();

    @ManyToOne

    private Client client;

    @PrePersist
    public void ensureId() {
        if (this.id == null || this.id.isBlank()) {
            this.id = UUID.randomUUID().toString();
        }
    }

}
