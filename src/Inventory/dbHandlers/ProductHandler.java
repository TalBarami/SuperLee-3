package Inventory.dbHandlers;

import java.sql.*;

import Inventory.program.SQLiteConnector;
import Inventory.entities.Category;
import Inventory.entities.ProductCatalog;

public class ProductHandler {
	public static void addProductCatalog(ProductCatalog product) throws SQLException {
		String sql = "INSERT INTO product (name,manufacture,min_amount,main_cat_id,sub_cat_id,ssub_cat_id) " +
				"VALUES ('"+product.get_name()+"', "+product.get_manufacture()+", "+product.get_minimal_amount()+", "+product.get_main_cat().get_id();

		if (product.get_sub_cat() != null)
			sql += ", " + product.get_sub_cat().get_id();
		else
			sql += ", NULL";
		if (product.get_ssub_cat() != null)
			sql += ", " + product.get_ssub_cat().get_id();
		else
			sql += ", NULL";

		sql += ");";

		SQLiteConnector.getInstance().runUnreturnedQuery(sql);
	}

	public static void printAllProductCatalog() throws SQLException
	{
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT product_id, name FROM product;");

		PrintResultSet(rs);
		stmt.close();
	}

	public static void printAllManufactures() throws SQLException
	{
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT * FROM Manufactures;");

		while (rs.next()) {
			int manuID = rs.getInt("ID");
			String name = rs.getString("name");

			System.out.print( "ID = " + manuID );
			System.out.println( ", NAME = " + name );
		}

		rs.close();
		stmt.close();
	}

	public static void PrintResultSet(ResultSet rs) throws SQLException  {
		while (rs.next()) {
			int productID = rs.getInt("product_id");
			String name = rs.getString("name");

			System.out.print( "ID = " + productID );
			System.out.println( ", NAME = " + name );
		}
		rs.close();
	}

	public static boolean checkIfProductExists(int prod_id) throws SQLException {
		boolean result = false;
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT * FROM product WHERE product_id=" + prod_id + ";");

		if (rs.next()) {
			result =true;
		}

		rs.close();
		stmt.close();

		return result;
	}

	public static ProductCatalog createProductCatalogByID(int prod_id) {
		ProductCatalog result = null;
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT * FROM product WHERE product_id=" + prod_id + ";");

			if (rs.next()) {
				int productID = rs.getInt("product_id");
				String name = rs.getString("name");
				int manufactureID = rs.getInt("manufacture");
				int minAmount = rs.getInt("min_amount");

				int mainCatID = rs.getInt("main_cat_id");
				int subCatID = rs.getInt("sub_cat_id");
				int ssubCatID = rs.getInt("ssub_cat_id");

				Category mainCat = CategoryHandler.checkIfCategoryExists(mainCatID, 1, null);
				Category subCat = CategoryHandler.checkIfCategoryExists(subCatID, 2, mainCat);
				Category ssubCat = CategoryHandler.checkIfCategoryExists(ssubCatID, 3, subCat);

				result = new ProductCatalog(productID, name, manufactureID, minAmount, mainCat, subCat, ssubCat);
			}

			rs.close();
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}

	public static boolean checkIfManufactureExists(int manu_id) throws SQLException {
		boolean result = false;
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT * FROM Manufactures WHERE ID=" + manu_id + ";");

		if (rs.next()) {
			result =true;
		}

		rs.close();
		stmt.close();

		return result;
	}

	public static String getManufactureNameByID(int manu_id) throws SQLException
	{
		String ans = "";
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT * FROM Manufacturers WHERE ID="+manu_id+";");

		if (rs.next()) {
			ans = rs.getString("name");
		}

		rs.close();
		stmt.close();

		return ans;
	}

	public static void updateExistingProduct(ProductCatalog product) throws SQLException {
		String sql = "UPDATE product SET name='"+product.get_name()+"', manufacture="+product.get_manufacture()+", min_amount="+product.get_minimal_amount()+", main_cat_id="+product.get_main_cat().get_id();

		if (product.get_sub_cat() != null)
			sql += ", sub_cat_id=" + product.get_sub_cat().get_id();
		else
			sql += ", sub_cat_id=NULL";
		if (product.get_ssub_cat() != null)
			sql += ", ssub_cat_id=" + product.get_ssub_cat().get_id();
		else
			sql += ", ssub_cat_id=NULL";

		sql += " WHERE product_id="+product.get_id()+";";

		SQLiteConnector.getInstance().runUnreturnedQuery(sql);
	}

	public static void deleteExistingProduct(int prod_id) throws SQLException {
		String sql = "DELETE FROM product WHERE product_id="+prod_id+";";
		SQLiteConnector.getInstance().runUnreturnedQuery(sql);
	}

}