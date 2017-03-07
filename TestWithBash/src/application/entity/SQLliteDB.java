package application.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//Erstelle SQLLite Datenbank mit den Spalten Recordname und Searchterm 1-5
public class SQLliteDB {
	public SQLliteDB() {

		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection("jdbc:sqlite:recordDB");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			statement.executeUpdate("DROP TABLE IF EXISTS record");
			statement.executeUpdate("CREATE TABLE record (Recordname STRING not null, Searchterm1 STRING,"
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