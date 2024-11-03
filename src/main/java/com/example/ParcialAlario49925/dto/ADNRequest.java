package com.example.ParcialAlario49925.dto;

/*
Recibimos el ADN en formato JSON
*/


import lombok.Getter;
import lombok.Setter;
import com.example.ParcialAlario49925.Validators.*;
import jakarta.validation.Valid;

@Getter
@Setter
public class ADNRequest {
    //me fijo que tenga formato valido
    @ADNValido
    private String[] adn;
}
