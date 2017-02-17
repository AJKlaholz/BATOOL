package application.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RecordToDB {
	private Connection dbConnection;

	public ArrayList<String> pullRecordFromDb(String s) {

		ArrayList<String> tmp = new ArrayList<String>();
		try {
			dbConnection = DriverManager.getConnection("jdbc:sqlite:sample.de");
			Statement preparedStatement = dbConnection.createStatement();

			ResultSet selectAllRecords = preparedStatement
					.executeQuery("SELECT * FROM record WHERE Recordname='" + s + "'");

			if (selectAllRecords.next()) {
				tmp.add(selectAllRecords.getString("Recordname"));
				for (int i = 1; i <= 5; i++) {
					tmp.add(selectAllRecords.getString("Searchterm" + i));
					System.out.println(selectAllRecords.getString("Searchterm" + i));
				}

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

		}

		if (dbConnection != null) {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return tmp;

	}

	public void pushRecordToDb(ArrayList<String> rsql) {

		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO record"
				+ "(Recordname, Searchterm1, Searchterm2, Searchterm3, Searchterm4, Searchterm5) VALUES"
				+ "(?,?,?,?,?,?)";

		try {
			dbConnection = DriverManager.getConnection("jdbc:sqlite:sample.de");
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			for (int i = 1; i <= rsql.size(); i++) {
				preparedStatement.setString(i, rsql.get(i - 1));
				System.out.println(rsql.get(i - 1));
			}
			// execute insert SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Record was insert into DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	public void deleteRecordFromDb(String s) {
		// DELETE RECORD FROM DATABASE

		Statement preparedStatement = null;

		String deleteFromTableSQL = "DELETE FROM record WHERE Recordname='" + s + "'";

		try {
			dbConnection = DriverManager.getConnection("jdbc:sqlite:sample.de");
			preparedStatement = dbConnection.createStatement();

			// execute insert SQL stetement
			preparedStatement.execute(deleteFromTableSQL);

			System.out.println("Record was deleted!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (dbConnection != null) {
				try {
					dbConnection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public ArrayList<String> pullAllRecordnamesFromDb() {
		ArrayList<String> ard = new ArrayList<String>();

		try {
			dbConnection = DriverManager.getConnection("jdbc:sqlite:sample.de");
			Statement preparedStatement = dbConnection.createStatement();
			ResultSet selectAllRecords = preparedStatement.executeQuery("SELECT Recordname From record");
			while (selectAllRecords.next()) {

				ard.add((selectAllRecords.getString("Recordname")));

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

		}

		if (dbConnection != null) {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ard;
	}
}
