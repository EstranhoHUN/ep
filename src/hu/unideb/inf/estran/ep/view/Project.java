package hu.unideb.inf.estran.ep.view;

public class Project {

	public Project(String projectName, boolean alphabet, boolean ALPHABET, boolean numbers, boolean symbols, String alpha, String omega,
			int method, int weight, int mutationRate, boolean differentParents, int populationSize, int maxCycle) {
		super();
		this.projectName = projectName;
		this.alphabet = alphabet;
		this.ALPHABET = ALPHABET;
		this.numbers = numbers;
		this.symbols = symbols;
		this.alpha = alpha;
		this.omega = omega;
		this.method = method;
		this.weight = weight;
		this.mutationRate = mutationRate;
		this.differentParents = differentParents;
		this.populationSize = populationSize;
		this.maxCycle = maxCycle;
	}
	public boolean isAlphabet() {
		return alphabet;
	}
	public boolean isALPHABET() {
		return ALPHABET;
	}
	public boolean isNumbers() {
		return numbers;
	}
	public boolean isSymbols() {
		return symbols;
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
	public String getProjectName() {
		return projectName;
	}

	private String projectName;

	private boolean alphabet;
	private boolean ALPHABET;
	private boolean numbers;
	private boolean symbols;

	private String alpha;
	private String omega;

	private int method; //0,1,2
	private int weight; //0,1,2
	private int mutationRate; //0,1,2,3,4,5

	private boolean differentParents;

	private int populationSize; //10-1000
	private int maxCycle;  //0-1000
}