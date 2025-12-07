package com.example.taf.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompteEpargne extends CompteBancaire{
    private double tauxInteret;
}
