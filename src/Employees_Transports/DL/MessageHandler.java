package Employees_Transports.DL;
import Employees_Transports.Backend.Message;
import Store.SQLiteConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;



public class MessageHandler {
	private Connection c;
	private static MessageHandler instance = null;

	public MessageHandler() {
		c = SQLiteConnector.getInstance().getConnection();
	}

	public static MessageHandler getInstance(){
		if (instance == null)
			instance = new MessageHandler();
		return instance;
	}

	public LinkedList<Message> getAll(String userID) {
		LinkedList<Message> ans = new LinkedList<Message>();
		ResultSet rs = null;
		Statement s = null;
		try {
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM Messages where User = '"+userID+"'");
			while (rs.next()) {
				ans.add(new Message(rs.getString("MessageID"), rs
					 	.getString("User"), rs
						.getString("Message")));
			}
			
			rs.close();
			s.close();
			
			s = c.createStatement();
			s.executeUpdate("DELETE from Messages "+
					"where  User = '"+userID+"'");
			s.close();
		} catch (Exception e) {
			ans = null;
		}
		return ans;
	}
}
