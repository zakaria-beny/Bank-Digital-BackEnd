package com.example.taf.services;

import com.example.taf.dto.ClientDTO;
import com.example.taf.entities.*;
import com.example.taf.exceptions.ClientNotFoundExceptions;
import com.example.taf.exceptions.CompteBancaireNotFoundExceptions;
import com.example.taf.exceptions.SoldNoSufficientExceptions;
import com.example.taf.mappers.CompteBancaireMapperImp;
import com.example.taf.repository.ClientRepo;
import com.example.taf.repository.CompteBancaireRepo;
import com.example.taf.repository.OperationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CompteBancaireServiceImp implements CompteBancaireServiceRepo {

    private  CompteBancaireRepo compteBancaireRepo;
    private  ClientRepo clientRepo;
    private  OperationRepo operationRepo;
    private CompteBancaireMapperImp dtoMapper;

    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        Client savedClient= dtoMapper.fromClientDTO(clientDTO);
        Client client=clientRepo.save(savedClient);
        return dtoMapper.fromClient(client);
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
    public List<ClientDTO> listClients() {
         List<Client> clients =clientRepo.findAll();
         List<ClientDTO>  clientDTOS= clients.stream()
                 .map(client -> dtoMapper.fromClient(client))
                 .collect(Collectors.toList());
return clientDTOS;
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
    @Override
    public ClientDTO getClient(Long clientId) {
        Client client= clientRepo.findById(clientId).orElseThrow(
                () -> new ClientNotFoundExceptions("Client not found"));
        return dtoMapper.fromClient(client);

    }

    @Override
    public ClientDTO UpdateClient(ClientDTO clientDTO) {
        Client savedClient= dtoMapper.fromClientDTO(clientDTO);
        Client client=clientRepo.save(savedClient);
        return dtoMapper.fromClient(client);
    }
    @Override
    public void deleteClient(Long clientId) {
clientRepo.deleteById(clientId);
    }

}

