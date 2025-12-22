package com.example.taf.dto;

import com.example.taf.entities.StatCompte;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CompteEpargneDTO extends CompteBancaireDTO {
    private Long id;
    private Date dateCreation;
    private StatCompte statut;
    private String devise;
    private ClientDTO clientdto;
}
