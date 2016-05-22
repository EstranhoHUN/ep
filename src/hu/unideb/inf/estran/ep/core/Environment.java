package hu.unideb.inf.estran.ep.core;
import java.util.Random;
import java.util.Vector;

public class Environment {

    private int populationSize;
    private int genomeSize;
    private String alphabet;
    private String alpha;
    private String omega;


    public Environment(int populationSize, int genomeSize, String alphabet, String omega) {

        this.populationSize = populationSize;
        this.genomeSize = genomeSize;
        this.alphabet = alphabet;
        this.omega = omega;

        if(isOmegaValid()) {} else {/* SHIT */}
    }




    public Environment(int populationSize, int genomeSize, String alphabet, String alpha, String omega) {

        this.populationSize = populationSize;
        this.genomeSize = genomeSize;
        this.alphabet = alphabet;
        this.alpha = alpha;
        this.omega = omega;

        if(isAlphaValid() && isOmegaValid()) {} else {/* SHIT */} //exception
    }




    public String getAlpha() {
        return alpha;
    }





    public int getPopulationSize() {
        return populationSize;
    }



    public int getGenomeSize() {
        return genomeSize;
    }



    public String getAlphabet() {
        return alphabet;
    }



    public String getOmega() {
        return omega;
    }



    public boolean isAlphaValid() {

    	if(alpha.equals("")) return true;


	        boolean charFound = false;
	        boolean allFound = true;

	        for (char ca: alpha.toCharArray()) {
	            for (char cab: alphabet.toCharArray()) {
	                if (ca==cab) charFound = true;
	            }
	            allFound &= charFound;
	            charFound = false;
	        }

	        return allFound && alpha.length()==genomeSize;

    }

    public boolean isOmegaValid() {

        boolean charFound = false;
        boolean allFound = true;

        for (char co: omega.toCharArray()) {
            for (char cab: alphabet.toCharArray()) {
                if (co==cab) charFound = true;
            }
            allFound &= charFound;
            charFound = false;
        }

        return allFound && omega.length()==genomeSize;
    }


        public Unit mutate(Unit u, int mutationRate) {

            mutationRate = mutationRate < 0 ? 0 : mutationRate > 10 ? 10 : mutationRate;

            Random rand = new Random();

            String genome = "";

            for(int i = 0;i<genomeSize;i++) {


                genome += rand.nextInt(10) < mutationRate ? alphabet.charAt(rand.nextInt(alphabet.length())) : u.getGenome().charAt(i);
            }


            return new Unit(genome, calculateFitness(genome));
        }


        public Unit CrossOver (Unit u1, Unit u2, int weight) {

            Random rand = new Random();
            String s = "";

            if (weight == -1)
                for(int i = 0;i<genomeSize;i++) s += rand.nextInt(1)==0?u1.getGenome().charAt(i):u2.getGenome().charAt(i);

            else if(weight == 0) {
                Unit u3 = u1.getFitness()>u2.getFitness()?u1:u2;
                int chance = Math.round(100/(Math.abs(u1.getFitness()-u2.getFitness()+1)));
                for(int i = 0;i<genomeSize;i++) s += rand.nextInt(chance) == 0 ? u3.getGenome().charAt(i): rand.nextInt(1)==0?u1.getGenome().charAt(i):u2.getGenome().charAt(i);
            }

            else {
                Unit u3 = u1.getFitness()>u2.getFitness()?u1:u2;
                for(int i = 0;i<genomeSize;i++) s += rand.nextInt(4) != 0 ? u3.getGenome().charAt(i): rand.nextInt(1)==0?u1.getGenome().charAt(i):u2.getGenome().charAt(i);
            }

            Unit u = new Unit(s, calculateFitness(s));
            return u;
        }



        public Unit Mutate (Unit u, int mutationRate) {
            return u;
        }
/*
        public Vector<Unit> Elitism (Population p, int elitismRate) {
            return p.getUnits();
        }
*/
        public int calculateFitness (String s) { //0 is a no match, 100 is a perfect match - peakUnit

            if(s.equals(omega)) return 100;

            else {

                int genomeFitness = 0;
                int geneFitness = Math.round(100/genomeSize);

                for(int i = 0;i<s.length();i++) {
                    //distance += Math.abs((int)s.charAt(i) - (int)Omega.charAt(i));
                    genomeFitness += s.charAt(i) == omega.charAt(i) ? geneFitness : 0;
                }

                return genomeFitness;
            }
        }

        public String generateGenome () {

            Random rand = new Random();
            String s = "";
            for(int i = 0;i<genomeSize;i++) s += alphabet.charAt(rand.nextInt(alphabet.length()));

            return s;
        }

        public String generateGenomeSeeded () {

            Random rand = new Random();
            String s = "";
            for(int i = 0;i<genomeSize;i++) s += alpha.charAt(rand.nextInt(alpha.length()));

            return s;
        }

        public Unit generateUnit() {
            String genome = generateGenome();
            //System.out.println(genome + " (" + calculateFitness(genome) + ")"); //test
            Unit u = new Unit(genome, calculateFitness(genome));

            return u;
        }

        public Unit generateUnitSeeded() {
            String genome = generateGenomeSeeded();
            Unit u = new Unit(genome, calculateFitness(genome));
            return u;
        }



}
