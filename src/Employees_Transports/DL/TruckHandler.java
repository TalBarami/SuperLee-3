package Employees_Transports.DL;

import Employees_Transports.Backend.truck;
import Store.SQLiteConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class TruckHandler {
	private Connection c;
	private static TruckHandler instance = null;

	public TruckHandler() {
		c = SQLiteConnector.getInstance().getConnection();
	}

	public static TruckHandler getInstance(){
		if (instance == null)
			instance = new TruckHandler();
		return instance;
	}
	
	public truck getruck(String id)
	{
		Statement stmt;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM Track where trackId="+id);
			int idans = rs.getInt("trackId");
			String  model = rs.getString("model");
			String  color = rs.getString("color");
			double netw=rs.getDouble("weightT");
			double maxw=rs.getDouble("maxWeight");
			int licenseDeg = rs.getInt("licenseDeg");
			rs.close();
			stmt.close();
			return new truck(idans,model,color,netw,maxw,licenseDeg);


		} catch (SQLException e) {
			return null;
		}
	}
	
	public ArrayList<truck> getAllTrucks()
	{
		ArrayList<truck> ans = new ArrayList<truck>();
		Statement stmt;
		try{
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM Track");
			while(rs.next())
			{
				int idans = rs.getInt("trackId");
				String  model = rs.getString("model");
				String  color = rs.getString("color");
				double netw=rs.getDouble("weightT");
				double maxw=rs.getDouble("maxWeight");
				int licenseDeg = rs.getInt("licenseDeg");
				ans.add(new truck(idans,model,color,netw,maxw,licenseDeg));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			return null;
		}
		return ans;
	}
	
	public boolean insertTruck(String id, String model, String color, String net_weight, String max_weight,String licenseDeg)
	{
		Statement stmt;
		try
		{
			stmt = c.createStatement();
			String sql = "INSERT INTO Track (trackId,model,color,weightT,maxWeight,licenseDeg) " +
					"VALUES ("+id+",'"+model+"','"+color+"',"+net_weight+","+max_weight+","+licenseDeg+")"; 
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e) 
		{
			if(e.getMessage().equals("UNIQUE constraint failed: Track.trackId")) System.out.println("*ERROR: Truck id already exists");
			else System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean deleteTrack(String id)
	{
		Statement stmt;
		try 
		{
			stmt = c.createStatement();                                                                                                                                                                                                          
			if(getruck(id) == null) throw new SQLException();
			String sql2 = "delete from Track where trackId ="+id;
			stmt.executeUpdate(sql2);
			stmt.close();
		}
		catch (SQLException e) 
		{
			System.out.println("*ERROR: Truck dosen't exist");
			return false;
		}

		return true;
	}
	
	public boolean updateTruck(String truckId,String newData,int option){ 
		Statement stmt;
		try 
		{
			String sql ="";
			stmt = c.createStatement();                                                                                                                                                                                                          
			if(getruck(truckId) == null) throw new SQLException();
			if(option ==1) sql ="UPDATE Track set model = '"+newData +"' where trackId="+truckId; //modele
			if(option ==2) sql ="UPDATE Track set color = '"+newData +"' where trackId="+truckId; //color
			if(option ==3) sql ="UPDATE Track set weightT = '"+newData +"' where trackId="+truckId; //weightT
			if(option ==4) sql ="UPDATE Track set maxWeight = '"+newData +"' where trackId="+truckId; //maxWeight
			if(option ==5) sql ="UPDATE Track set licenseDeg = '"+newData +"' where trackId="+truckId; //licenseDeg
			stmt.executeUpdate(sql);
		}
		catch (SQLException e) 
		{
			System.out.println("*ERROR: Truck dosen't exist");
			return false;
		}
		return true;
	}
}
