package test;

/**
 * tests if we can store whole size of data in memory before we d any fine
 * tuning on actual code
 * 
 * @author morteza
 *
 */
public class TestDataFitInMemory {

	public static void main(String[] args) {
		int max = 3000000;
		String[] words = new String[max];
		System.out.println("words done");
		double[][] vectors = new double[300][max];

		System.out.println("vectors allocated");
		for (int i = 0; i < vectors.length; i++) {
			for (int j = 0; j < vectors[0].length; j++) {
				vectors[i][j] = 1;
			}
		}

		for (int i = 0; i < words.length; i++) {
			words[i] = "as";
		}
	}

}
