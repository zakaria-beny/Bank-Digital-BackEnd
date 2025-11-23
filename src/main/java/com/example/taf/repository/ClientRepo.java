package com.example.taf.repository;

import com.example.taf.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepo extends JpaRepository<Client, Long> {
    List<Client> findByNom(String nom);
    List<Client> findByEmail(String email);
    List<Client> findByNomContains(String keyword);
}
