package hu.unideb.inf.estran.ep.core;
import java.util.Random;
import java.util.Vector;

import com.sun.javafx.print.Units;

public class Population {

	public Population(Environment e) {

		this.e = e;
		units = new Vector<>();


	}

	private Vector<Unit> units; //egyedek
	private int populationFitness;
	private int avarageFitness;
	private int peakFitness;
	private Environment e;
	private String allTimePeakGenome;

	public int getPopulationFitness() {
		return populationFitness;
	}

	public int getavarageFitness() {
		return avarageFitness;
	}

	public void evolve(int method, int weight, boolean differentParents) {

		Vector<Unit> nextGeneration = new Vector<>(); //StarTrek FTW
		Unit u1, u2;

		for(int i = 0;i<e.getPopulationSize();i++) {

			u1 = Selection(method);
			u2 = Selection(method);
			while(differentParents && u1.equals(u2) ) { u2 = Selection(method);}

			nextGeneration.add(e.CrossOver(
					u1,
					u2,
					weight));
		}

		units.clear();
		units = nextGeneration;

		update();

	}

	public Unit Selection (int method) { //átkéne rakni env-be... ha lesz idõ...
		return method==0?TruncationSelection():method==1?RouletteWheelSelection():RandomSelection();
	}


	public Unit TruncationSelection () { // >= avg ; 0
		Random rand = new Random();
		Unit u = units.elementAt(rand.nextInt(e.getPopulationSize()));

		while (u.getFitness()<avarageFitness)
			u = units.elementAt(rand.nextInt(e.getPopulationSize()));

		return u;
	}


	public Unit RouletteWheelSelection () { // weighted by fitness ; IF popF is 0 -> randomS

		if (populationFitness == 0) return RandomSelection();

		else {

			int rand = new Random().nextInt(populationFitness);
			int i = 0;
			int counter = units.elementAt(i).getFitness();

			while (counter < rand) counter += units.elementAt(++i).getFitness();

			return units.elementAt(i);
		}


	}

	public Unit RandomSelection () { //random ; else
		Random rand = new Random();
		return units.elementAt(rand.nextInt(e.getPopulationSize()));
	}


	public void mutate(int mutationRate) {

		Random rand = new Random();
		mutationRate = mutationRate < 0 ? 0 : mutationRate > 10 ? 10 : mutationRate;

		for (int i = 0; i<e.getPopulationSize();i++) if (rand.nextInt(10) < mutationRate) units.set(i, e.mutate(units.elementAt(i), mutationRate));


		update();

	}


	public void genesis() { //creates initial population
		for (int i=0;i<e.getPopulationSize();i++) units.add(e.generateUnit());

		update();

	}

	private void update(){
		updatePeakFitness();
		updatePopulationFitness();
		updateAvarageFitness();
		updateAllTimePeakGenome();
	}

	private void updateAllTimePeakGenome() {
		if(allTimePeakGenome == null) allTimePeakGenome = getPeakGenome();
		if(e.calculateFitness(allTimePeakGenome)<e.calculateFitness(getPeakGenome())) allTimePeakGenome=getPeakGenome();

	}

	public void genesisFromSeed() { //creates initial population from seed - alpha

		for (int i=0;i<e.getPopulationSize();i++) units.add(e.generateUnitSeeded());

		update();
	}

	private Unit getFittestUnit() {


		Unit unit = units.elementAt(0);

		for (Unit u : units) {
			if (unit.getFitness()<u.getFitness()) unit = u;
		}

		return unit;

	}

	public int getPeakFitness() {
		return getFittestUnit().getFitness();
	}

	public String getPeakGenome() {
		return getFittestUnit().getGenome();
	}

	public void updateAvarageFitness() { avarageFitness = populationFitness == 0?0:populationFitness/e.getPopulationSize();}

	public void updatePopulationFitness() {
		populationFitness = 0;
		for (Unit u : units) populationFitness += u.getFitness();

	}

	public void updatePeakFitness() {peakFitness = getPeakFitness();}

	public String getAllTimePeakGenome() {
		return allTimePeakGenome;
	}

}

//egyenlõ szülõk - ha elsz idõ
