package com.example.taf.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardStat {
    private Long totalClients;
    private Long totalComptes;
    private Double totalBalance;
    private Long totalOperations;
    private Long compteCourantCount;
    private Long compteEpargneCount;
    private Map<String, Long> operationsByMonth;
    private Map<String, Double> balanceByDevise;
    private Long activeAccounts;
    private Long suspendedAccounts;
}