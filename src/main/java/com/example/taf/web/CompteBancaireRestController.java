package com.example.taf.web;

import com.example.taf.dto.CompteBancaireDTO;
import com.example.taf.dto.OperationsDTO;
import com.example.taf.services.CompteBancaireServiceRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CompteBancaireRestController {
    private CompteBancaireServiceRepo compteBancaireServiceRepo;

    @GetMapping("/accounts/{accountId}")
    public CompteBancaireDTO getCompteBancaire(@PathVariable Long accountId) {

    return compteBancaireServiceRepo.getCompteBancaireById(accountId);
    }

    @GetMapping("/accounts")
    public List<CompteBancaireDTO> listCompteBancaires() {
    return compteBancaireServiceRepo.listCompteBancaire();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<OperationsDTO> getHistorique(@PathVariable Long accountId) {
        return compteBancaireServiceRepo.CompteHistorique(accountId);
    }
}
