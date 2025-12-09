package com.example.taf.exceptions;

public class SoldNoSufficientExceptions extends RuntimeException {
    public SoldNoSufficientExceptions(String noSuffucientSolde) {
        super(noSuffucientSolde);
    }
}
