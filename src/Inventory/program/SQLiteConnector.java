package Inventory.program;

import java.sql.*;

import org.sqlite.SQLiteConfig;

public class SQLiteConnector {
	private static SQLiteConnector instance = null;
	private Connection c = null;
	
	private SQLiteConnector() {
	    try {
	    	  SQLiteConfig config = new SQLiteConfig();  
	          config.enforceForeignKeys(true);
	          
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:SuperLeeDB_INT.db", config.toProperties());
//		      c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\hod\\Desktop\\assignments\\����� ������\\DB\\superlee.db");
		      c.setAutoCommit(true);
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
	}
	
	public static SQLiteConnector getInstance() {
		if (instance == null)
			instance = new SQLiteConnector();
		return instance;
	}
	
	public Connection getConnection() {
		return c;
	}
	
	public void CloseConnection() {
		try {
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void runUnreturnedQuery(String query) throws SQLException {
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		
		stmt = c.createStatement();
		
	    stmt.executeUpdate(query);
	    stmt.close();
	    c.commit();
	}
}
