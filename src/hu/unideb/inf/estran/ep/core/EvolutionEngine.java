package hu.unideb.inf.estran.ep.core;

import java.util.Vector;

public class EvolutionEngine {

	public EvolutionEngine(int populationSize, int genomeSize, String alphabet, String alpha, String omega, int maxCycle, int method, int weight, int mutationRate, boolean differentParents) {
		this.maxCycle = maxCycle;
		this.method=method;
		this.weight=weight;
		this.mutationRate = mutationRate;
		this.differentParents = differentParents;

		averageFitness = new Vector<>();
		peakFitness = new Vector<>();


		p = new Population(new Environment(populationSize, genomeSize, alphabet, alpha, omega));
		if(alpha.equals(""))p.genesis(); else p.genesisFromSeed();
	}

	private Population p;
	private int maxCycle;

	private Vector<Integer> averageFitness;// = new Dimension()
	private Vector<Integer> peakFitness;// = new Dimension()
	private String fittestGenome;

	private int method;
	private int weight;
	private int mutationRate;
	private boolean differentParents;

	public void evolution() {

		for (int currentCycle = 0; currentCycle<maxCycle; currentCycle++) {


			averageFitness.add(p.getavarageFitness());
			peakFitness.add(p.getPeakFitness());
			p.evolve(method, weight, differentParents);
			p.mutate(mutationRate);
			//average és max fittness valamint all top unit mentése
			//MUTATE / CROSSOVER
		}

		fittestGenome = p.getAllTimePeakGenome();

	}

	public Vector<Integer> getAverageFitness() {return averageFitness;}
	public Vector<Integer> getPeakFitness() {return peakFitness;}
	public String getFittestGenome() {return fittestGenome;}


}
