package com.example.taf.dto;

import com.example.taf.entities.StatCompte;
import lombok.*;


import java.util.Date;



@Getter
@Setter
public class CompteCourantDTO extends CompteBancaireDTO {


    private Long id;
    private Date dateCreation ;
    private double solde;
    private StatCompte statut;
    private  String devise;
    private double decouvert;
    private ClientDTO clientdto;

}
