package io;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * sed 's/,/@/g' glove.840B.300d.txt > output.txt sed 's/ /,/g' output.txt >
 * glove.840B.300d.txt.csv
 * 
 * SELECT count(*) FROM db_word2vec.tbl_word2vec; CREATE INDEX word_index ON
 * tbl_word2vec(word) SELECT * FROM db_word2vec.tbl_word2vec where word like
 * '%born%'; UPDATE tbl_word2vec SET word = SUBSTRING(word, 2) delete useless '
 * from beginning of all words and vectors UPDATE tbl_word2vec SET vector =
 * SUBSTRING(vector, 2)
 * 
 * UPDATE tbl_word2vec SET word = substring(word,1,length(word)-1); UPDATE
 * tbl_word2vec SET vector = substring(vector,1,length(vector)-1);
 * 
 * SELECT * FROM db_word2vec.tbl_word2vec where word = 'born';
 * 
 * 
 * 
 * @author morteza
 *
 */

public class FillMySQL {
	Connection connection = null;

	public FillMySQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/db_word2vec", "root",
					"temp12345678");

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	private void loadVectors() throws Exception {
		Scanner sc = new Scanner(new FileReader(
				"/Users/morteza/Downloads/word2vec/glove.840B.300d.txt.csv"));

		int i = 0;
		while (sc.hasNext()) {
			String s = sc.nextLine();
			i++;
			int idx = s.indexOf(',');

			String word = s.substring(0, idx);
			if (word.length() > 240)
				word = word.substring(0, 240);
			// if (i == 10)
			// System.out.println(i +" "+ wv.word);
			String vector = s.substring(idx + 1);
			WordVector wv = new WordVector(word, vector);

			mysqlInsert(wv);
		}
	}

	public static void main(String[] args) throws Exception {
		// Wor2Vector wor2Vector = new Wor2Vector();
		// wor2Vector.loadVectors();
		// wor2Vector.connection.close();

	}

	private void mysqlInsert(WordVector wv) throws Exception {

		if (connection != null) {
			String sql;

			sql = "INSERT INTO tbl_word2vec(word,vector) VALUES(?,?);";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, "'" + wv.getWord() + "'");
			stmt.setString(2, "\'" + wv.getVectorArray() + "'");

			stmt.executeUpdate();
			stmt.close();
		} else {
			System.out.println("Failed to make connection!");
		}
	}
}
