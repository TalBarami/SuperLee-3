package Store;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

public class SQLiteConnector {
	private Connection c;
	private static SQLiteConnector instance = null;
	
	private SQLiteConnector() {
		c = null;
		startConnection();
	}
	
	public static SQLiteConnector getInstance() {
		if (instance == null)
			instance = new SQLiteConnector();
		return instance;
	}

	public void startConnection(){
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			config.enforceForeignKeys(true);
			c = DriverManager.getConnection(
					"jdbc:sqlite:SuperLeeDB.db",
					config.toProperties());
			c.setAutoCommit(true);
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	public void endConnection(){
		try {
			c.close();
		} catch (SQLException e) {
			System.exit(0);
		}
	}
	
	public Connection getConnection(){
		return c;
	}
}
