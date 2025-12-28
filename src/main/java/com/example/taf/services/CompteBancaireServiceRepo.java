package com.example.taf.services;

import com.example.taf.dto.*;
import com.example.taf.entities.CompteBancaire;

import java.util.List;

public interface CompteBancaireServiceRepo {



    CompteCourantDTO saveCourantCompteBancaire(String numAcc, double initialsold, double decouvert, String ClientId, String devise);
    CompteEpargneDTO saveEpargneCompteBancaire(String numAcc, double initialsold, double tauxInteret, String ClientId, String devise);
    CompteBancaireDTO createCompte(CompteBancaireDTO compteBancaireDTO);
    CompteBancaireDTO getCompteBancaireById(String id);
    void debit(String accountId, Double amount, String description);
    void credit(String accountId, Double amount, String description);
    void transfer(String accountIdSource, String accountIdDestination, Double amount);
    List<CompteBancaireDTO> findByClientId(String clientId);
    List<CompteBancaireDTO> listCompteBancaire();
    CompteBancaireDTO updateCompte(String compteId, CompteBancaireDTO compteBancaireDTO);
    void deleteCompte(String compteId);
    ClientDTO saveClient(ClientDTO clientDTO);

    ClientDTO getClient(String clientId);
    List<ClientDTO> listClients();
    ClientDTO UpdateClient(ClientDTO clientDTO);

    void deleteClient(String id);

    List<OperationsDTO> CompteHistorique(String accountId);

    List<ClientDTO> searchClient(String motcle);
    List<CompteBancaireDTO> searchCompteBancaire(String nom);
}
