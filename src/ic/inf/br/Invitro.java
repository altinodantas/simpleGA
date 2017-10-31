package ic.inf.br;

import java.util.ArrayList;
import java.util.Random;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;

public class Invitro {

	public static final int AR = 1;
	public static final int EAR_P = 2;
	public static final int EAR_T = 3;
	public static final int EAR_N = 4;

	private int CHROMOSOME_SIZE = 44;
	private Random r = new Random();
	private ArrayList<Solution> ivf_offspring = new ArrayList<>();

	public Invitro(int chromosome_size) {
		this.CHROMOSOME_SIZE = chromosome_size;
	}

	public Solution performInVitro(ArrayList<Solution> solutions, int ivf_operator) {

		switch (ivf_operator) {
		case 1:
			return AR(solutions);
		case 2:
			return EAR_P(solutions);
		case 3:
			return EAR_T(solutions);
		case 4:
			return EAR_N(solutions);
		default:
			break;
		}
		return null;
	}

	private Solution AR(ArrayList<Solution> solutions) {

		Solution father = solutions.get(0);
		solutions.remove(0);

		for (Solution solution : solutions) {

			ArrayList<Solution> sons = this.onePoint(solution, father);

			this.ivf_offspring.add(sons.get(0));
			this.ivf_offspring.add(sons.get(1));

		}

		for (Solution solution : this.ivf_offspring) {
			if (solution.fitness > father.fitness) {
				father.chromosome = solution.chromosome.clone();
				father.evaluate();
			}
		}

		this.ivf_offspring.clear();
		return father;
	}

	private Solution EAR_N(ArrayList<Solution> solutions) {

		Solution best = solutions.get(0);
		Solution father = solutions.get(0);
		solutions.remove(0);
		int count = 0;
		do {

			int n_new_mothers = solutions.size() / 2;

			for (int i = solutions.size() - 1; i >= n_new_mothers; i--) {
				solutions.remove(i);
			}

			for (int i = 0; i < n_new_mothers; i++) {
				solutions.add(randomMother());
			}

			for (Solution solution : solutions) {
				ArrayList<Solution> sons = this.onePoint(solution, father);
				this.ivf_offspring.add(sons.get(0));
				this.ivf_offspring.add(sons.get(1));
			}

			for (Solution solution : this.ivf_offspring) {
				if (solution.fitness > father.fitness) {
					best = new Solution(solution.chromosome);
				}
			}

			this.ivf_offspring.clear();

			if (best.fitness > father.fitness) {
				father = best;
			} else {
				break;
			}
			System.out.println(count++);
		} while (true);

		return father;
	}

	private Solution EAR_T(ArrayList<Solution> solutions) {

		Solution best = solutions.get(0);
		Solution father = solutions.get(0);
		solutions.remove(0);

		do {

			int n_new_mothers = solutions.size() / 2;

			for (int i = n_new_mothers; i < solutions.size(); i++) {
				this.mutateFull(solutions.get(i));
			}

			for (Solution solution : solutions) {
				ArrayList<Solution> sons = this.onePoint(solution, father);
				this.ivf_offspring.add(sons.get(0));
				this.ivf_offspring.add(sons.get(1));
			}

			for (Solution solution : this.ivf_offspring) {
				if (solution.fitness > father.fitness) {
					best = new Solution(solution.chromosome);
				}
			}

			this.ivf_offspring.clear();

			if (best.fitness > father.fitness) {
				father = best;
			} else {
				break;
			}

		} while (true);

		return father;

	}

	private Solution EAR_P(ArrayList<Solution> solutions) {

		Solution best = solutions.get(0);
		Solution father = solutions.get(0);
		solutions.remove(0);

		do {
			int n_new_mothers = solutions.size() / 2;
			int part = r.nextInt(2);

			for (int i = n_new_mothers; i < solutions.size(); i++) {
				this.mutatePart(solutions.get(i), part);
			}

			for (Solution solution : solutions) {
				ArrayList<Solution> sons = this.onePoint(solution, father);
				this.ivf_offspring.add(sons.get(0));
				this.ivf_offspring.add(sons.get(1));
			}

			for (Solution solution : this.ivf_offspring) {
				if (solution.fitness > father.fitness) {
					best = new Solution(solution.chromosome);
				}
			}

			this.ivf_offspring.clear();

			if (best.fitness > father.fitness) {
				father = best;
			} else {
				break;
			}

		} while (true);

		return father;
	}

	private void mutateFull(Solution solution) {

		for (int i = 0; i < solution.chromosome_size; i++)
			solution.chromosome[i] = (solution.chromosome[i] == 0) ? 1 : 0;

		solution.evaluate();

	}

	private void mutatePart(Solution solution, int part) {

		if (part == 0) {
			for (int i = 0; i < solution.chromosome_size / 2; i++)
				solution.chromosome[i] = (solution.chromosome[i] == 0) ? 1 : 0;

		} else {
			for (int i = solution.chromosome_size / 2; i < solution.chromosome_size; i++)
				solution.chromosome[i] = (solution.chromosome[i] == 0) ? 1 : 0;
		}

		solution.evaluate();

	}

	public ArrayList<Solution> collect(ArrayList<Solution> host_population, int amount) {
		ArrayList<Solution> partial_population = new ArrayList<>();

		for (int i = 0; i < amount; i++) {
			partial_population.add(new Solution(host_population.get(i).chromosome));
		}

		return partial_population;

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

	public Solution randomMother() {

		int[] individual = new int[CHROMOSOME_SIZE];

		for (int j = 0; j < individual.length; j++)
			individual[j] = r.nextInt(2);

		return new Solution(individual);
	}
}
