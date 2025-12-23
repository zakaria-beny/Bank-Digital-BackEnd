package com.example.taf.dto;

import lombok.Data;

@Data
public class CompteBancaireDTO {
    private String type;
    private double solde;
    private Double decouvert;
    private Double tauxInteret;
    private String numeroCompte;

    private Long clientId;
}
