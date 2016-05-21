package hu.unideb.inf.estran.ep.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import hu.unideb.inf.estran.ep.core.EvolutionEngine;

public class CoreTest {

	private EvolutionEngine ee;
	private EvolutionEngine eeSeeded;

	@Before
	public void setUp() throws Exception {

		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numbers = "0123456789";
		String symbols = "+-*/%=<>()[]{}.,;!? _-&|$*'";

		String alpha = "HAlL oWXrld!";
		String omega = "Hello World!";

		int method = 1;  //method  0: truncation, 1: wheel(best), else: random
		int weight = 1;  //weight -1: random, 0: defined by fitness(best), else: given rate
		int mutationRate = 2; //mutationRate 0-10 = 0%-100%
		boolean differentParents = true;


		int populationSize = 250;
		int maxCycle = 250;

		ee = new EvolutionEngine(populationSize, omega.length(), alphabet+ALPHABET+symbols, omega, "", maxCycle, method, weight, mutationRate, differentParents);
		eeSeeded = new EvolutionEngine(populationSize, omega.length(), alphabet+ALPHABET+symbols, alpha, omega, maxCycle, method, weight, mutationRate, differentParents);


	}


	@Test
	public void test() {

		//assertEquals("2*3=?6", 6, test.multiply(2,3));

		assertThat(ee, is(not(eeSeeded)));
		assertThat(eeSeeded, is(not(ee)));
		//assertEquals("Should be fail.", ee, eeSeeded);
		//fail("Not yet implemented");
	}

}
