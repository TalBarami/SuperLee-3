package Inventory.tests;

import static org.junit.Assert.*;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale.Category;

import junit.framework.Assert;

import org.junit.Test;

import Inventory.dbHandlers.CategoryHandler;
import Inventory.dbHandlers.ProductHandler;
import Inventory.dbHandlers.ProductStockHandler;
import Inventory.dbHandlers.StockTicketHandler;
import Inventory.entities.ProductStock;
import Inventory.entities.StockTicket;
import Inventory.program.SQLiteConnector;

public class Tests {

	@Test
	public void testConnection() {
		Connection c = SQLiteConnector.getInstance().getConnection();
		boolean connect = c != null;
		assertEquals(true, connect);
	}

	@Test
	public void testCheckIfCategoryExists() {
		Inventory.entities.Category c = null;
		try {
			c = CategoryHandler.checkIfCategoryExists(0, 1, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(null, c);
	}
	
	@Test
	public void testCheckIfProductExists() {
		boolean c = true;
		try {
			c = ProductHandler.checkIfProductExists(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(false, c);
	}
	
	@Test
	public void testPrintAllProductInStock(){
		int result = 1;
		try {
			result = ProductStockHandler.printAllProductInStock(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(0, result);
	}
	
	@Test
	public void testCheckIfProductStockExists(){
		boolean result = true;
		try {
			result = ProductStockHandler.checkIfProductStockExists(0, "2016-04-01", 1, true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(false, result);
	}
	
	@Test
	public void testCheckIfThereEnoughInStock(){
		boolean result = true;
		try {
			result = ProductStockHandler.checkIfThereEnoughInStock(0,  "2016-04-01", 5);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(false, result);
	}
	
	@Test
	public void testCheckIfStockTicketExists(){
		boolean result = true;
		try {
			result = StockTicketHandler.checkIfStockTicketExists(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(false, result);
	}
	
	@Test
	public void testReturnStockTicketIdByDate(){
		int result = 1;
		try {
			result = StockTicketHandler.returnStockTicketIdByDate("2014");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(0, result);
	}
	
	@Test
	public void testStockTicketList(){
		Inventory.entities.StockTicket st = new StockTicket(0, "null");
		st.addProductToList(new Inventory.entities.ProductStock(0, 1, "null", true));
		
		assertEquals(1, st.get_items().size());
	}
	
	@Test
	public void testCategoryHeirarchy(){
		Inventory.entities.Category c1 = new Inventory.entities.Category(0, null, "1", 1);
		Inventory.entities.Category c2 = new Inventory.entities.Category(0, c1, "1", 1);
		
		assertEquals(c1, c2.get_parent_cat());
	}
}