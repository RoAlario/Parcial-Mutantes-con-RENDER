package com.example.ParcialAlario49925.Services;


/*
Genera las  estadisticas
*/

import com.example.ParcialAlario49925.dto.StatsResponse;
import com.example.ParcialAlario49925.repositories.ADNRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final ADNRepository adnRepository;

    @Autowired
    public StatsService(ADNRepository adnRepository) {
        this.adnRepository = adnRepository;
    }

    public StatsResponse getStats() {
        long contarMutantes = adnRepository.countByIsMutant(true);
        long contarHumanos = adnRepository.countByIsMutant(false);
        double ratio = contarHumanos == 0 ? 0 : (double) contarMutantes / contarHumanos;
        return new StatsResponse(contarMutantes, contarHumanos, ratio);
    }
}