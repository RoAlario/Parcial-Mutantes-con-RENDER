    package com.example.ParcialAlario49925.dto;


    /*
    Devuelve estadísticas sobre cuántos ADN mutantes y humanos
    se procesaron y el ratio entre ellos.
    */


    import com.fasterxml.jackson.annotation.JsonProperty;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.Getter;
    import lombok.Setter;

    @AllArgsConstructor
    @Getter
    @Setter
    @Data
    public class StatsResponse {

        @JsonProperty("count_mutant_adn")
        private long countMutantADN;

        @JsonProperty("count_human_adn")
        private long countHumanADN;

        //para decir cuantos mtantes y no mutantes hay (ej 3 mut 1 humano 3:1)
        private double ratio;

    }