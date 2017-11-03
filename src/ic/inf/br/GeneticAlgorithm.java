package ic.inf.br;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.sun.javafx.css.Rule;

public class GeneticAlgorithm {

	public Random r = new Random();

	public int CHROMOSOME_SIZE 	= 44;
	public int MAX_GENERATIONS 	= 200;
	public int POPULATION_SIZE 	= 100;
	public float CROSSOVER_RATE = 0.90f;
	public float MUTATION_RATE 	= 0.05f;
	private int ELITISM_NUMBER 	= 0;
	
	private Selection selection_op = new Roulette();

	public ArrayList<Solution> population = new ArrayList<>();
	public ArrayList<Solution> offspring = new ArrayList<>();
	
	public Invitro IVF = new Invitro(CHROMOSOME_SIZE); // for In Vitro Implementation

	public Solution solve() {

		GeneticAlgorithm ag = new GeneticAlgorithm();

		ag.initializePop();

		Collections.sort(ag.population);

		int generation = 0;
		while (generation < ag.MAX_GENERATIONS) {

			ag.performeCrossover();
			ag.mutation();
			ag.mergePopulations();

			Collections.sort(ag.population);

			System.out.println(ag.avgGenerationFitness() + "\t" + ag.population.get(0).fitness);

			generation++;

		}
		return ag.population.get(0);
	}

	private void margePopulationWithInvitro(int N) {

		for (Solution solution : offspring)
			this.population.add(new Solution(solution.chromosome));

		/* Execution of In Vitro Fertilization Module */
		Solution inv_solution = IVF.performInVitro(IVF.collect(this.population, N), IVF.EAR_P);

		if (inv_solution.fitness > this.population.get(0).fitness) {
			this.population.add(inv_solution);
		}
		/* Execution of In Vitro Fertilization Module */

		Collections.sort(this.population);

		for (int i = this.population.size() - 1; i >= this.POPULATION_SIZE; i--)
			this.population.remove(i);

		this.offspring.clear();

	}
	
	private void mergePopulations() {

		for (int i = POPULATION_SIZE-1; i >= ELITISM_NUMBER; i--) 
			this.population.remove(i);
		
		Collections.sort(this.offspring);
		
		for (int i = ELITISM_NUMBER, j = 0; i < POPULATION_SIZE; i++, j++)
			this.population.add(new Solution(offspring.get(j).chromosome));
		
		this.offspring.clear();
		
	}

	private void mergePopulationsByRank() {

		for (Solution solution : offspring)
			this.population.add(new Solution(solution.chromosome));

		Collections.sort(this.population);

		for (int i = this.population.size() - 1; i >= this.POPULATION_SIZE; i--)
			this.population.remove(i);

		this.offspring.clear();

	}

	// initializing population
	public void initializePop() {

		for (int i = 0; i < POPULATION_SIZE; i++) {
			int[] individual = new int[CHROMOSOME_SIZE];
			for (int j = 0; j < individual.length; j++)
				individual[j] = r.nextInt(2);
			population.add(new Solution(individual));
		}

	}

	public void performeCrossover() {

		while (this.offspring.size() < POPULATION_SIZE) {

			int father_a = this.selection_op.getIndividualIndex(population);
			int father_b = this.selection_op.getIndividualIndex(population);

			if (r.nextDouble() < CROSSOVER_RATE) {
				ArrayList<Solution> sons = this.onePoint(population.get(father_a), population.get(father_b));
				this.offspring.add(sons.get(0));
				this.offspring.add(sons.get(1));
			} else {
				this.offspring.add(new Solution(population.get(father_a).chromosome));
				this.offspring.add(new Solution(population.get(father_b).chromosome));
			}
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
				if (r.nextFloat() <= MUTATION_RATE)
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

		double x = 0, y = 0;

		int bin_x[] = new int[s.chromosome.length / 2];
		int bin_y[] = new int[s.chromosome.length / 2];

		for (int i = 0; i < bin_y.length; i++) {
			bin_x[i] = s.chromosome[i];
			bin_y[i] = s.chromosome[i + bin_y.length];
		}

		for (int i = 0; i < bin_x.length; i++)
			x += bin_x[i] * Math.pow(2, bin_x.length - i - 1);

		x = -100 + x * (200) / (Math.pow(2, bin_x.length) - 1);

		for (int i = 0; i < bin_y.length; i++)
			y += bin_y[i] * Math.pow(2, bin_y.length - i - 1);

		y = -100 + y * (200) / (Math.pow(2, bin_y.length) - 1);

		System.out.println("x: " + x + ", y: " + x);
	}

	public float avgGenerationFitness() {
		float avg = 0;
		for (Solution solution : population)
			avg += solution.fitness;
		return avg / population.size();
	}
}
