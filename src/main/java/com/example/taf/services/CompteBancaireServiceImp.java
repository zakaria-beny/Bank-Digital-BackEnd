package com.example.taf.services;

import com.example.taf.dto.*;
import com.example.taf.entities.*;
import com.example.taf.exceptions.ClientNotFoundExceptions;
import com.example.taf.exceptions.CompteBancaireNotFoundExceptions;
import com.example.taf.exceptions.SoldNoSufficientExceptions;
import com.example.taf.mappers.CompteBancaireMapperImp;
import com.example.taf.repository.ClientRepo;
import com.example.taf.repository.CompteBancaireRepo;
import com.example.taf.repository.OperationRepo;
import jakarta.persistence.EntityManager;
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
    private EntityManager entityManager;

    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        Client savedClient= dtoMapper.fromClientDTO(clientDTO);
        Client client=clientRepo.save(savedClient);
        return dtoMapper.fromClient(client);
    }

    @Override
    public CompteCourantDTO saveCourantCompteBancaire(String numAcc,double initialsold, double decouvert, String ClientId,String devise) {
        Client client = clientRepo.findById(ClientId)
                .orElseThrow(() -> new ClientNotFoundExceptions("Client not found"));
        CompteCourant comptecourant = new CompteCourant();
        comptecourant.setNumeroCompte(numAcc);

        comptecourant.setDateCreation(new Date());
        comptecourant.setSolde(initialsold);
        comptecourant.setClient(client);
        comptecourant.setDecouvert(decouvert);
        comptecourant.setDevise((devise != null && !devise.isEmpty()) ? devise : "MAD");
        compteBancaireRepo.save(comptecourant);
        return dtoMapper.fromCompteCourant(comptecourant);
    }

    @Override
    public CompteEpargneDTO saveEpargneCompteBancaire(String numAcc,double initialsold, double tauxInteret, String ClientId,String devise) {
        Client client = clientRepo.findById(ClientId)
                .orElseThrow(() -> new ClientNotFoundExceptions("Client not found"));

        CompteEpargne compteepargne = new CompteEpargne();
        compteepargne.setNumeroCompte(numAcc);
        compteepargne.setDateCreation(new Date());
        compteepargne.setSolde(initialsold);
        compteepargne.setClient(client);
        compteepargne.setTauxInteret(tauxInteret);
        compteepargne.setDevise((devise != null && !devise.isEmpty()) ? devise : "MAD");
        compteBancaireRepo.save(compteepargne);
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
    public CompteBancaireDTO getCompteBancaireById(String id) {
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
    public void debit(String accountId, Double amount, String Description) {
        CompteBancaire compteBancaire= compteBancaireRepo.findById(accountId)
                .orElseThrow(() -> new CompteBancaireNotFoundExceptions("Compte bancaire not found"));
        if (compteBancaire.getStatut() == StatCompte.SUSPENDED) {
            throw new IllegalStateException("Cannot perform operations on a suspended account");
        }
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
    public void credit(String accountId, Double amount, String Description) {
        CompteBancaire compteBancaire= compteBancaireRepo.findById(accountId)
                .orElseThrow(() -> new CompteBancaireNotFoundExceptions("Compte bancaire not found"));
        if (compteBancaire.getStatut() == StatCompte.SUSPENDED) {
            throw new IllegalStateException("Cannot perform operations on a suspended account");
        }
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
    public void transfer(String accountIdSource, String accountIdDestination, Double amount) {
        CompteBancaire source = compteBancaireRepo.findById(accountIdSource)
                .orElseThrow(() -> new CompteBancaireNotFoundExceptions("Compte bancaire not found"));
        if (source.getStatut() == StatCompte.SUSPENDED) {
            throw new IllegalStateException("Cannot perform operations on a suspended account");
        }
        CompteBancaire destination = compteBancaireRepo.findById(accountIdDestination)
                .orElseThrow(() -> new CompteBancaireNotFoundExceptions("Compte bancaire not found"));
        if (destination.getStatut() == StatCompte.SUSPENDED) {
            throw new IllegalStateException("Cannot transfer to a suspended account");
        }
        debit(accountIdSource, amount, "Transfer to " + accountIdDestination);
        credit(accountIdDestination, amount, "Transfer from " + accountIdSource);
    }

    @Override
    public List<CompteBancaireDTO> findByClientId(String clientId) {
        List<CompteBancaire> comptes = compteBancaireRepo.findByClientId(clientId);

        List<CompteBancaireDTO> compteBancaireDTOs = comptes.stream().map(compte -> {
            if (compte instanceof CompteCourant) {
                CompteCourant cmpcourant = (CompteCourant) compte;
                return dtoMapper.fromCompteCourant(cmpcourant);
            } else {
                CompteEpargne cmpEpargne = (CompteEpargne) compte;
                return dtoMapper.fromCompteEpargne(cmpEpargne);
            }
        }).collect(Collectors.toList());

        return compteBancaireDTOs;
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
    public ClientDTO getClient(String clientId) {
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
    public void deleteClient(String clientId) {
        clientRepo.deleteById(clientId);
    }
    @Override
    public List<OperationsDTO> CompteHistorique(String accountId) {
        List<Operation> operationsHistoriques=operationRepo.findByCompteBancaireId(accountId);
        return  operationsHistoriques.stream().map(operation ->
                        dtoMapper.fromOperation(operation))
                .collect(Collectors.toList());

    }

    @Override
    public List<ClientDTO> searchClient(String motcle) {
        List<Client> clients=clientRepo.findByNomContainingIgnoreCase(motcle);
        List<ClientDTO> clientDTOS= clients.stream().map(client->dtoMapper.fromClient(client)).collect(Collectors.toList());
        return clientDTOS;
    }



    @Override
    public CompteBancaireDTO createCompte(CompteBancaireDTO compteBancaireDTO) {
        if (compteBancaireDTO.getClientId() == null) {
            throw new IllegalArgumentException("clientId must not be null");
        }
        Client client = clientRepo.findById(compteBancaireDTO.getClientId())
                .orElseThrow(() -> new ClientNotFoundExceptions("Client not found with id: " + compteBancaireDTO.getClientId()));

        CompteBancaire compte;
        String type = compteBancaireDTO.getType().toUpperCase();

        if ("COURANT".equals(type)) {
            CompteCourant compteCourant = new CompteCourant();
            compteCourant.setDecouvert(compteBancaireDTO.getDecouvert() != null ? compteBancaireDTO.getDecouvert() : 0.0);
            compte = compteCourant;
        } else if ("EPARGNE".equals(type)) {
            CompteEpargne compteEpargne = new CompteEpargne();
            compteEpargne.setTauxInteret(compteBancaireDTO.getTauxInteret() != null ? compteBancaireDTO.getTauxInteret() : 0.0);
            compte = compteEpargne;
        } else {
            throw new IllegalArgumentException("Type de compte inconnu : " + compteBancaireDTO.getType());
        }
        if (compteBancaireDTO.getNumeroCompte() == null || compteBancaireDTO.getNumeroCompte().isEmpty()) {
            compte.setNumeroCompte(java.util.UUID.randomUUID().toString());
        } else {
            compte.setNumeroCompte(compteBancaireDTO.getNumeroCompte());
        }

        compte.setSolde(compteBancaireDTO.getSolde());
        compte.setDateCreation(new Date());
        compte.setClient(client);
        compte.setStatut(StatCompte.CREATED);
        compte.setDevise(compteBancaireDTO.getDevise());


        if (compteBancaireDTO.getDevise() != null && !compteBancaireDTO.getDevise().isEmpty()) {
            compte.setDevise(compteBancaireDTO.getDevise());
        } else {
            compte.setDevise("MAD");
        }
        CompteBancaire savedCompte = compteBancaireRepo.save(compte);

        if (savedCompte instanceof CompteCourant) {
            return dtoMapper.fromCompteCourant((CompteCourant) savedCompte);
        } else {
            return dtoMapper.fromCompteEpargne((CompteEpargne) savedCompte);
        }
    }
    @Override
    public CompteBancaireDTO updateCompte(String compteId, CompteBancaireDTO compteBancaireDTO) {
        CompteBancaire compte = compteBancaireRepo.findById(compteId)
                .orElseThrow(() -> new CompteBancaireNotFoundExceptions("Compte not found"));

        String requestedType = compteBancaireDTO.getType() != null ? compteBancaireDTO.getType().toUpperCase() : null;
        boolean wantCourant = requestedType != null && requestedType.contains("COURANT");
        boolean wantEpargne = requestedType != null && requestedType.contains("EPARGNE");
        boolean isCourant = compte instanceof CompteCourant;
        boolean isEpargne = compte instanceof CompteEpargne;

        if (requestedType != null && ((wantCourant && isEpargne) || (wantEpargne && isCourant))) {
            String discriminatorValue = wantCourant ? "CompteCourant" : "CompteEpargne";
            double decouvert = wantCourant
                    ? (compteBancaireDTO.getDecouvert() != null ? compteBancaireDTO.getDecouvert() : 0.0)
                    : 0.0;
            double tauxInteret = wantEpargne
                    ? (compteBancaireDTO.getTauxInteret() != null ? compteBancaireDTO.getTauxInteret() : 0.0)
                    : 0.0;

            compteBancaireRepo.updateAccountDiscriminator(compteId, discriminatorValue, decouvert, tauxInteret);

            entityManager.flush();
            entityManager.clear();

            compte = compteBancaireRepo.findById(compteId)
                    .orElseThrow(() -> new CompteBancaireNotFoundExceptions("Compte not found"));
        }

        if (compteBancaireDTO.getNumeroCompte() != null && !compteBancaireDTO.getNumeroCompte().isEmpty()) {
            compte.setNumeroCompte(compteBancaireDTO.getNumeroCompte());
        }

        if (compteBancaireDTO.getDateCreation() != null) {
            compte.setDateCreation(compteBancaireDTO.getDateCreation());
        }

        compte.setSolde(compteBancaireDTO.getSolde());


        if (compteBancaireDTO.getStatut() != null) {
            compte.setStatut(compteBancaireDTO.getStatut());
        }


        if (compteBancaireDTO.getDevise() != null && !compteBancaireDTO.getDevise().isEmpty()) {
            compte.setDevise(compteBancaireDTO.getDevise());
        }

        if (compte instanceof CompteCourant) {
            CompteCourant cc = (CompteCourant) compte;
            if (compteBancaireDTO.getDecouvert() != null) {
                cc.setDecouvert(compteBancaireDTO.getDecouvert());
            }
        }

        if (compte instanceof CompteEpargne) {
            CompteEpargne ce = (CompteEpargne) compte;
            if (compteBancaireDTO.getTauxInteret() != null) {
                ce.setTauxInteret(compteBancaireDTO.getTauxInteret());
            }
        }

        CompteBancaire updatedCompte = compteBancaireRepo.save(compte);

        if (updatedCompte instanceof CompteCourant) {
            return dtoMapper.fromCompteCourant((CompteCourant) updatedCompte);
        } else {
            return dtoMapper.fromCompteEpargne((CompteEpargne) updatedCompte);
        }
    }

    @Override
    public void deleteCompte(String accountId) {
        CompteBancaire compte = compteBancaireRepo.findById(accountId).orElse(null);

        if (compte != null) {
            if (compte.getClient() != null) {
                compte.getClient().getComptes().remove(compte);
                compte.setClient(null);
            }

            compteBancaireRepo.delete(compte);
        }
    }
    @Override
    public List<CompteBancaireDTO> searchCompteBancaire(String motcle) {
        List<CompteBancaire> comptes = compteBancaireRepo.findByClient_NomContainingIgnoreCase(motcle);
        return comptes.stream().map(compte -> {
            if (compte instanceof CompteCourant) {
                return dtoMapper.fromCompteCourant((CompteCourant) compte);
            } else {
                return dtoMapper.fromCompteEpargne((CompteEpargne) compte);
            }
        }).collect(Collectors.toList());
    }

}

