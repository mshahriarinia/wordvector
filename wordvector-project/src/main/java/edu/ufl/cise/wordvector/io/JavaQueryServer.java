package edu.ufl.cise.wordvector.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaQueryServer {

	private HashMap<String, float[]> wvTable = new HashMap();
	String[] keysList;

	public static void main(String[] args) throws Exception {

		JavaQueryServer jqs = new JavaQueryServer();

		Instant start, end;
		start = Instant.now();

		jqs.loadVectors();
		Set<String> keySet = jqs.wvTable.keySet();
		jqs.keysList = keySet.toArray(new String[keySet.size()]);

		// timer
		end = Instant.now();
		System.out.println("Initialization period: " + Duration.between(start, end)); // prints
																																									// PT1M3.553S
		start = Instant.now(); // reset timer

		System.out.println("Total item count:" + jqs.wvTable.size());
		jqs.console();

	}

	private void console() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String OPERATORS_PATTERN = "\\+|\\-";
		String queryStr = "";
		float[] res = new float[WordVector.VECTOR_LENGTH];

		System.out.print("> ");

		while ((queryStr = br.readLine()) != null && !queryStr.equals("quit")) {
			try {

				String[] operands = queryStr.split(OPERATORS_PATTERN);

				System.out.println("You entered : " + queryStr);
				if (operands.length > 0) { // query a string for its vector

					System.out.println();
					System.out.println("Step by step operations: ");
					res = wvTable.get(operands[0]);

					System.out.println(operands[0] + "\t" + Arrays.toString(res));

					ArrayList<Integer> positions = new ArrayList();
					Pattern p = Pattern.compile(OPERATORS_PATTERN); // insert
					// your pattern here
					Matcher m = p.matcher(queryStr);

					while (m.find()) {
						int operatorPosition = m.start();
						positions.add(operatorPosition);
					}

					for (int i = 0; i < positions.size(); i++) {
						char operandChar = queryStr.charAt(positions.get(i));
						// System.out.println(operandChar);
						float[] tempVar = wvTable.get(operands[i + 1]);
						if (operandChar == '+') {
							System.out.println('+');
							System.out.println(operands[i + 1] + "\t" + Arrays.toString(tempVar));
							res = WordVector.add(res, tempVar);
						} else if (operandChar == '-') {
							System.out.println('-');
							System.out.println(operands[i + 1] + "\t" + Arrays.toString(tempVar));
							res = WordVector.subtract(res, tempVar);
						}

						System.out.println("=====\t" + Arrays.toString(res));
						List<WordVectorDistanceMapItem> knnlist = getKNN(res);
						for (WordVectorDistanceMapItem wvdmi : knnlist) {
							System.out.println(wvdmi);
						}

					}

					// System.out.println(operands[0]);
					// System.out.println(operands[1]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.print("> ");
		}
		System.out.println("Exitted");
	}

	private void loadVectors() throws Exception {
		String serverPath = "/home/msnia/zproject/workspaces/w2v/glove.840B.300d.txt.csv";
		String localPath = "/Users/morteza/Downloads/word2vec/sample.csv";
		String path = null;
		File f = new File(localPath);
		if (f.exists())
			path = localPath;
		else
			path = serverPath;

		Scanner sc = new Scanner(new FileReader(path));

		while (sc.hasNext()) {
			String s = sc.nextLine();
			int idx = s.indexOf(',');

			String word = s.substring(0, idx).trim();
			if (word.length() > 240)
				word = word.substring(0, 240);
			// if (i == 10)
			// System.out.println(i +" "+ wv.word);
			String vector = s.substring(idx + 1);
			WordVector wv = new WordVector(word, vector);

			wvTable.put(wv.getWord(), wv.getVectorArray());
		}
	}

	private List<WordVectorDistanceMapItem> getKNN(float[] vector) {
		ArrayList<WordVectorDistanceMapItem> list = new ArrayList<WordVectorDistanceMapItem>();

		Iterator<String> keyIterator = wvTable.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			WordVectorDistanceMapItem wvdmi = new WordVectorDistanceMapItem();
			wvdmi.word = key;
			wvdmi.distance = WordVector.distance(wvTable.get(key), vector);
			list.add(wvdmi);
		}

		List<WordVectorDistanceMapItem> l = com.google.common.collect.Ordering.natural().leastOf(list, 10);

		return l;
	}

}
