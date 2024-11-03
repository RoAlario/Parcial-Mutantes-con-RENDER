package com.example.ParcialAlario49925.repositories;


/*
Permite gestionar a ADN mediante métodos
*/



import com.example.ParcialAlario49925.entities.ADN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ADNRepository extends JpaRepository<ADN, Long> {

    //Buscar secuenciasde ADN
    Optional<ADN> findByAdn(String secuencia);

    //Contar cuantos son mutantes
    long countByIsMutant(boolean isMutant);
}