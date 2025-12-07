package com.example.taf.services;

import com.example.taf.entities.Client;
import com.example.taf.entities.CompteBancaire;
import com.example.taf.repository.ClientRepo;
import com.example.taf.repository.CompteBancaireRepo;
import com.example.taf.repository.OperationRepo;
import lombok.AllArgsConstructor;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
@AllArgsConstructor
public class CompteBancaireServiceImp  implements CompteBancaireServiceRepo{

    private CompteBancaireRepo compteBancaireRepo;
    private ClientRepo clientRepo;
    private OperationRepo operationRepo;

    @Override
    public Client saveClient(Client client) {
        return null;
    }

    @Override
    public CompteBancaire saveCompteBancaire(double initialsold, String type, Long ClientId) {
        return null;
    }

    @Override
    public List<Client> listClients() {
        return List.of();
    }

    @Override
    public CompteBancaire getCompteBancaireById(Long id) {
        return null;
    }

    @Override
    public void debit(Long accountId, String type) {

    }

    @Override
    public void credit(Long accountId, String type) {

    }

    @Override
    public void transfer(Long accountIdSource, String accountIdDestination, Double amount) {

    }
}
