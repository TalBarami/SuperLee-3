package Inventory.dbHandlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import Inventory.entities.ProductCatalog;
import Inventory.program.SQLiteConnector;
import Inventory.entities.ProductStock;

public class ProductStockHandler {
	private static void PrintResultSet(ResultSet rs) throws SQLException  {
		while (rs.next()) {
			int productID = rs.getInt("product_id");
			String date = rs.getString("date_of_exp");
			int amount = rs.getInt("amount");
			boolean is_valid = rs.getBoolean("is_valid");

			System.out.print( "ID = " + productID );
			System.out.print( ", DATE = " + date );
			System.out.print( ", AMOUNT = " + amount );
			System.out.println( ", IS_VALID = " + is_valid );
		}
		rs.close();
	}

	private static void PrintResultSetWithName(ResultSet rs) throws SQLException  {
		while (rs.next()) {
			int productID = rs.getInt("product_id");
			String date = rs.getString("date_of_exp");
			int amount = rs.getInt("amount");
			boolean is_valid = rs.getBoolean("is_valid");
			String name = rs.getString("name");

			System.out.print( "ID = " + productID );
			System.out.print( ", NAME = " + name );
			System.out.print( ", DATE = " + date );
			System.out.print( ", AMOUNT = " + amount );
			System.out.println( ", IS_VALID = " + is_valid );

		}
		rs.close();
	}

	//return 0 if there are no results
	public static int printAllProductInStock(int prod_id) throws SQLException {
		int ans = 1;
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT * FROM product_stock WHERE product_id="+prod_id+";");

		if(!rs.next())
			ans = 0;
		else {
			rs = stmt.executeQuery("SELECT * FROM product_stock WHERE product_id="+prod_id+";");
			PrintResultSet(rs);
		}

		stmt.close();
		return ans;
	}

	public static boolean checkIfProductStockExists(int prod_id, String date, int isValid, boolean checkIfValid) throws SQLException {
		boolean result = false;
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		stmt = c.createStatement();
		if(checkIfValid)
			rs = stmt.executeQuery("SELECT * FROM product_stock WHERE product_id=" + prod_id + " AND date_of_exp='"+date+"' AND is_valid=" + isValid + ";");
		else
			rs = stmt.executeQuery("SELECT * FROM product_stock WHERE product_id=" + prod_id + " AND date_of_exp='"+date+"';");

		if (rs.next()) {
			result =true;
		}

		rs.close();
		stmt.close();

		return result;
	}

	public static void addProductStock(ProductStock product) throws SQLException {
		String sql = "INSERT INTO product_stock (product_id,date_of_exp,amount,is_valid) " +
				"VALUES ("+product.get_product_id()+", '"+product.get_date_of_exp()+"', "+product.get_amount()+", 1);";

		SQLiteConnector.getInstance().runUnreturnedQuery(sql);
	}

	public static void UpdateProductStock(ProductStock productStock) throws SQLException {
		int is_valid = 0;

		if (productStock.get_is_valid())
			is_valid = 1;

		String sql = "UPDATE product_stock SET amount="+productStock.get_amount()+", is_valid="+is_valid+" WHERE product_id="+productStock.get_product_id()+" AND date_of_exp='"+productStock.get_date_of_exp()+"';";
		SQLiteConnector.getInstance().runUnreturnedQuery(sql);
	}


	public static boolean checkIfThereEnoughInStock(int id, String date, int amount) throws SQLException
	{
		boolean result = false;
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT * FROM product_stock WHERE product_id=" + id + " AND date_of_exp='"+date+"' AND amount>=" + amount + ";");

		if (rs.next()) {
			result =true;
		}

		rs.close();
		stmt.close();

		return result;
	}

	public static void printAllMinimalAmountProductStock() throws SQLException {
		ResultSet rs = GetAllMinimalAmountProducts();
		ProductHandler.PrintResultSet(rs);
	}

	public static ResultSet GetAllMinimalAmountProducts() throws SQLException {
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String currDate = dateFormat.format(cal.getTime());

		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT product_id, name, min_amount FROM product AS p WHERE min_amount > (SELECT DISTINCT SUM(amount) FROM product_stock AS ps WHERE ps.product_id=p.product_id AND ps.date_of_exp > '"+currDate+"' AND ps.is_valid=1 GROUP BY ps.product_ID) OR product_id NOT IN(SELECT product_id FROM product_stock AS pss WHERE product_id=pss.product_id AND pss.date_of_exp > '"+currDate+"' AND pss.is_valid=1);");

		stmt.close();
		return rs;
	}

	public static Map<ProductCatalog, Integer> GetAllAmountMissingOfProducts()
	{
		try {
			ResultSet rs = GetAllMinimalAmountProducts();
			Map<ProductCatalog, Integer> missingProducts = new HashMap<ProductCatalog, Integer>();
			while (rs.next()) {
				int id = rs.getInt("product_id");
				int min_amount = rs.getInt("min_amount");
				int sumAmount = getSumAmountByProductId(id);
				//we decided that in every order we would like to get twice from the required amount
				missingProducts.put(ProductHandler.createProductCatalogByID(id), (min_amount - sumAmount) * 2);
			}
			return missingProducts;
		}catch (SQLException e){
			e.printStackTrace();
		}
	}

	private static int getSumAmountByProductId(int id) throws SQLException {
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;


		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String currDate = dateFormat.format(cal.getTime());

		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT SUM(amount) AS amount FROM product_stock WHERE product_id="+id+" AND date_of_exp > '"+currDate+"' AND is_valid=1 GROUP BY product_id;");

		stmt.close();
		if(rs.next())
			return rs.getInt("amount");
		return 0;
	}

	public static void updateProductStockAmount(int productID, int amount) throws SQLException {
		String sql = "UPDATE product_stock SET amount=amount-" + amount + " WHERE product_id=" + productID + ";";
		SQLiteConnector.getInstance().runUnreturnedQuery(sql);
		// DeleteEmptyProductStock(productID);
	}

	public static void DeleteEmptyProductStock(int productID) throws SQLException {
		String sql = "DELETE FROM product_stock WHERE product_id="+productID+" AND amount=0;";
		SQLiteConnector.getInstance().runUnreturnedQuery(sql);
	}

	public static void printAllProductInStockByCategory(int cat_id, int level) throws SQLException {
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		stmt = c.createStatement();
		switch (level) {
			case 1:
				rs = stmt.executeQuery("SELECT * FROM product_stock JOIN product ON product_stock.product_id = product.product_id WHERE main_cat_id ="+cat_id+";");
				break;
			case 2:
				rs = stmt.executeQuery("SELECT * FROM product_stock JOIN product ON product_stock.product_id = product.product_id WHERE sub_cat_id ="+cat_id+";");
				break;

			case 3:
				rs = stmt.executeQuery("SELECT * FROM product_stock JOIN product ON product_stock.product_id = product.product_id WHERE ssub_cat_id ="+cat_id+";");
				break;
		}

		PrintResultSetWithName(rs);
		stmt.close();

	}

	public static void printAllNonValidProducts() throws SQLException {
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT * FROM product AS p JOIN product_stock AS ps ON p.product_id=ps.product_id WHERE is_valid=0;");

		PrintResultSetWithName(rs);
		stmt.close();

	}

	public static void printAllExpiredProducts() throws SQLException {
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String currDate = dateFormat.format(cal.getTime());

		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT * FROM product AS p JOIN product_stock AS ps ON p.product_id=ps.product_id WHERE ps.date_of_exp < '"+currDate+"';");

		PrintResultSetWithName(rs);
		stmt.close();

	}
}
