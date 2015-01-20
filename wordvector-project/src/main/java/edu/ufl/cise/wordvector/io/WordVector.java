package edu.ufl.cise.wordvector.io;

public class WordVector {

	private String word;

	private float[] vectorArray;

	public static final int VECTOR_LENGTH = 300;

	public WordVector(String word, String vector) {
		this.word = word;

		this.vectorArray = new float[VECTOR_LENGTH];
		String[] arr = vector.split(",");
		for (int i = 0; i < this.vectorArray.length; i++) {
			this.vectorArray[i] = Float.parseFloat(arr[i]);
		}
	}

	public WordVector(String word, float[] vectorArr) {
		this.word = word;
		this.vectorArray = vectorArr;
	}

	public float[] getVectorArray() {
		return vectorArray;
	}

	public String getWord() {
		return word;
	}

	@Override
	public String toString() {
		return word + " " + vectorArray[0];
	}

	public double distance(WordVector wv) {
		return distance(getVectorArray(), wv.getVectorArray());
	}

	public double distance(float[] vectorArray) {
		return distance(getVectorArray(), vectorArray);
	}

	public static double distance(float[] v1, float[] v2) {
		double sum = 0;

		for (int i = 0; i < VECTOR_LENGTH; i++) {
			sum += Math.pow(v1[i] - v2[i], 2);
		}
		return sum;
	}

	public static float[] subtract(float[] v1, float[] v2) {
		float[] res = new float[VECTOR_LENGTH];
		for (int i = 0; i < VECTOR_LENGTH; i++) {
			res[i] = v1[i] - v2[i];
		}
		return res;
	}

	public static float[] add(float[] v1, float[] v2) {
		float[] res = new float[VECTOR_LENGTH];
		for (int i = 0; i < VECTOR_LENGTH; i++) {
			res[i] = v1[i] + v2[i];
		}
		return res;
	}
}
