package com.example.ParcialAlario49925.dto;

/*
 Devuelve el resultado del análisis de ADN al cliente,
 dice si el ADN  es un mutante o no, mediante un
 valor booleano
*/


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ADNResponse {
    private boolean isMutant;
    private String message;

    public ADNResponse(boolean isMutant) {
        this.isMutant = isMutant;
        this.message = isMutant ? "Es mutante" : "No es mutante";
    }
}