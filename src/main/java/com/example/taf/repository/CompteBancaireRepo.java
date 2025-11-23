package com.example.taf.repository;

import com.example.taf.entities.CompteBancaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompteBancaireRepo extends JpaRepository<CompteBancaire, Long>
{
    List<CompteBancaire> findByStatut(int statut);
    List<CompteBancaire> findByDevise(String devise);
}
