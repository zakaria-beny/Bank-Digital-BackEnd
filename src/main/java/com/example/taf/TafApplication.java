package com.example.taf;

import com.example.taf.dto.ClientDTO;
import com.example.taf.dto.CompteBancaireDTO;
import com.example.taf.dto.CompteCourantDTO;
import com.example.taf.dto.CompteEpargneDTO;
import com.example.taf.entities.Client;
import com.example.taf.entities.CompteBancaire;
import com.example.taf.services.CompteBancaireServiceRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class TafApplication {

    public static void main(String[] args) {
        SpringApplication.run(TafApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CompteBancaireServiceRepo compteBancaireServicerepo) {
        return args -> {
            Stream.of("othman", "zakaria", "su").forEach(name -> {
                ClientDTO client = new ClientDTO();
                client.setNom(name);
                client.setEmail(name + "@gmail.com");
                compteBancaireServicerepo.saveClient(client);
            });

            List<ClientDTO> clients = compteBancaireServicerepo.listClients();
            for (ClientDTO client : clients) {
                CompteCourantDTO courant = compteBancaireServicerepo.saveCourantCompteBancaire(Math.random() * 9000, 800, client.getId());
                CompteEpargneDTO epargne = compteBancaireServicerepo.saveEpargneCompteBancaire(Math.random() * 120000, 5.5, client.getId());

                List<CompteBancaireDTO> comptebancaires = compteBancaireServicerepo.listCompteBancaire();
                for (CompteBancaireDTO compteBancaireDTO : comptebancaires) {

                        compteBancaireServicerepo.credit(courant.getId(), 1000.0, "Initial credit");
                        compteBancaireServicerepo.debit(epargne.getId(), 400.0, "Initial debit");



                }
            }};}}

