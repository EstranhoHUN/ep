package hu.unideb.inf.estran.ep.test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import hu.unideb.inf.estran.ep.core.Environment;
import hu.unideb.inf.estran.ep.core.EvolutionEngine;
import hu.unideb.inf.estran.ep.core.Population;
import hu.unideb.inf.estran.ep.core.Unit;
import hu.unideb.inf.estran.ep.dao.ProjectDao;
import hu.unideb.inf.estran.ep.dao.ProjectDaoJsonImpl;
import hu.unideb.inf.estran.ep.dao.ProjectService;
import hu.unideb.inf.estran.ep.view.Project;

public class CoreTest {

	private EvolutionEngine ee, eeSeeded;
	private Environment eOK, eFail, eFail_;
	private Unit u1, u2;
	private ProjectDaoJsonImpl pDJ;
	private ProjectService pS;
	private Population p;

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

		eOK = new Environment(populationSize, 12, alphabet+ALPHABET+symbols, alpha, omega);
		eFail = new Environment(populationSize, 12, alphabet, alpha, omega);
		eFail_ = new Environment(populationSize, 11, alphabet+ALPHABET+symbols, alpha, omega);

		u1 = new Unit("genome", eOK.calculateFitness("genome"));
		u2 = new Unit("geneom", eOK.calculateFitness("geneom"));

		pDJ = new ProjectDaoJsonImpl();
		pS = new ProjectService();

		p = new Population(eOK);

		p.genesis();

	}


	@Test
	public void instances() {

		assertThat(ee, is(not(eeSeeded)));
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
	}
}
