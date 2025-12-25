package com.example.taf.repository;

import com.example.taf.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepo extends JpaRepository<Operation, Long> {
    List<Operation> findByCompteBancaireId(Long accountId);
}
