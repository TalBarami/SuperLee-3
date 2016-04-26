package Inventory.dbHandlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Inventory.entities.ProductStock;
import Inventory.entities.StockTicket;
import Inventory.program.SQLiteConnector;

public class StockTicketHandler {
	public static void printStockTicketItemByID(int stockTicketID) throws SQLException {
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT * FROM stock_ticket_items AS s JOIN product AS p ON s.product_id=p.product_id WHERE ticket_id="+stockTicketID+";");
	    
		while (rs.next()) {
		     int productID = rs.getInt("product_id");
		     String name = rs.getString("name");
		     String date_of_exp = rs.getString("date_of_exp");
		     int amount = rs.getInt("amount");
		     
		     System.out.print( "ID = " + productID );
		     System.out.print( ", NAME = " + name );
		     System.out.print( ", DATE OF EXP = " + date_of_exp );
		     System.out.println( ", AMOUNT = " + amount );
		}
		 
		rs.close();
	    stmt.close();
	}
	
	public static boolean checkIfStockTicketExists(int stockTicketID) throws SQLException {
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		boolean ans = false;
		
		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT * FROM stock_tickets WHERE ticket_id="+stockTicketID+";");
	    
		if (rs.next()) {
			ans = true;
		}
		 
		rs.close();
	    stmt.close();
	    
	    return ans;
	}
	
	public static int createNewStockTicket(String date) throws SQLException {
		String sql = "INSERT INTO stock_tickets (date) " +
		        "VALUES ('"+date+"');";
		SQLiteConnector.getInstance().runUnreturnedQuery(sql);
		
		return returnStockTicketIdByDate(date);
	}
	
	public static int returnStockTicketIdByDate(String date) throws SQLException {
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		int ticketID = 0;
		
		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT ticket_id FROM stock_tickets WHERE date='"+date+"' AND ticket_id>=(SELECT DISTINCT MAX(ticket_id) FROM stock_tickets);");
	    
		if (rs.next()) {
		     ticketID = rs.getInt("ticket_id");
		}
		 
		rs.close();
	    stmt.close();
	    
	    return ticketID;
	}
	
	public static void addStockTicketItems(StockTicket ticket) throws SQLException {
		int ticket_id = ticket.get_id();
		
		for (ProductStock product : ticket.get_items()) {
			addSingleStockItemToTicket(ticket_id, product);
			ProductStockHandler.updateProductStockAmount(product.get_product_id(), product.get_amount());
		}
	}
	
	private static void addSingleStockItemToTicket(int ticketID, ProductStock product) throws SQLException {
		String sql = "INSERT INTO stock_ticket_items (ticket_id,product_id,date_of_exp,amount) " +
		        "VALUES ("+ticketID+", "+product.get_product_id()+", '"+product.get_date_of_exp()+"',"+product.get_amount()+");";
		
		SQLiteConnector.getInstance().runUnreturnedQuery(sql);
	}
	
	public static void printAllStockTickets() throws SQLException {
		Connection c = SQLiteConnector.getInstance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		stmt = c.createStatement();
		rs = stmt.executeQuery("SELECT * FROM stock_tickets");
	    
		while (rs.next()) {
		     int ticketID = rs.getInt("ticket_id");
		     String date = rs.getString("date");
		     
		     System.out.print( "ID = " + ticketID );
		     System.out.println( ", IS_VALID = " + date );
		}
		 
		rs.close();
	    stmt.close();
	}
}