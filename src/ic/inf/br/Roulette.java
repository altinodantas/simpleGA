package ic.inf.br;

import java.util.ArrayList;
import java.util.Random;

public class Roulette implements Selection {
	Random r = new Random();

	public int getIndividualIndex(ArrayList<Solution> pop) {
		int index = 0;

		// sum of all fitness
		float sum = 0;
		
		for (Solution solution : pop)
			sum += solution.fitness;

		float threshold = (float) (r.nextDouble() * sum);

		float a_sum = 0;
		
		for (int i = 0; i < pop.size(); i++) {
			a_sum += pop.get(i).fitness;
			
			if (a_sum >= threshold) {
				index = i;
				break;
			}
		}
		return index;
	}

}
