package com.example.taf.dto;

import com.example.taf.entities.StatCompte;
import lombok.Data;
import java.util.Date;

@Data
public class CompteBancaireDTO {

    private Long id;
    private String numeroCompte;
    private double solde;
    private Date dateCreation;
    private StatCompte statut;
    private String devise;
    private String type;


    private Long clientId;
    private ClientDTO clientdto;

    private Double decouvert;
    private Double tauxInteret;
}