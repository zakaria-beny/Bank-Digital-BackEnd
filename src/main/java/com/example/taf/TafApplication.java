package com.example.taf;

import com.example.taf.entities.Client;
import com.example.taf.repository.ClientRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TafApplication {

    public static void main(String[] args) {
        SpringApplication.run(TafApplication.class, args);
    }
    @Bean
    CommandLineRunner start(ClientRepo clientRepository) {
        return args -> {
            Client c = new Client("Amina" ,"amina@mail.com");
            clientRepository.save(c);
            System.out.println("Client enregistré : " + c.getNom());
        };
    }
}
