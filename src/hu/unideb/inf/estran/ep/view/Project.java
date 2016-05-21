package hu.unideb.inf.estran.ep.view;

import hu.unideb.inf.estran.ep.core.EvolutionEngine;


public class Project {

	public Project(String alpha, String omega, int genomeSize)
	{
		this.alpha = alpha;
		this.omega = omega;
		this.genomeSize = genomeSize;
	}

	final private String alpha;
	final private String omega;
	final private int genomeSize;

	public String getAlpha() {
		return alpha;
	}
	public String getOmega() {
		return omega;
	}
	public int getGenomeSize() {
		return genomeSize;
	}

}

/*
public class Project {

	public Project(String alphabet, String alpha, String omega, int method, int weight, int mutationRate,
			boolean differentParents, int populationSize, int maxCycle) {
		super();
		this.alphabet = alphabet;
		this.alpha = alpha;
		this.omega = omega;
		this.method = method;
		this.weight = weight;
		this.mutationRate = mutationRate;
		this.differentParents = differentParents;
		this.populationSize = populationSize;
		this.maxCycle = maxCycle;
	}


	final private String alphabet;
	final private String alpha;
	final private String omega;

	final private int method;
	final private int weight;
	final private int mutationRate;
	final private boolean differentParents;

	final private int populationSize;
	final private int maxCycle;
	public String getAlphabet() {
		return alphabet;
	}
	public String getAlpha() {
		return alpha;
	}
	public String getOmega() {
		return omega;
	}
	public int getMethod() {
		return method;
	}
	public int getWeight() {
		return weight;
	}
	public int getMutationRate() {
		return mutationRate;
	}
	public boolean isDifferentParents() {
		return differentParents;
	}
	public int getPopulationSize() {
		return populationSize;
	}
	public int getMaxCycle() {
		return maxCycle;
	}


}
*/
