package com.example.taf.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter  @NoArgsConstructor @AllArgsConstructor @Builder

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;

    @OneToMany(mappedBy = "client",fetch=FetchType.EAGER)
    private List<CompteBancaire> comptes = new ArrayList<>();


}
