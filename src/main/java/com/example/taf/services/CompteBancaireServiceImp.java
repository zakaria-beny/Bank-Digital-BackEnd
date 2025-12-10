package com.example.taf.services;

import com.example.taf.entities.*;
import com.example.taf.exceptions.ClientNotFoundExceptions;
import com.example.taf.exceptions.CompteBancaireNotFoundExceptions;
import com.example.taf.exceptions.SoldNoSufficientExceptions;
import com.example.taf.repository.ClientRepo;
import com.example.taf.repository.CompteBancaireRepo;
import com.example.taf.repository.OperationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CompteBancaireServiceImp implements CompteBancaireServiceRepo {

    private final CompteBancaireRepo compteBancaireRepo;
    private final ClientRepo clientRepo;
    private final OperationRepo operationRepo;

    @Override
    public Client saveClient(Client client) {
        return clientRepo.save(client);
    }

    @Override
    public CompteBancaire saveCourantCompteBancaire(double initialsold, double decouvert, Long ClientId) {
        Client client = clientRepo.findById(ClientId)
                .orElseThrow(() -> new ClientNotFoundExceptions("Client not found"));

        CompteCourant comptecourant = new CompteCourant();
        comptecourant.setDateCreation(new Date());
        comptecourant.setSolde(initialsold);
        comptecourant.setClient(client);
        comptecourant.setDecouvert(decouvert);

        return compteBancaireRepo.save(comptecourant);
    }

    @Override
    public CompteBancaire saveEpargneCompteBancaire(double initialsold, double tauxInteret, Long ClientId) {
        Client client = clientRepo.findById(ClientId)
                .orElseThrow(() -> new ClientNotFoundExceptions("Client not found"));

        CompteEpargne compteepargne = new CompteEpargne();
        compteepargne.setDateCreation(new Date());
        compteepargne.setSolde(initialsold);
        compteepargne.setClient(client);
        compteepargne.setTauxInteret(tauxInteret);

        return compteBancaireRepo.save(compteepargne);
    }

    @Override
    public List<Client> listClients() {
        return clientRepo.findAll();
    }

    @Override
    public CompteBancaire getCompteBancaireById(Long id) {
        return compteBancaireRepo.findById(id)
                .orElseThrow(() -> new CompteBancaireNotFoundExceptions("Compte bancaire not found"));
    }

    @Override
    public void debit(Long accountId, Double amount, String Description) {
        CompteBancaire compteBancaire = getCompteBancaireById(accountId);
        if (compteBancaire.getSolde() < amount)
            throw new SoldNoSufficientExceptions("Insufficient balance");

        Operation operation = new Operation();
        operation.setType(TypeOp.DEBIT);
        operation.setMontant(amount);
        operation.setDateOp(new Date());
        operation.setCompteBancaire(compteBancaire);
        operationRepo.save(operation);

        compteBancaire.setSolde(compteBancaire.getSolde() - amount);
        compteBancaireRepo.save(compteBancaire);
    }

    @Override
    public void credit(Long accountId, Double amount, String Description) {
        CompteBancaire compteBancaire = getCompteBancaireById(accountId);

        Operation operation = new Operation();
        operation.setType(TypeOp.CREDIT);
        operation.setMontant(amount);
        operation.setDateOp(new Date());
        operation.setCompteBancaire(compteBancaire);
        operationRepo.save(operation);

        compteBancaire.setSolde(compteBancaire.getSolde() + amount);
        compteBancaireRepo.save(compteBancaire);
    }

    @Override
    public void transfer(Long accountIdSource, Long accountIdDestination, Double amount) {
        debit(accountIdSource, amount, "Transfer to " + accountIdDestination);
        credit(accountIdDestination, amount, "Transfer from " + accountIdSource);
    }

    @Override
    public List<CompteBancaire> listCompteBancaire() {
        return compteBancaireRepo.findAll();
    }
}
