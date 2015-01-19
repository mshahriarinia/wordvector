package io;

public class WordVectorDistanceMapItem implements Comparable {
	String word;
	double distance;

	@Override
	public int compareTo(Object o) {
		int res = 0;
		if (distance > 0)
			res = 1;
		else if (distance < 0)
			res = -1;
		return res;
	}
	
	@Override
	public String toString() {
		return word + " " + distance;
	}
}
