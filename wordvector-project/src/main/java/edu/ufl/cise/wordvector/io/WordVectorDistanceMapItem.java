package edu.ufl.cise.wordvector.io;

public class WordVectorDistanceMapItem implements Comparable<WordVectorDistanceMapItem> {
	String word;
	double distance;

	public int compareTo(WordVectorDistanceMapItem o) {
		int res = 0;
		if (distance-o.distance > 0)
			res = 1;
		else if (distance-o.distance < 0)
			res = -1;
		return res;
	}
	
	@Override
	public String toString() {
		return word + " " + distance;
	}
}
