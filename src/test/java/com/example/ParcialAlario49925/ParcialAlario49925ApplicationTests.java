package com.example.ParcialAlario49925;


import com.example.ParcialAlario49925.Controllers.ADNController;
import com.example.ParcialAlario49925.Controllers.StatsController;
import com.example.ParcialAlario49925.Validators.ADNValidator;
import com.example.ParcialAlario49925.dto.ADNRequest;
import com.example.ParcialAlario49925.dto.ADNResponse;
import com.example.ParcialAlario49925.dto.StatsResponse;
import com.example.ParcialAlario49925.Services.*;
import com.example.ParcialAlario49925.entities.ADN;
import com.example.ParcialAlario49925.repositories.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.example.ParcialAlario49925.Services.ADNService.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class ParcialAlario49925ApplicationTests {

	//verifico que el springboot funcine coretamente
	@Test
	void contextLoads() {
	}



	@Test
	public void testADNValidoSinSecuencias() {
		String[] adn2 = {
				"AGAATG",
				"TACAGT",
				"GCAGCC",
				"TTGATG",
				"GTAGTC",
				"AGTCAA"
		};
		mostrarMatriz(adn2);
		boolean esMutante = isMutant(adn2);
		assertTrue(esMutante);
	}

	@Test
	public void testADNConSecuenciasDiagonales() {
		String[] adn3 = {
				"AATC",
				"CACC",
				"TCAG",
				"CGTA"
		};
		mostrarMatriz(adn3);
		boolean esMutante = isMutant(adn3);
		assertTrue(esMutante); // Debería ser mutante
	}

	@Test
	public void testADNConVariasSecuencias() {
		String[] adn4 = {
				"TTTTTTTTT",
				"TTTTTTTTT",
				"TTTTTTTTT",
				"TTTTTTTTT",
				"CCGAACAGT",
				"GGCACTCCA",
				"AGGACACTA",
				"CAAAGGCAT",
				"GCAGTCCCC"
		};
		mostrarMatriz(adn4);
		boolean esMutante = isMutant(adn4);
		assertTrue(esMutante); // Debería ser mutante
	}


	@Test
	public void noMutante1() {
		String[] adn5 = {
				"AAAT",
				"AACC",
				"AAAC",
				"CGGG"
		};
		mostrarMatriz(adn5);
		boolean esMutante = isMutant(adn5);
		assertFalse(esMutante);
	}

	@Test
	public void mutante1() {
		String[] adn6 = {
				"TGAC",
				"AGCC",
				"TGAC",
				"GGTC"
		};
		mostrarMatriz(adn6);
		boolean esMutante = isMutant(adn6);
		assertTrue(esMutante);
	}

	@Test
	public void mutante2() {
		String[] adn7 = {
				"AAAA",
				"AAAA",
				"AAAA",
				"AAAA"
		};
		mostrarMatriz(adn7);
		boolean esMutante = isMutant(adn7);
		assertTrue(esMutante);
	}

	@Test
	public void noMutante2() {
		String[] adn8 = {
				"TGAC",
				"ATCC",
				"TAAG",
				"GGTC"
		};
		mostrarMatriz(adn8);
		boolean esMutante = isMutant(adn8);
		assertFalse(esMutante);
	}



	@Test
	public void mutante4() {
		String[] adn10 = {
				"TTTTTTTTT",
				"TTTTTTTTT",
				"TTTTTTTTT",
				"TTTTTTTTT",
				"CCGACCAGT",
				"GGCACTCCA",
				"AGGACACTA",
				"CAAAGGCAT",
				"GCAGTCCCC"
		};
		mostrarMatriz(adn10);
		boolean esMutante = isMutant(adn10);
		assertTrue(esMutante);
	}



	@Test
	public void testADNNulo() {
		String[] adn = null;
		assertFalse(ADNValidator.validarADN(adn));
	}

	@Test
	public void testADNVacio() {
		String[] adnVacio = {};
		boolean esMutante = isMutant(adnVacio);
		assertFalse(esMutante); // No debería ser mutante
	}

	@Test
	public void testADNConCaracteresNoPermitidos() {
		String[] adnInvalido = {
				"AGAATG",
				"TACAGT",
				"GCAGCC",
				"TTGATG",
				"GTAGTC",
				"AGTCAA",
				"AGTC1A" // Caracter no permitido
		};
		boolean esValido = ADNValidator.validarADN(adnInvalido);
		assertFalse(esValido); // No debería ser válido
	}




	@Mock
	private ADNRepository adnRepository;

	@Mock
	private ADNService adnService;

	@Mock
	private StatsService statsService;

	@InjectMocks
	private ADNController adnController;

	@InjectMocks
	private StatsController statsController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}



	@Test
	public void testIsNotMutant() {
		ADNRequest adnRequest = new ADNRequest();
		adnRequest.setAdn(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});

		when(adnService.analizarADN(adnRequest.getAdn())).thenReturn(false);

		ResponseEntity<ADNResponse> response = adnController.isMutant(adnRequest);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
		assertEquals(false, response.getBody().isMutant());
	}




	@Test
	public void testAnalizarADNNoMutante() {
		String[] adn = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
		when(adnRepository.findByAdn(String.join(",", adn))).thenReturn(Optional.empty());
		when(adnRepository.save(ADN.builder().adn(String.join(",", adn)).isMutant(false).build())).thenReturn(null);

		boolean isMutant = adnService.analizarADN(adn);
		assertFalse(isMutant);
	}



	// Pruebas para ADNValidator

	@Test
	public void testValidarADNValido() {
		String[] adn = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
		assertTrue(ADNValidator.validarADN(adn));
	}

	@Test
	public void testValidarADNInvalido() {
		String[] adn = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG1"};
		assertFalse(ADNValidator.validarADN(adn));
	}

	@Test
	public void testValidarADNNulo() {
		String[] adn = null;
		assertFalse(ADNValidator.validarADN(adn));
	}

	@Test
	public void testValidarADNVacio() {
		String[] adn = {};
		assertFalse(ADNValidator.validarADN(adn));
	}

	@Test
	public void testValidarADNConFilasNulas() {
		String[] adn = {"ATGCGA", null, "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
		assertFalse(ADNValidator.validarADN(adn));
	}



	@Test
	public void testCountByIsMutant() {
		when(adnRepository.countByIsMutant(true)).thenReturn(10L);
		when(adnRepository.countByIsMutant(false)).thenReturn(20L);

		long countMutants = adnRepository.countByIsMutant(true);
		long countHumans = adnRepository.countByIsMutant(false);

		assertEquals(10, countMutants);
		assertEquals(20, countHumans);
	}


	@Test
	public void testADNConstructor() {
		ADN adn = ADN.builder().adn("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG").isMutant(true).build();
		assertEquals("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG", adn.getAdn());
		assertTrue(adn.isMutant());
	}


	@Test
	public void testValidarADNConCaracteresNoPermitidos() {
		String[] adn = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG1"};
		assertFalse(ADNValidator.validarADN(adn));
	}




	@Test
	public void testADNResponseConstructor() {
		ADNResponse adnResponse = new ADNResponse(true);
		assertTrue(adnResponse.isMutant());
		assertEquals("Es mutante", adnResponse.getMessage());
	}


	@Test
	public void testADNResponse() {
		ADNResponse adnResponse = new ADNResponse(true);
		assertTrue(adnResponse.isMutant());
		assertEquals("Es mutante", adnResponse.getMessage());
	}

	@Test
	public void testStatsResponseConstructor() {
		StatsResponse statsResponse = new StatsResponse(10L, 20L, 0.5);
		assertEquals(10L, statsResponse.getCountMutantADN());
		assertEquals(20L, statsResponse.getCountHumanADN());
		assertEquals(0.5, statsResponse.getRatio(), 0.001);
	}


	@Test
	public void testADNEntity() {
		// Crear una instancia de ADN usando el constructor de Lombok
		ADN adn = ADN.builder()
				.adn("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG")
				.isMutant(true)
				.build();

		// Verificar que los valores se hayan establecido correctamente
		assertEquals("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG", adn.getAdn());
		assertTrue(adn.isMutant());

		// Modificar los valores usando los setters
		adn.setAdn("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG");
		adn.setMutant(false);

		// Verificar que los valores se hayan modificado correctamente
		assertEquals("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG", adn.getAdn());
		assertFalse(adn.isMutant());
	}



}




