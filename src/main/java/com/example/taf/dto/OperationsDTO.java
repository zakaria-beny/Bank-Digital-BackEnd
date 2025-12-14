package com.example.taf.dto;

import com.example.taf.entities.CompteBancaire;
import com.example.taf.entities.TypeOp;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Data

public class OperationsDTO {
        private Long id;
        private Date dateOp;
        private double montant;
        private TypeOp type;



    }


