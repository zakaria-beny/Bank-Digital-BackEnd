package com.example.taf.repository;

import com.example.taf.entities.CompteBancaire;
import com.example.taf.entities.CompteCourant;
import com.example.taf.entities.CompteEpargne;
import com.example.taf.entities.StatCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CompteBancaireRepo extends JpaRepository<CompteBancaire, String>
{@Query("select c from CompteBancaire c where lower(c.client.nom) like lower(concat('%', :kw, '%'))")
List<CompteBancaire> findByClient_NomContainingIgnoreCase(@Param("kw") String keyword);
    List<CompteBancaire> findByClientId(String clientId);

    @Query("select sum(c.solde) from CompteBancaire c")
    Double sumSolde();

    @Query("select count(c) from CompteBancaire c where type(c) = CompteCourant")
    Long countCourant();

    @Query("select count(c) from CompteBancaire c where type(c) = CompteEpargne")
    Long countEpargne();

    @Query("select count(c) from CompteBancaire c where c.statut = :statut")
    Long countByStatut(@Param("statut") StatCompte statut);

    @Query("select coalesce(c.devise,'MAD'), sum(c.solde) from CompteBancaire c group by coalesce(c.devise,'MAD')")
    List<Object[]> sumSoldeByDevise();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE compte_bancaire " +
                    "SET `type` = :dtype, decouvert = :decouvert, taux_interet = :tauxInteret " +
            "WHERE id = :id",
            nativeQuery = true
    )
    int updateAccountDiscriminator(
            @Param("id") String id,
            @Param("dtype") String discriminatorValue,
            @Param("decouvert") double decouvert,
            @Param("tauxInteret") double tauxInteret
    );

}
