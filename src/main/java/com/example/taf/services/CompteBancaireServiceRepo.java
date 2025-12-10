package com.example.taf.services;

import com.example.taf.entities.Client;
import com.example.taf.entities.CompteBancaire;

import java.util.List;

public interface CompteBancaireServiceRepo {
    Client saveClient(Client client);

    CompteBancaire saveCourantCompteBancaire(double initialSold, double decouvert, Long clientId);
    CompteBancaire saveEpargneCompteBancaire(double initialSold, double tauxInteret, Long clientId);

    List<Client> listClients();
    CompteBancaire getCompteBancaireById(Long id);
    void debit(Long accountId, Double amount, String description);
    void credit(Long accountId, Double amount, String description);
    void transfer(Long accountIdSource, Long accountIdDestination, Double amount);

    List<CompteBancaire> listCompteBancaire();
}
