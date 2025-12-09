package com.example.taf.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@DiscriminatorValue("CompteEpargne")
public class CompteEpargne extends CompteBancaire{
    private double tauxInteret;
}
