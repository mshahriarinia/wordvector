package edu.ufl.cise.wordvector.io;

import java.util.Comparator;

public class WordVectorDistanceMapItem implements Comparable<WordVectorDistanceMapItem> {
	String word;
	double distance;

	public int compareTo(WordVectorDistanceMapItem o) {
		int res = 0;
		if (distance - o.distance > 0)
			res = 1;
		else if (distance - o.distance < 0)
			res = -1;
		return res;
	}

	public static Comparator<WordVectorDistanceMapItem> COMPARATOR = new Comparator<WordVectorDistanceMapItem>() {
		public int compare(WordVectorDistanceMapItem o1, WordVectorDistanceMapItem o2) {
			if (o1.distance > o2.distance)
				return 1;
			else if (o1.distance < o2.distance)
				return -1;
			else
				return 0;
		}
	};

	@Override
	public String toString() {
		return word + " " + LinearAlgebra.df.format(distance);
	}
}
