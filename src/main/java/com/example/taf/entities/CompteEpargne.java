package com.example.taf.entities;

import jakarta.persistence.Entity;

@Entity
public class CompteEpargne extends CompteBancaire{
    private double tauxInteret;
}
