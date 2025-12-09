package com.example.taf.services;

import com.example.taf.entities.Client;
import com.example.taf.entities.CompteBancaire;

import java.util.List;

public interface CompteBancaireServiceRepo {
    Client saveClient(Client client) ;

    CompteBancaire saveCourantCompteBancaire(double initialsold , double decouvert,Long ClientId);
    CompteBancaire saveEpargneCompteBancaire(double initialsold ,double tauxInteret,Long ClientId);

    List<Client> listClients();
    CompteBancaire getCompteBancaireById(String id);
    void debit(String accountId,Double amount,String Description);
    void credit(String accountId,Double amount,String Description);
    void transfer(String accountIdSource,String accountIdDestination,Double amount);

}
