package application.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLliteSample {
	public SQLliteSample(){
		 // load the sqlite-JDBC driver using the current class loader
	      try {
			Class.forName("org.sqlite.JDBC");
			  
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      Connection connection = null;
	      

	    	  try {
				connection = DriverManager.getConnection("jdbc:sqlite:sample.de");
		        Statement statement = connection.createStatement();
		         statement.setQueryTimeout(30);  // set timeout to 30 sec.


		         statement.executeUpdate("DROP TABLE IF EXISTS record");
		         statement.executeUpdate("CREATE TABLE record (Recordname STRING not null, Searchterm1 STRING,"
		         		+ " Searchterm2 STRING, Searchterm3 STRING, Searchterm4 STRING,"
		         		+ " Searchterm5 STRING)");
		         /*
		         int ids [] = {1,2,3,4,5};
		         String names [] = {"Peter","Pallar","William","Paul","James Bond"};

		         for(int i=0;i<ids.length;i++){
		              statement.executeUpdate("INSERT INTO record values(' "+ids[i]+"', '"+names[i]+"')");   
		         }
		         
		         //statement.executeUpdate("UPDATE person SET name='Peter' WHERE id='1'");
		         //statement.executeUpdate("DELETE FROM person WHERE id='1'");

		           ResultSet resultSet = statement.executeQuery("SELECT * from record");
		           while(resultSet.next())
		           {
		              // iterate & read the result set
		              System.out.println("name = " + resultSet.getString("name"));
		              System.out.println("id = " + resultSet.getInt("id"));
		           }
		          */
				
			} catch (SQLException e) {System.err.println(e.getMessage()); }       
	          finally {         
	              try {
	                    if(connection != null)
	                       connection.close();
	                    }
	              catch(SQLException e) {  // Use SQLException class instead.          
	                 System.err.println(e); 
	               }
			}
	      
	      
	}

}