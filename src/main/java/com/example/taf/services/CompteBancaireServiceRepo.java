package com.example.taf.services;

import com.example.taf.dto.*;
import com.example.taf.entities.CompteBancaire;

import java.util.List;

public interface CompteBancaireServiceRepo {


    CompteCourantDTO saveCourantCompteBancaire(double initialSold, double decouvert, Long clientId);
    CompteEpargneDTO saveEpargneCompteBancaire(double initialSold, double tauxInteret, Long clientId);


    CompteBancaireDTO getCompteBancaireById(Long id);
    void debit(Long accountId, Double amount, String description);
    void credit(Long accountId, Double amount, String description);
    void transfer(Long accountIdSource, Long accountIdDestination, Double amount);

    List<CompteBancaireDTO> listCompteBancaire();

    ClientDTO saveClient(ClientDTO clientDTO);

    ClientDTO getClient(Long clientId);
    List<ClientDTO> listClients();
    ClientDTO UpdateClient(ClientDTO clientDTO);

    void deleteClient(Long id);

    List<OperationsDTO> CompteHistorique(Long accountId);
}
