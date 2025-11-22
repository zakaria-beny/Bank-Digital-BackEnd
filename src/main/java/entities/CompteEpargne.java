package entities;

import jakarta.persistence.Entity;

@Entity
public class CompteEpargne extends CompteBancaire{
    private double tauxInteret;
}
