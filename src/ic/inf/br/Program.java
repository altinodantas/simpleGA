package ic.inf.br;

public class Program {
	public static void main(String[] args) {
			
		GeneticAlgorithm ga = new GeneticAlgorithm();
		
		ga.setCHROMOSOME_SIZE(44);
		ga.setMAX_GENERATIONS(200);
		ga.setPOPULATION_SIZE(200);
		ga.setCROSSOVER_RATE(0.65f);
		ga.setMUTATION_RATE(0.05f);
		ga.setELITISM_NUMBER(1);
		ga.setTYPE_OF_GEN_MERGE(GeneticAlgorithm.NEXT_GEN_RANK);
		ga.setSelection_op(new BinaryTournament());
		
		ga.setPRINT_FINAL(true);
		
		ga.solve();
		
	}
	

}
