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

	private Vector<Integer> averageFitness;
	private Vector<Integer> peakFitness;
	private String allTimeFittestGenome;
	private int allTimePeakFitness;

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
		}

		allTimeFittestGenome = p.getAllTimePeakGenome();
		allTimePeakFitness = p.getAllTimePeakFitness();
	}

	public Vector<Integer> getAverageFitness() {return averageFitness;}
	public Vector<Integer> getPeakFitness() {return peakFitness;}
	public String getAllTimeFittestGenome() {return allTimeFittestGenome;}
	public int getAllTimePeakFitness() {return allTimePeakFitness;}
}