package com.example.ParcialAlario49925.Controllers;

/*
Recibe solicitudes con una secuencia de ADN, las procesa a traves de
ADN Services y nos dice si ADN es mutante o no
*/


import com.example.ParcialAlario49925.Services.ADNService;
import com.example.ParcialAlario49925.dto.ADNRequest;
import com.example.ParcialAlario49925.dto.ADNResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Getter
@Setter
@RestController
@RequestMapping("/mutant")
public class ADNController {

    private final ADNService adnService;

    // Constructor para inyección de dependencia
    public ADNController(ADNService adnService) {
        this.adnService = adnService;
    }

    @PostMapping
    public ResponseEntity<ADNResponse> isMutant(@Valid @RequestBody ADNRequest adnRequest) {
        boolean isMutant = adnService.analizarADN(adnRequest.getAdn());
        ADNResponse adnResponse = new ADNResponse(isMutant);
        if (isMutant) {
            return ResponseEntity.ok(adnResponse);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(adnResponse);
        }
    }
}




