package ic.inf.br;

public class Solution implements Comparable<Solution>{

	public int chromosome_size;
	public int[] chromosome;
	public float fitness;

	public Solution(int chromossome_size) {
		this.chromosome_size = chromossome_size;
	}

	public Solution(int[] bits) {
		this.chromosome_size = bits.length;
		this.chromosome = bits;
		this.fitness = evaluate(chromosome);
	}

	// fitness function
	public static float evaluate(int[] bits) {
		float fitness = 0;

		for (int i = 0; i < bits.length; i++)
			fitness += bits[i] * Math.pow(2, bits.length - i - 1);

		return fitness * fitness;
	}

	
	public int compareTo(Solution o) {
		
		if(this.fitness > o.fitness)
			return -1;
		if(this.fitness < o.fitness)
			return 1;
		
		return 0;
	}

	public void evaluate() {
		this.fitness = evaluate(this.chromosome);
	}

}
