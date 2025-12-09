package com.example.taf.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CompteCourant")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompteCourant extends CompteBancaire {

    private double decouvert;



}
