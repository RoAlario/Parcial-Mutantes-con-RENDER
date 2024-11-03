package com.example.ParcialAlario49925.entities;



/*
representa un registro en la tabla de ADN en la base de datos
*/



import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "adn")
@Setter
@Builder

public class ADN extends Base implements Serializable {

    // private String[] adn;
    @Column(unique = true)
    private String adn;

    private boolean isMutant;

}