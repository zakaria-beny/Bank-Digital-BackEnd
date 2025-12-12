package com.example.taf.services;

import com.example.taf.dto.ClientDTO;
import com.example.taf.dto.CompteBancaireDTO;
import com.example.taf.dto.CompteCourantDTO;
import com.example.taf.dto.CompteEpargneDTO;
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
    public CompteCourantDTO saveCourantCompteBancaire(double initialsold, double decouvert, Long ClientId) {
        Client client = clientRepo.findById(ClientId)
                .orElseThrow(() -> new ClientNotFoundExceptions("Client not found"));

        CompteCourant comptecourant = new CompteCourant();
        comptecourant.setDateCreation(new Date());
        comptecourant.setSolde(initialsold);
        comptecourant.setClient(client);
        comptecourant.setDecouvert(decouvert);

        return dtoMapper.fromCompteCourant(comptecourant);
    }

    @Override
    public CompteEpargneDTO saveEpargneCompteBancaire(double initialsold, double tauxInteret, Long ClientId) {
        Client client = clientRepo.findById(ClientId)
                .orElseThrow(() -> new ClientNotFoundExceptions("Client not found"));

        CompteEpargne compteepargne = new CompteEpargne();
        compteepargne.setDateCreation(new Date());
        compteepargne.setSolde(initialsold);
        compteepargne.setClient(client);
        compteepargne.setTauxInteret(tauxInteret);

        return dtoMapper.fromCompteEpargne(compteepargne);
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
    public CompteBancaireDTO getCompteBancaireById(Long id) {
        CompteBancaire comptebancaire= compteBancaireRepo.findById(id)
                .orElseThrow(() -> new CompteBancaireNotFoundExceptions("Compte bancaire not found"));
    if (comptebancaire instanceof CompteEpargne){
        CompteEpargne compteEpargne=(CompteEpargne)  comptebancaire;
        return dtoMapper.fromCompteEpargne(compteEpargne);
    }else {
        CompteCourant compteCourant= (CompteCourant) comptebancaire;
        return dtoMapper.fromCompteCourant(compteCourant) ;  }

    }

    @Override
    public void debit(Long accountId, Double amount, String Description) {
        CompteBancaire compteBancaire= compteBancaireRepo.findById(accountId)
                .orElseThrow(() -> new CompteBancaireNotFoundExceptions("Compte bancaire not found"));
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
        CompteBancaire compteBancaire= compteBancaireRepo.findById(accountId)
                .orElseThrow(() -> new CompteBancaireNotFoundExceptions("Compte bancaire not found"));
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
    public List<CompteBancaireDTO> listCompteBancaire() {
        List<CompteBancaire> compteBancaires = compteBancaireRepo.findAll();
        List<CompteBancaireDTO> compteBancaireDTOs = compteBancaires.stream().map(compteBancaire -> {

                    if (compteBancaire instanceof CompteCourant) {
                        CompteCourant cmpcourant = (CompteCourant) compteBancaire;
                        return dtoMapper.fromCompteCourant(cmpcourant);
                    } else {
                        CompteEpargne cmpEpargne = (CompteEpargne) compteBancaire;
                        return dtoMapper.fromCompteEpargne(cmpEpargne);
                    }

                }
        ).collect(Collectors.toList());
        return compteBancaireDTOs;
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

