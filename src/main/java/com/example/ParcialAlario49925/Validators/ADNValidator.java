package com.example.ParcialAlario49925.Validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ADNValidator implements ConstraintValidator<ADNValido, String[]> {

    @Override
    public void initialize(ADNValido constraintAnnotation) {
        // Aquí puedes inicializar cualquier cosa si es necesario
    }

    @Override
    public boolean isValid(String[] adn, ConstraintValidatorContext context) {
        return validarADN(adn);
    }

    // Valido la matriz antes de verificarla
    public static boolean validarADN(String[] adn) {

        // Verifico si el array es null
        if (adn == null) {
            System.out.println("Error: El ADN es nulo");
            return false;
        }

        // Verifico si está vacío
        if (adn.length == 0) {
            System.out.println("Error: El ADN está vacío.");
            return false;
        }

        // Verifico si todas las filas tienen el mismo tamaño (N x N)
        int n = adn.length;
        for (String fila : adn) {
            if (fila == null) {
                System.out.println("Error: Una de las filas es null.");
                return false;
            }
            if (fila.length() != n) {
                System.out.println("Error: El ADN no es NxN.");
                return false;
            }
        }

        // Verifico que solo contenga 'A', 'C', 'G', 'T'
        for (String fila : adn) {
            for (char letra : fila.toCharArray()) {
                if (letra != 'A' && letra != 'C' && letra != 'G' && letra != 'T') {
                    System.out.println("Error: El ADN contiene caracteres no permitidos: " + letra);
                    return false;
                }
            }
        }

        // Si pasa todas las validaciones, retornamos true
        System.out.println("El ADN es válido.");
        return true;
    }
}
