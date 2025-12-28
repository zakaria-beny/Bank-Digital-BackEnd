package com.example.taf.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.example.taf.entities.StatCompte;
import lombok.Data;
import java.util.Date;

@Data
public class CompteBancaireDTO {

    private String id;
    private String numeroCompte;
    private double solde;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateCreation;
    private StatCompte statut;
    private String devise;
    private String type;


    private String clientId;
    private ClientDTO clientdto;

    private Double decouvert;
    private Double tauxInteret;
}