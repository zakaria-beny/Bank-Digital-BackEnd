package com.example.taf.services;

import com.example.taf.dto.DashboardStat;
import com.example.taf.entities.CompteBancaire;
import com.example.taf.entities.CompteCourant;
import com.example.taf.entities.CompteEpargne;
import com.example.taf.entities.StatCompte;
import com.example.taf.repository.ClientRepo;
import com.example.taf.repository.CompteBancaireRepo;
import com.example.taf.repository.OperationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DashboardService {

    private ClientRepo clientRepo;
    private CompteBancaireRepo compteBancaireRepo;
    private OperationRepo operationRepo;

    public DashboardStat getDashboardStats() {
        Long totalClients = clientRepo.count();
        Long totalComptes = compteBancaireRepo.count();
        Long totalOperations = operationRepo.count();

        List<CompteBancaire> comptes = compteBancaireRepo.findAll();

        Double totalBalance = comptes.stream()
                .mapToDouble(CompteBancaire::getSolde)
                .sum();

        Long compteCourantCount = comptes.stream()
                .filter(c -> c instanceof CompteCourant)
                .count();

        Long compteEpargneCount = comptes.stream()
                .filter(c -> c instanceof CompteEpargne)
                .count();

        Long activeAccounts = comptes.stream()
                .filter(c -> c.getStatut() == StatCompte.ACTIVATED)
                .count();

        Long suspendedAccounts = comptes.stream()
                .filter(c -> c.getStatut() == StatCompte.SUSPENDED)
                .count();

        Map<String, Double> balanceByDevise = new HashMap<>();
        comptes.forEach(compte -> {
            String devise = compte.getDevise() != null ? compte.getDevise() : "MAD";
            balanceByDevise.merge(devise, compte.getSolde(), Double::sum);
        });

        Map<String, Long> operationsByMonth = new HashMap<>();
        operationsByMonth.put("Jan", 12L);
        operationsByMonth.put("Feb", 19L);
        operationsByMonth.put("Mar", 25L);
        operationsByMonth.put("Apr", 18L);
        operationsByMonth.put("May", 22L);
        operationsByMonth.put("Jun", 30L);

        return DashboardStat.builder()
                .totalClients(totalClients)
                .totalComptes(totalComptes)
                .totalBalance(totalBalance)
                .totalOperations(totalOperations)
                .compteCourantCount(compteCourantCount)
                .compteEpargneCount(compteEpargneCount)
                .operationsByMonth(operationsByMonth)
                .balanceByDevise(balanceByDevise)
                .activeAccounts(activeAccounts)
                .suspendedAccounts(suspendedAccounts)
                .build();
    }
}