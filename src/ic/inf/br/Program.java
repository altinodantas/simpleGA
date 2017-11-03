package ic.inf.br;

public class Program {
	public static void main(String[] args) {
			
		GeneticAlgorithm ga = new GeneticAlgorithm();
		
		ga.setCHROMOSOME_SIZE(44);
		ga.setMAX_GENERATIONS(20);
		ga.setPOPULATION_SIZE(100);
		ga.setCROSSOVER_RATE(0.60f);
		ga.setMUTATION_RATE(0.1f);
		ga.setELITISM_NUMBER(1);
		ga.setTYPE_OF_GEN_MERGE(GeneticAlgorithm.NEXT_GEN_RANK);
		ga.setSelection_op(new Roulette());
		
		ga.setPRINT_FINAL(true);
		
		ga.solve();
		
	}
	

}
