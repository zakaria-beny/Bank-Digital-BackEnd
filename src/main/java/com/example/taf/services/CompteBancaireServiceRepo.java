package com.example.taf.services;

import com.example.taf.entities.Client;
import com.example.taf.entities.CompteBancaire;

import java.util.List;

public interface CompteBancaireServiceRepo {
    Client saveClient(Client client) ;

    CompteBancaire saveCompteBancaire(double initialsold ,String type,Long ClientId);
    List<Client> listClients();
    CompteBancaire getCompteBancaireById(Long id);
    void debit(Long accountId,String type);
    void credit(Long accountId,String type);
    void transfer(Long accountIdSource,String accountIdDestination,Double amount);

}
