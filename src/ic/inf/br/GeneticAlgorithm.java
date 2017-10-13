package ic.inf.br;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm {

	public Random r = new Random();
	public Solution s;

	public int CHROMOSOME_SIZE = 32;
	public int MAX_GENERATIONS = 1000;
	public int POPULATION_SIZE = 32;
	public float CROSSOVER_RATE = 0.95f;
	public float MUTATION_RATE = 0.15f;

	public ArrayList<Solution> population = new ArrayList<>();
	public ArrayList<Solution> offspring = new ArrayList<>();

	public static void main(String[] args) {

		GeneticAlgorithm ag = new GeneticAlgorithm();
		
		ag.s = new Solution(ag.CHROMOSOME_SIZE);
		
		ag.initializePop();

		Collections.sort(ag.population);

		int generation = 0;
		while (generation < ag.MAX_GENERATIONS) {

			ag.performeCrossover();
			ag.mutation();
			ag.copyOffspring();

			Collections.sort(ag.population);

			System.out.println(ag.avgGenerationFitness());

			generation++;
		}
		
		ag.printSolution(ag.population.get(0));
	}

	private void copyOffspring() {

		for (Solution solution : offspring)
			this.population.add(solution);

		Collections.sort(this.population);

		for (int i = this.population.size() - 1; i >= this.POPULATION_SIZE; i--)
			this.population.remove(i);

		this.offspring.clear();

	}

	// initializing population
	public void initializePop() {

		for (int i = 0; i < POPULATION_SIZE; i++) {

			int[] individual = new int[s.chromosome_size];
			for (int j = 0; j < individual.length; j++)
				individual[j] = 0;
				//individual[j] = r.nextInt(2);
			population.add(new Solution(individual));
		}

	}	

	public int binaryTournament() {
		// Attention: this works just whether population is already sorted by fitness values
		int x1 = r.nextInt(POPULATION_SIZE);
		int x2 = r.nextInt(POPULATION_SIZE);
		return (x1 < x2) ? x1 : x2;
	}

	public void performeCrossover() {

		// Adjusting for even number of parents
		int size = (int) (POPULATION_SIZE / 2 * CROSSOVER_RATE) * 2;

		for (int i = 0; i < size; i += 2) {
			ArrayList<Solution> sons = this.onePoint(population.get(this.binaryTournament()), population.get(this.binaryTournament()));
			this.offspring.add(sons.get(0));
			this.offspring.add(sons.get(1));
		}

	}

	public ArrayList<Solution> onePoint(Solution parent_a, Solution parent_b) {

		ArrayList<Solution> sons = new ArrayList<>();

		int cut_point = r.nextInt(CHROMOSOME_SIZE - 2) + 1;
		int[] son_a = new int[CHROMOSOME_SIZE];
		int[] son_b = new int[CHROMOSOME_SIZE];

		for (int i = 0; i < CHROMOSOME_SIZE; i++) {

			if (i <= cut_point) {
				son_a[i] = parent_a.chromosome[i];
				son_b[i] = parent_b.chromosome[i];
			} else {
				son_a[i] = parent_b.chromosome[i];
				son_b[i] = parent_a.chromosome[i];
			}

		}

		sons.add(new Solution(son_a));
		sons.add(new Solution(son_b));

		return sons;
	}

	// bit-flip mutation
	public void mutation() {
		for (Solution solution : offspring) {
			for (int i = 0; i < solution.chromosome.length; i++) {
				float prob = r.nextFloat();
				if (prob <= MUTATION_RATE)
					if (solution.chromosome[i] == 0)
						solution.chromosome[i] = 1;
					else
						solution.chromosome[i] = 0;
				solution.evaluate();
			}
		}
	}

	public void printSolution(Solution s) {

		System.out.println("\n--------------------");
		for (int gene : s.chromosome)
			System.out.print("|" + gene);

		System.out.println("\n--------------------");
		System.out.println(s.fitness);

	}
	
	public float avgGenerationFitness() {
		float avg = 0;
		for (Solution solution : population) 
			avg += solution.fitness;
		return avg / population.size();
	}
}
