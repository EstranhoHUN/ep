package hu.unideb.inf.estran.ep.core;

public class Unit {

	public Unit(String genome, int fitness) {
		//super();
		this.genome = genome;
		this.fitness = fitness;
	}

	private String genome; //DNS
	private int fitness;

	public String getGenome() {
		return genome;
	}

	public int getFitness() {
		return fitness;
	}

	@Override
	public String toString() {
		return genome + " (" + fitness + ")";
	}


}
