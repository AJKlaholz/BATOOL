package application.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//Erstelle SQLLite Datenbank mit den Spalten Recordname und Searchterm 1-5
public class CreateRecordDB {
	public CreateRecordDB() {

		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection("jdbc:sqlite:recordTable");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			statement.executeUpdate("DROP TABLE IF EXISTS recordTable");
			statement.executeUpdate("CREATE TABLE recordTable (Recordname STRING not null, Searchterm1 STRING,"
					+ " Searchterm2 STRING, Searchterm3 STRING, Searchterm4 STRING," + " Searchterm5 STRING)");

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) { 
				System.err.println(e);
			}
		}

	}

}