package ic.inf.br;

import java.util.ArrayList;
import java.util.Random;

public class BinaryTournament implements Selection {
	Random r = new Random();

	public int getIndividualIndex(ArrayList<Solution> pop) {
		int x1 = r.nextInt(pop.size());
		int x2 = r.nextInt(pop.size());
		return (x1 < x2) ? x1 : x2;
	}

}
