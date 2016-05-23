package hu.unideb.inf.estran.ep.core;

public class Unit {

	public Unit(String genome, int fitness) {
		this.genome = genome;
		this.fitness = fitness;
	}

	private String genome;
	private int fitness;

	public String getGenome() {
		return genome;
	}

	public int getFitness() {
		return fitness;
	}
}
