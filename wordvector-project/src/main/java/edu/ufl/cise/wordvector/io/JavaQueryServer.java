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
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaQueryServer {

	public HashMap<String, WordVector> wvTable = new HashMap();
	public String[] keysList;
	private final int K = 30; // K as in top k
	private final int THRESHOLD = 5;

	public static void main(String[] args) throws Exception {
		System.out.println("Hi");

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

	public void loadVectors() throws Exception {
		String serverPath = "/home/msnia/zproject/workspaces/w2v/glove.840B.300d.txt.csv"; // sample.csv"; // glove.840B.300d.txt.csv";
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

			wvTable.put(wv.getWord(), wv);
		}
	}

	public void console() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String OPERATORS_PATTERN = "\\+|\\-";
		String queryStr = "";
		float[] res = new float[LinearAlgebra.VECTOR_LENGTH];

		System.out.print("> ");

		while ((queryStr = br.readLine()) != null && !queryStr.equals("quit")) {
			try {

				String[] operands = queryStr.split(OPERATORS_PATTERN);

				System.out.println("You entered : " + queryStr);
				if (operands.length > 0) { // query a string for its vector

					System.out.println();
					System.out.println("Step by step operations: ");

					List<WordVector> listOperandVectors = new LinkedList<WordVector>();

					WordVector tempWV = wvTable.get(operands[0]);
					res = tempWV.getVectorArray();
					listOperandVectors.add(tempWV);

					// print the first operand
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
						tempWV = wvTable.get(operands[i + 1]);
						listOperandVectors.add(tempWV);

						if (operandChar == '+') {
							System.out.println('+');
							res = LinearAlgebra.add(res, tempWV.getVectorArray());
						} else if (operandChar == '-') {
							System.out.println('-');
							res = LinearAlgebra.subtract(res, tempWV.getVectorArray());
						}

						System.out.println(operands[i + 1] + "\t" + Arrays.toString(tempWV.getVectorArray()));

						System.out.println("=====\t" + Arrays.toString(res));
						System.out.println("Result Length: " + LinearAlgebra.df.format(LinearAlgebra.norm(res)));
						

					}
					getKNN(res, listOperandVectors); // gets and prints them
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.print("> ");
		}
		System.out.println("Exitted");
	}

	private List<WordVectorDistance> getKNN(float[] vector, List<WordVector> listOperandVectors) {
		ArrayList<WordVectorDistance> listEuclidean = new ArrayList<WordVectorDistance>();
		ArrayList<WordVectorDistance> listCossim = new ArrayList<WordVectorDistance>();

		Iterator<String> keyIterator = wvTable.keySet().iterator();
		while (keyIterator.hasNext()) { // iterate over all items in table

			String key = keyIterator.next();
			WordVector keyWV = wvTable.get(key);

			WordVectorDistance wvdmi = new WordVectorDistance();
			wvdmi.wordVector = keyWV;
			wvdmi.distance = LinearAlgebra.distance(keyWV.getVectorArray(), vector);
			listEuclidean.add(wvdmi);

			// WordVectorDistance wvdmi2 = new WordVectorDistance();
			// wvdmi2.wordVector = keyWV;
			// wvdmi2.distance = LinearAlgebra.cossim(keyWV.getVectorArray(), vector);
			 wvdmi.cossimDistance = LinearAlgebra.cossim(keyWV.getVectorArray(), vector);
			 
			// listCossim.add(wvdmi2);

		}

		System.out.println("-------------------------- EUCLIDEAN");

		List<WordVectorDistance> topKEuclidean = com.google.common.collect.Ordering.natural().leastOf(listEuclidean, K);
		listEuclidean.sort(WordVectorDistance.COMPARATOR);

		// filter out very close terms to the query nodes themselves
		List<WordVectorDistance> toremove = new LinkedList<WordVectorDistance>();
		for (int i = 0; i < K; i++) {
			for (int j = 0; j < listOperandVectors.size(); j++) {
				WordVectorDistance aTopKwvd = topKEuclidean.get(i);
				WordVector anOperandwv = listOperandVectors.get(j);
				if (aTopKwvd.wordVector.distance(anOperandwv) < THRESHOLD)
					toremove.add(aTopKwvd);
			}
		}

		// topKEuclidean.removeAll(toremove);

		for (WordVectorDistance wvdmi : topKEuclidean) {
			if (!toremove.contains(wvdmi))
				System.out.println(wvdmi);
		}

		// System.out.println("----------------------------- COSSIM");
		//
		// List<WordVectorDistance> topKCossim =
		// com.google.common.collect.Ordering.natural().leastOf(listCossim, K);
		// listCossim.sort(WordVectorDistance.COMPARATOR);
		//
		// for (WordVectorDistance wvdmi : topKCossim) {
		// System.out.println(wvdmi);
		// }

		return topKEuclidean;
	}

}
