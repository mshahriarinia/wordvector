package snippets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CodeSnippets {

	private void mysqlSample() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}

		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/db_word2vec", "root",
					"temp12345678");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");

			//
			//
			//
			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			Statement stmt = null;
			stmt = connection.createStatement();
			String sql;
			sql = "SELECT * FROM tbl_word2vec";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("idtbl_word2vec");
				String word = rs.getString("word");
				String vector = rs.getString("vector");

				// Display values
				System.out.print(id);
				System.out.print(word);
				System.out.print(vector);
			}
			System.out.println();
			// STEP 6: Clean-up environment
			rs.close();
			//
			//
			//
			sql = "INSERT INTO tbl_word2vec " + "VALUES (NULL, 'Zara', 'Ali')";
			stmt.executeUpdate(sql);
			stmt.close();

			connection.close();
		} else {
			System.out.println("Failed to make connection!");
		}
	}
}
