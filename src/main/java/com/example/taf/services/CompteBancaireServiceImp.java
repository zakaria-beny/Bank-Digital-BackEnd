package com.example.taf.services;

import com.example.taf.entities.*;
import com.example.taf.exceptions.ClientNotFoundExceptions;
import com.example.taf.exceptions.CompteBancaireNotFoundExceptions;
import com.example.taf.exceptions.SoldNoSufficientExceptions;
import com.example.taf.repository.ClientRepo;
import com.example.taf.repository.CompteBancaireRepo;
import com.example.taf.repository.OperationRepo;
import lombok.AllArgsConstructor;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class CompteBancaireServiceImp  implements CompteBancaireServiceRepo{

    private CompteBancaireRepo compteBancaireRepo;
    private ClientRepo clientRepo;
    private OperationRepo operationRepo;

    @Override
    public Client saveClient(Client client) {
       Client saveclient= clientRepo.save(client);
        return saveclient;
    }

    @Override
    public CompteBancaire saveCourantCompteBancaire(double initialsold, double decouvert, Long ClientId) {


        Client client = clientRepo.findById(ClientId).orElseThrow();
        if (client==null)throw new ClientNotFoundExceptions("Client not found");

        CompteCourant comptecourant = new CompteCourant();

        comptecourant.setId(Long.valueOf(UUID.randomUUID().toString()));
        comptecourant.setDateCreation(new Date());
        comptecourant.setSolde(initialsold);
        comptecourant.setClient(client);
        comptecourant.setDecouvert(decouvert);

        CompteCourant savecopmtecourant = compteBancaireRepo.save(comptecourant);
        return savecopmtecourant;
    }

    @Override
    public CompteBancaire saveEpargneCompteBancaire(double initialsold, double tauxInteret, Long ClientId) {

        Client client = clientRepo.findById(ClientId).orElseThrow();
        if (client==null)throw new ClientNotFoundExceptions("Client not found");

        CompteEpargne compteepargne = new CompteEpargne();

        compteepargne.setId(Long.valueOf(UUID.randomUUID().toString()));
        compteepargne.setDateCreation(new Date());
        compteepargne.setSolde(initialsold);
        compteepargne.setClient(client);
        compteepargne.setTauxInteret(tauxInteret);

        CompteEpargne savecopmteepargne = compteBancaireRepo.save(compteepargne);
        return savecopmteepargne;
    }


    @Override
    public List<Client> listClients() {
        return clientRepo.findAll();
    }

    @Override
    public CompteBancaire getCompteBancaireById(String id) {
        CompteBancaire comptebancaire =compteBancaireRepo.findById(id).orElseThrow(
                ()->new CompteBancaireNotFoundExceptions("compte bancaire no trouver"));
        return comptebancaire;
    }

    @Override
    public void debit(String accountId,Double amount,String Description) {
        CompteBancaire compteBancaire=getCompteBancaireById(accountId);
        if (compteBancaire.getSolde()<amount)throw new SoldNoSufficientExceptions("no suffucient solde");
        Operation operation= new Operation();
        operation.setType(TypeOp.DEBIT);
        operation.setMontant(amount);
        operation.setDateOp(new Date());
        operation.setCompteBancaire(compteBancaire);
        operationRepo.save(operation);
        compteBancaire.setSolde(compteBancaire.getSolde() - amount);
        compteBancaireRepo.save(compteBancaire);
    }

    @Override
    public void credit(String accountId  ,Double amount,String Description) {
        CompteBancaire compteBancaire=getCompteBancaireById(accountId);

        Operation operation= new Operation();
        operation.setType(TypeOp.CREDIT);
        operation.setMontant(amount);
        operation.setDateOp(new Date());
        operation.setCompteBancaire(compteBancaire);
        operationRepo.save(operation);
        compteBancaire.setSolde(compteBancaire.getSolde() + amount);
        compteBancaireRepo.save(compteBancaire);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, Double amount) {
        debit(accountIdSource,amount,"transfer to "+accountIdDestination);
        credit(accountIdDestination,amount,"transfer from "+accountIdSource);

    }
}
