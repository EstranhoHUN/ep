package hu.unideb.inf.estran.ep.core;

import java.util.Vector;

public class EvolutionEngine {

	public EvolutionEngine(int populationSize, int genomeSize, String alphabet, String omega, int maxCycle) {
		this.maxCycle = maxCycle;

		p = new Population(new Environment(populationSize, genomeSize, alphabet, omega));
		p.genesis();
	}

	public EvolutionEngine(int populationSize, int genomeSize, String alphabet, String alpha, String omega, int maxCycle) {
		this.maxCycle = maxCycle;

		p = new Population(new Environment(populationSize, genomeSize, alphabet, alpha, omega));
		p.genesisFromSeed();
	}

	private Population p;
	private int maxCycle;
	private Vector<Integer> averageFitness;// = new Dimension()
	private Vector<Integer> maxFitness;// = new Dimension()
	private Unit fittestUnit;


	public void evolution(int method, int weight) {

		for (int currentCycle = 0; currentCycle<maxCycle; currentCycle++) {

			System.out.println("Peak unit: " + p.getPeakGenome() + "(" + p.getPeakFitness() + "/"+p.getavarageFitness()+")");
			p.evolve(method, weight);
			//average és max fittness valamint all top unit mentése
			//MUTATE / CROSSOVER
		}

	}




}
