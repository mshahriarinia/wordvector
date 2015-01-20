package edu.ufl.cise.wordvector.io;

public class WordVector {

	private String word;

	private float[] vectorArray;

	public WordVector(String word, String vector) {
		this.word = word;

		this.vectorArray = new float[LinearAlgebra.VECTOR_LENGTH];
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
		return LinearAlgebra.distance(getVectorArray(), wv.getVectorArray());
	}

	public double distance(float[] vectorArray) {
		return LinearAlgebra.distance(getVectorArray(), vectorArray);
	}

}
