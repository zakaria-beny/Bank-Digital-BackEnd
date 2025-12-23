package com.example.taf.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
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

}
