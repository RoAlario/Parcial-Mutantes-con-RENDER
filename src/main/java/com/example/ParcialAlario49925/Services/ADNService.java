package com.example.ParcialAlario49925.Services;


/*
En esta  clase tengo el algoritmo para recorrer la matriz
mostrarla y verificar si es mutante o no
*/


import com.example.ParcialAlario49925.entities.ADN;
import com.example.ParcialAlario49925.repositories.ADNRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import static com.example.ParcialAlario49925.Validators.ADNValidator.validarADN;

@Service
public class ADNService {

    private  ADNRepository adnRepository;

    @Autowired
    public ADNService(ADNRepository adnRepository) {
        this.adnRepository = adnRepository;
    }


    public boolean analizarADN(String[] adn) {
        // Si el ADN no es valido, termina aca.
        if (!validarADN(adn)) {
            return false;
        }

        String adnSecuencia = String.join(",", adn);

        // Verificamos si el ADN ya existe en la base de datos
        Optional<ADN> existingADN = adnRepository.findByAdn(adnSecuencia);
        if (existingADN.isPresent()) {
            // Si el ADN ya fue analizado, retornamos el resultado
            return existingADN.get().isMutant();
        }

        // Determinamos si el ADN es mutante y lo guardamos en la base de datos
        boolean isMutant = isMutant(adn);
        ADN adnEntity = ADN.builder()
                .adn(adnSecuencia)
                .isMutant(isMutant)
                .build();
        adnRepository.save(adnEntity);

        return isMutant;

    }


    // Recibo la matriz y verifico si es mutante
    public static boolean isMutant(String[] adn) {

        int n = adn.length;
        String[][] matriz = new String[n][n];

        // Transformo el array que recibo en POSTMAN en una matriz
        for (int i = 0; i < n; i++) {
            matriz[i] = adn[i].split("");
        }
        // Verifico la matriz
        return verificacion(matriz);
    }

    public static boolean verificacion(String matriz[][]) {
        int n = matriz.length;
        int totalSecuencias = 0;
        int totalFila = 0;
        int totalColumna = 0;

        for (int fila = 0; fila < n; fila++) {
            int secFila = revisarFila(matriz[fila]);
            totalFila += secFila;
        }

        for (int col = 0; col < n; col++) {
            int secCol = revisarColumna(matriz, col, n);
            totalColumna += secCol;
        }

        int secDP = revisarDiagPrincipal(matriz, n);
        int secDS = revisarDiagonalSecundaria(matriz, n);

        totalSecuencias = secDS + secDP + totalFila + totalColumna;

        if (totalSecuencias > 1) {
            System.out.println("El ADN es mutante");
            return true;
        } else {
            System.out.println("El ADN NO es mutante");
            return false;
        }
    }

    // Reviso las filas
    public static int revisarFila(String[] fila) {
        String anterior = null;
        int contador = 0;
        int secuenciasFila = 0;

        for (String letra : fila) {
            if (letra.equals(anterior)) {
                contador++;
                if (contador == 4) {
                    secuenciasFila++;
                    contador = 0;
                }
            } else {
                anterior = letra;
                contador = 1;
            }
        }
        return secuenciasFila;
    }

    // Reviso las columnas
    public static int revisarColumna(String matriz[][], int col, int n) {
        String anterior = null;
        int contador = 0;
        int secuenciaColumna = 0;

        for (int fila = 0; fila < n; fila++) {
            String letra = matriz[fila][col];
            if (letra.equals(anterior)) {
                contador++;
                if (contador == 4) {
                    secuenciaColumna++;
                    contador = 0;
                }
            } else {
                anterior = letra;
                contador = 1;
            }
        }
        return secuenciaColumna;
    }

    public static int revisarDiagPrincipal(String matriz[][], int n) {
        int secuenciaDiagonalPrincipal = 0;
        String anteriorDP = null;
        int contadorDP = 0;
        for (int i = 0; i < n; i++) {
            String letra = matriz[i][i];
            if (letra.equals(anteriorDP)) {
                contadorDP++;
                if (contadorDP == 4) {
                    secuenciaDiagonalPrincipal++;
                    contadorDP = 1;
                }
            } else {
                anteriorDP = letra;
                contadorDP = 1;
            }
        }
        return secuenciaDiagonalPrincipal;
    }

    public static int revisarDiagonalSecundaria(String matriz[][], int n) {
        int secuenciaDiagonalSecundaria = 0;
        String anterior = null;
        int contadorDS = 0;

        for (int i = 0; i < n; i++) {
            String letra = matriz[i][n - i - 1];
            if (letra.equals(anterior)) {
                contadorDS++;
                if (contadorDS == 4) {
                    secuenciaDiagonalSecundaria++;
                    contadorDS = 1;
                }
            } else {
                anterior = letra;
                contadorDS = 1;
            }
        }
        return secuenciaDiagonalSecundaria;
    }


    //==========MOSTRAR MATRIZ==========//

    public static void mostrarMatriz(String[] dna) {
        int n = dna.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(dna[i].charAt(j) + " ");
            }
            System.out.println();
        }
    }



}


