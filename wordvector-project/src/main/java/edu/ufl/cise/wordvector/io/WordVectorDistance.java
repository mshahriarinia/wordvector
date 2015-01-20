package edu.ufl.cise.wordvector.io;

import java.util.Comparator;

public class WordVectorDistance implements Comparable<WordVectorDistance> {
	WordVector wordVector;
	double distance;
	double cossimDistance;

	public int compareTo(WordVectorDistance o) {
		int res = 0;
		if (distance - o.distance > 0)
			res = 1;
		else if (distance - o.distance < 0)
			res = -1;
		return res;
	}

	public static Comparator<WordVectorDistance> COMPARATOR = new Comparator<WordVectorDistance>() {
		public int compare(WordVectorDistance o1, WordVectorDistance o2) {
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
		return wordVector.getWord() + " " + LinearAlgebra.df.format(distance) + " \t cossim: "
				+ LinearAlgebra.df.format(cossimDistance);
	}
}
