package hu.unideb.inf.estran.ep.test;

import static org.junit.Assert.*;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

import hu.unideb.inf.estran.ep.core.Environment;
import hu.unideb.inf.estran.ep.core.EvolutionEngine;
import hu.unideb.inf.estran.ep.core.Population;
import hu.unideb.inf.estran.ep.core.Unit;
import hu.unideb.inf.estran.ep.dao.ProjectDao;
import hu.unideb.inf.estran.ep.dao.ProjectDaoJsonImpl;
import hu.unideb.inf.estran.ep.dao.ProjectService;

public class CoreTest {

	private EvolutionEngine ee, eeSeeded, eeNumbers;
	private Environment eOK, eFail, eFail_, eDivergent;
	private Unit u1, u2;
	private ProjectDaoJsonImpl pDJ;
	private ProjectService pS;
	private Population p,q;

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

		ee = new EvolutionEngine(populationSize, omega.length(), alphabet+ALPHABET+symbols, "", omega, maxCycle, method, weight, mutationRate, differentParents);
		eeSeeded = new EvolutionEngine(populationSize, omega.length(), alphabet+ALPHABET+symbols, alpha, omega, maxCycle, method, weight, mutationRate, differentParents);
		eeNumbers = new EvolutionEngine(populationSize, omega.length(), numbers, alpha, omega, maxCycle, method, weight, mutationRate, differentParents);

		eOK = new Environment(populationSize, 12, alphabet+ALPHABET+symbols, alpha, omega);
		eFail = new Environment(populationSize, 12, alphabet, alpha, omega);
		eFail_ = new Environment(populationSize, 11, alphabet+ALPHABET+symbols, alpha, omega);
		eDivergent = new Environment(populationSize, 5, alphabet+ALPHABET+symbols, "", "éáõúû");

		u1 = new Unit("genome", eOK.calculateFitness("genome"));
		u2 = new Unit("geneom", eOK.calculateFitness("geneom"));

		pDJ = new ProjectDaoJsonImpl();
		pS = new ProjectService();

		p = new Population(eOK);
		p.genesis();

		q = new Population(eDivergent);
		q.genesis();

	}


	@Test
	public void instances() {

		assertThat(ee, is(not(eeSeeded)));
		assertThat(eeNumbers, IsNull.notNullValue());
		assertThat(u1, is(not(u2)));
		assertThat(ee, instanceOf(EvolutionEngine.class));
		assertThat(eOK, instanceOf(Environment.class));
		assertThat(u1, instanceOf(Unit.class));
		assertThat(pDJ, instanceOf(ProjectDao.class));
		assertThat(pDJ, instanceOf(ProjectDaoJsonImpl.class));
		assertThat(pS, instanceOf(ProjectService.class));
	}

	@Test
	public void methods(){
		assertEquals(eOK.isAlphaValid(), true);
		assertEquals(eOK.isOmegaValid(), true);
		assertEquals(eFail.isAlphaValid(), false);
		assertEquals(eFail.isOmegaValid(), false);
		assertEquals(eFail_.isAlphaValid(), false);
		assertEquals(eFail_.isOmegaValid(), false);
		assertEquals(eOK.calculateFitness("Hello World!"), 100);
		assertEquals(eOK.calculateFitness("Hello World?"), 88);
		assertEquals(eOK.calculateFitness("XXXXXXXXXXXX"), 0);
		assertEquals(eOK.calculateFitness("Hello World?"), eOK.calculateFitness("Xello World!"));
		assertEquals(p.getPopulationFitness()/eOK.getPopulationSize(), p.getavarageFitness());
		assertEquals(eOK.calculateFitness(u1.getGenome()), eOK.calculateFitness("genome"));
		assertEquals(u1.getFitness(), eOK.calculateFitness("genome"));
		assertEquals(p.getPeakFitness(), eOK.calculateFitness(p.getPeakGenome()));
		assertTrue(p.getPeakFitness() >= 0);
		assertTrue(p.getAllTimePeakFitness()>=p.getPeakFitness());
		assertEquals(q.getAllTimePeakFitness(), 0);
	}
}
