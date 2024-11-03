package com.example.ParcialAlario49925.Controllers;



/*
Cuando se hace un getstats, esta clase recibe la solicitud, llama a getstats()
de statsService, recibe las estadisticas y las devuelve en JSON
*/


import com.example.ParcialAlario49925.dto.StatsResponse;
import com.example.ParcialAlario49925.Services.StatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public StatsResponse getStats() {
        return statsService.getStats();
    }
}