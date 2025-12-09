package com.example.taf.exceptions;

public class CompteBancaireNotFoundExceptions extends RuntimeException {
    public CompteBancaireNotFoundExceptions(String compteBancaireNoTrouver) {
        super(compteBancaireNoTrouver);
    }
}
