package com.example.taf.repository;

import com.example.taf.entities.CompteBancaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompteBancaireRepo extends JpaRepository<CompteBancaire, Long>
{@Query("select c from CompteBancaire c where lower(c.client.nom) like lower(concat('%', :kw, '%'))")
List<CompteBancaire> findByClient_NomContainingIgnoreCase(@Param("kw") String keyword);
    List<CompteBancaire> findByClientId(Long clientId);

}
