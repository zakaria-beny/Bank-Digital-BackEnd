package com.example.taf.services;

import com.example.taf.dto.*;
import com.example.taf.entities.CompteBancaire;

import java.util.List;

public interface CompteBancaireServiceRepo {



    CompteCourantDTO saveCourantCompteBancaire(String numAcc, double initialsold, double decouvert, Long ClientId, String devise);
    CompteEpargneDTO saveEpargneCompteBancaire(String numAcc, double initialsold, double tauxInteret, Long ClientId, String devise);
    CompteBancaireDTO createCompte(CompteBancaireDTO compteBancaireDTO);
    CompteBancaireDTO getCompteBancaireById(Long id);
    void debit(Long accountId, Double amount, String description);
    void credit(Long accountId, Double amount, String description);
    void transfer(Long accountIdSource, Long accountIdDestination, Double amount);
    List<CompteBancaireDTO> findByClientId(Long clientId);
    List<CompteBancaireDTO> listCompteBancaire();
    CompteBancaireDTO updateCompte(Long compteId, CompteBancaireDTO compteBancaireDTO);
    void deleteCompte(Long compteId);
    ClientDTO saveClient(ClientDTO clientDTO);

    ClientDTO getClient(Long clientId);
    List<ClientDTO> listClients();
    ClientDTO UpdateClient(ClientDTO clientDTO);

    void deleteClient(Long id);

    List<OperationsDTO> CompteHistorique(Long accountId);

    List<ClientDTO> searchClient(String motcle);
    List<CompteBancaireDTO> searchCompteBancaire(String nom);
}
