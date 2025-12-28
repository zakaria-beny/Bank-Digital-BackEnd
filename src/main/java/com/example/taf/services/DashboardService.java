package com.example.taf.services;

import com.example.taf.dto.DashboardStat;
import com.example.taf.entities.StatCompte;
import com.example.taf.repository.ClientRepo;
import com.example.taf.repository.CompteBancaireRepo;
import com.example.taf.repository.OperationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

        Double totalBalance = compteBancaireRepo.sumSolde();
        if (totalBalance == null) totalBalance = 0.0;

        Long compteCourantCount = compteBancaireRepo.countCourant();
        if (compteCourantCount == null) compteCourantCount = 0L;

        Long compteEpargneCount = compteBancaireRepo.countEpargne();
        if (compteEpargneCount == null) compteEpargneCount = 0L;

        Long activeAccounts = compteBancaireRepo.countByStatut(StatCompte.ACTIVATED);
        if (activeAccounts == null) activeAccounts = 0L;

        Long suspendedAccounts = compteBancaireRepo.countByStatut(StatCompte.SUSPENDED);
        if (suspendedAccounts == null) suspendedAccounts = 0L;

        Map<String, Double> balanceByDevise = new HashMap<>();
        for (Object[] row : compteBancaireRepo.sumSoldeByDevise()) {
            if (row == null || row.length < 2) continue;
            String devise = row[0] != null ? String.valueOf(row[0]) : "MAD";
            Double sum = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
            balanceByDevise.put(devise, sum);
        }

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