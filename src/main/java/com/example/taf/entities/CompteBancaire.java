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
@Inheritance(strategy = InheritanceType.JOINED)
public class CompteBancaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateCreation ;
    private double solde;
    @Enumerated(EnumType.STRING)
    private StatCompte statut;
    private  String devise;

    @OneToMany(mappedBy = "compteBancaire",fetch = FetchType.EAGER)
    private List<Operation> operations = new ArrayList<>();

    @ManyToOne

    private Client client;

}
