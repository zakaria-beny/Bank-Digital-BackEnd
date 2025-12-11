package com.example.taf.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @OneToMany(mappedBy = "client",fetch=FetchType.EAGER, cascade = CascadeType.ALL,
            orphanRemoval = true)

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<CompteBancaire> comptes = new ArrayList<>();


}
