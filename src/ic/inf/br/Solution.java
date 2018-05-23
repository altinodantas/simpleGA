package ic.inf.br;

import java.util.Random;

public class Solution implements Comparable<Solution>{

	public int chromosome_size;
	public int[] chromosome;
	public float fitness;
	private static Random random;

	public Solution(int[] bits) {
		this.chromosome_size = bits.length;
		this.chromosome = bits.clone();
		this.fitness = (float) evaluate(chromosome);
	}

	// fitness function
	public static float evaluateOld(int[] bits) {
		float fitness = 0;

		for (int i = 0; i < bits.length; i++) {
			fitness += bits[i] * Math.pow(2, bits.length - i - 1);
		}

		return fitness;
	}

	
	public int compareTo(Solution o) {
		
		if(this.fitness > o.fitness) {
			return -1;
		}
		if(this.fitness < o.fitness) {
			return 1;
		}
		
		return 0;
	}

	public void evaluate() {
		this.fitness = (float) evaluate(this.chromosome);
	}
	
	public static double evaluate(int[] bits) {
		double x = 0, y = 0, xsqrdysqrd = 0;
		
		int bin_x[] = new int[bits.length/2]; int bin_y[] = new int[bits.length/2];
		
		for (int i = 0; i < bin_y.length; i++) {
			bin_x[i] = bits[i];
			bin_y[i] = bits[i + bin_y.length];
		}
		
		for (int i = 0; i < bin_x.length; i++) {
			x += bin_x[i] * Math.pow(2, bin_x.length - i - 1);
			y += bin_y[i] * Math.pow(2, bin_y.length - i - 1);
		}
		
		x = -100 + x * (200) / (Math.pow(2, bin_x.length) - 1);
		y = -100 + y * (200) / (Math.pow(2, bin_y.length) - 1);
		
		xsqrdysqrd = x * x + y * y;
		
		return ((0.5 - (Math.pow(Math.sin(  Math.sqrt(xsqrdysqrd)),2) - 0.5) / Math.pow((1 + 0.001 * xsqrdysqrd), 2)));
	}
	
}
