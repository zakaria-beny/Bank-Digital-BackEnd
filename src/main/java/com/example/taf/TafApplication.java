package com.example.taf;

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
                Client client = new Client();
                client.setNom(name);
                client.setEmail(name + "@gmail.com");
                compteBancaireServicerepo.saveClient(client);
            });

            List<Client> clients = compteBancaireServicerepo.listClients();
            for (Client client : clients) {
                CompteBancaire courant = compteBancaireServicerepo.saveCourantCompteBancaire(Math.random() * 9000, 800, client.getId());
                CompteBancaire epargne = compteBancaireServicerepo.saveEpargneCompteBancaire(Math.random() * 120000, 5.5, client.getId());


                compteBancaireServicerepo.credit(courant.getId(), 1000.0, "Initial credit");
                compteBancaireServicerepo.debit(epargne.getId(), 400.0, "Initial debit");
            }
        };
    }
}
