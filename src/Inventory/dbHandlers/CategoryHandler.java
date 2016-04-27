package Inventory.dbHandlers;

import java.sql.*;

import Inventory.entities.Category;

public class CategoryHandler {
	private Store.SQLiteConnector connector;
	private Connection c;


	public CategoryHandler(){
		connector = Store.SQLiteConnector.getInstance();
		c = connector.getConnection();
	}

	public void PrintAllMainCategories() throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;

		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT cat_id, name FROM category WHERE level=1;");

		PrintResultSet(rs);
		stmt.close();
	}

	public void PrintAllSubCategories(int id, int level) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;

		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT cat_id, name FROM category WHERE level="+level+" AND parent_id="+id+";");

		PrintResultSet(rs);
		stmt.close();
	}

	public Category checkIfCategoryExists(int id, int level, Category parentCat) throws SQLException {
		Category result = null;
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

	private void PrintResultSet(ResultSet rs) throws SQLException {
		while (rs.next()) {
			int cat_id = rs.getInt("cat_id");
			String name = rs.getString("name");

			System.out.print( "ID = " + cat_id );
			System.out.println( ", NAME = " + name );
		}

		rs.close();
	}

	public void addNewCategory(String name, int i,Category fatherCategory) throws SQLException {
		String sql;
		if(fatherCategory == null)
			sql = "INSERT INTO category (name, level) " +"VALUES ('"+name+"', " + i+");";
		else
			sql = "INSERT INTO category (parent_id, name, level) " +"VALUES (" + fatherCategory.get_id() +  ", '"+name+"', " + i+");";

		connector.runUnreturnedQuery(sql);
	}

	public void updateCategoryName(Category category, String newName) throws SQLException {
		String sql = "UPDATE category SET name='"+newName+"' WHERE cat_id="+category.get_id()+";";
		connector.runUnreturnedQuery(sql);
	}

	public void DeleteCategory(int catID) throws SQLException {
		String sql = "DELETE FROM category WHERE cat_id="+catID+";";
		connector.runUnreturnedQuery(sql);
	}
}
