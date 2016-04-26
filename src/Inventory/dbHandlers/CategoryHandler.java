package Inventory.dbHandlers;

import java.sql.*;

import Inventory.entities.Category;
import Inventory.program.SQLiteConnector;

public class CategoryHandler {
	public static void PrintAllMainCategories() throws SQLException {
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT cat_id, name FROM category WHERE level=1;");
	    
		PrintResultSet(rs);
	    stmt.close();
	}
	
	public static void PrintAllSubCategories(int id, int level) throws SQLException {
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT cat_id, name FROM category WHERE level="+level+" AND parent_id="+id+";");
	    
		PrintResultSet(rs);
	    stmt.close();
	}
	
	public static Category checkIfCategoryExists(int id, int level, Category parentCat) throws SQLException {
		Category result = null;
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		stmt = c.createStatement();
		if(parentCat == null)
			rs = stmt.executeQuery("SELECT * FROM category WHERE cat_id=" + id + " AND level=" + level + ";");
		else
			rs = stmt.executeQuery("SELECT * FROM category WHERE cat_id=" + id + " AND level=" + level + " AND parent_id="+parentCat.get_id()+";");
	    
	    if (rs.next()) {
	        String  name = rs.getString("name");
	        result = new Category(id, parentCat, name, level);
	    }
	    
	    rs.close();
	    stmt.close();
	    
	    return result;
	}
	
	private static void PrintResultSet(ResultSet rs) throws SQLException {
	    while (rs.next()) {
	          int cat_id = rs.getInt("cat_id");
	          String name = rs.getString("name");

	          System.out.print( "ID = " + cat_id );
	          System.out.println( ", NAME = " + name );
		    }
		    
		    rs.close();
	}

	public static void addNewCategory(String name, int i,Category fatherCategory) throws SQLException {
		String sql;
		if(fatherCategory == null)
			sql = "INSERT INTO category (name, level) " +"VALUES ('"+name+"', " + i+");";
		else
			sql = "INSERT INTO category (parent_id, name, level) " +"VALUES (" + fatherCategory.get_id() +  ", '"+name+"', " + i+");";

		SQLiteConnector.getInstance().runUnreturnedQuery(sql);
	}
	
	public static void updateCategoryName(Category category, String newName) throws SQLException {
	    String sql = "UPDATE category SET name='"+newName+"' WHERE cat_id="+category.get_id()+";";
	    SQLiteConnector.getInstance().runUnreturnedQuery(sql);
	}
	
	public static void DeleteCategory(int catID) throws SQLException {
	    String sql = "DELETE FROM category WHERE cat_id="+catID+";";
	    SQLiteConnector.getInstance().runUnreturnedQuery(sql);
	}
}
