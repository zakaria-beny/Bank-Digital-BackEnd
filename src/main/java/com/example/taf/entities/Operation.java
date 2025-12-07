package com.example.taf.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateOp;
    private double montant;
    @Enumerated(EnumType.STRING)
    private TypeOp type;

    @ManyToOne

    private CompteBancaire compteBancaire;

}
