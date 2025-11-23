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
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateOp;
    private double montant;
    private TypeOp type;

    @ManyToOne

    private CompteBancaire compteBancaire;

}
