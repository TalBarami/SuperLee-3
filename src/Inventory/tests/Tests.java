package Inventory.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import Inventory.entities.Category;

import org.junit.Before;
import org.junit.Test;
import Inventory.dbHandlers.CategoryHandler;
import Inventory.dbHandlers.ProductHandler;
import Inventory.dbHandlers.ProductStockHandler;
import Inventory.dbHandlers.StockTicketHandler;
import Inventory.entities.ProductStock;
import Inventory.entities.StockTicket;

public class Tests {

	private CategoryHandler catHdr;
	private ProductHandler proHdr;
	private ProductStockHandler proStkHdr;
	private StockTicketHandler stkTktHdr;

	@Before
	public void setUp() throws Exception {
		catHdr = new CategoryHandler();
		proHdr = new ProductHandler();
		proStkHdr = new ProductStockHandler();
		stkTktHdr = new StockTicketHandler();
	}


	@Test
	public void testConnection() {
		Connection c = Store.SQLiteConnector.getInstance().getConnection();
		boolean connect = c != null;
		assertEquals(true, connect);
	}

	@Test
	public void testCheckIfCategoryExists() {
		Category c = null;
		try {
			c = catHdr.checkIfCategoryExists(0, 1, null);
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
			c = proHdr.checkIfProductExists(0);
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
			result = proStkHdr.printAllProductInStock(0);
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
			result = proStkHdr.checkIfProductStockExists(0, "2016-04-01", 1, true);
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
			result = proStkHdr.checkIfThereEnoughInStock(0,  "2016-04-01", 5);
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
			result = stkTktHdr.checkIfStockTicketExists(0);
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
			result = stkTktHdr.returnStockTicketIdByDate("2014");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(0, result);
	}

	@Test
	public void testStockTicketList(){
		StockTicket st = new StockTicket(0, "null");
		st.addProductToList(new ProductStock(0, 1, "null", true));

		assertEquals(1, st.get_items().size());
	}

	@Test
	public void testCategoryHeirarchy(){
		Category c1 = new Category(0, null, "1", 1);
		Category c2 = new Category(0, c1, "1", 1);

		assertEquals(c1, c2.get_parent_cat());
	}
}