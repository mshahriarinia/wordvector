package edu.ufl.cise.wordvector.io;

import java.text.DecimalFormat;

public class LinearAlgebra {

	public static final int VECTOR_LENGTH = 300;

	public static final DecimalFormat df = new DecimalFormat("#.00");

	// public static final float[] ZERO = new float[VECTOR_LENGTH];

	public static double distance(float[] v1, float[] v2) {
		return norm(subtract(v1, v2));
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

	/**
	 * dot product fo two vectors
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	static double dot(float[] a, float[] b) {
		double sum = 0;

		for (int j = 0; j < VECTOR_LENGTH; j++) {
			sum += a[j] * b[j];
		}
		return sum;
	}

	/**
	 * Magnitude of a vector
	 * 
	 * @param a
	 * @return
	 */
	static double norm(float[] a) {
		double sum = 0;

		for (int j = 0; j < VECTOR_LENGTH; j++) {
			sum += a[j] * a[j];
		}
		return Math.sqrt(sum);
	}

	/**
	 * Cosine similarity
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	static double cossim(float[] a, float[] b) {
		return dot(a, b) / (norm(a) * norm(b));
	}

}
