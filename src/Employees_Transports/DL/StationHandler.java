package Employees_Transports.DL;
import Employees_Transports.Backend.station;
import Employees_Transports.Backend.area;
import Store.SQLiteConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class StationHandler {
	private Connection c;
	private static StationHandler instance = null;

	public StationHandler() {
		c = SQLiteConnector.getInstance().getConnection();
	}

	public static StationHandler getInstance(){
		if (instance == null)
			instance = new StationHandler();
		return instance;
	}
	
	public station getstation(String address)
	{ 
		Statement stmt;
		try{
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM Station where address="+'"'+address+'"');
			String addresstemp = rs.getString("address");
			String  p = rs.getString("phoneNum");
			String  n = rs.getString("contact");
			String area=rs.getString("areasname");
			rs.close();
			stmt.close();
			ArrayList<area> a = AreaHandler.getInstance().getsAreaList();
			for(int i=0; i<a.size();i++)
			{
				if(a.get(i).getName().equals(area))
				{
					rs.close();
					stmt.close();
					return new station(addresstemp,p,n,a.get(i));
				}
			}
			return null;

		} catch (SQLException e) {
			return null;
		}
	}
	
	public ArrayList<station> getAllStations()
	{
		ArrayList<station> ans = new ArrayList<station>();
		Statement stmt;
		try{
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM Station");
			while(rs.next())
			{
				String address = rs.getString("address");
				String  p = rs.getString("phoneNum");
				String  n = rs.getString("contact");
				String area=rs.getString("areasname");
				ArrayList<area> a = AreaHandler.getInstance().getsAreaList();
				for(int i=0; i<a.size();i++)
				{
					if(a.get(i).getName().equals(area))
					{
						ans.add(new station(address,p,n,a.get(i)));
					}
				}

			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			return null;
		}
		return ans;
	}
	
	public boolean insertStation(String address, String phone_Number, String name, String areasname)
	{
		Statement stmt;
		try
		{
			stmt = c.createStatement();
			String sql = "INSERT INTO Station (address,phoneNum,contact,areasname) " +
					"VALUES ('"+address+"'"+","+phone_Number+",'"+name+"',"+"'"+areasname+"')"; 
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e) 
		{
			if(e.getMessage().equals("UNIQUE constraint failed: Station.address")) System.out.println("*ERROR: Station already exists");
			else if(e.getMessage().equals("FOREIGN KEY constraint failed")) System.out.println("*ERROR: Area dosen't exist");
			else System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean deleteStation(String address)
	{
		Statement stmt;
		try 
		{
			stmt = c.createStatement();                                                                                                                                                                                                          
			if(getstation(address) == null) throw new SQLException();
			String sql2 = "delete from Station where address='"+address+"'";
			stmt.executeUpdate(sql2);
			stmt.close();
		}
		catch (SQLException e) 
		{
			System.out.println("*ERROR: Station dosen't exist");
			return false;
		}
		return true;

	}
	
	public boolean updateStation(String address,String newData,int option){// 1-phonenum, 2-contact 3-areaname
		Statement stmt;
		try 
		{
			String sql ="";
			stmt = c.createStatement();                                                                                                                                                                                                          
			if(getstation(address)== null) throw new SQLException();
			if(option ==1) sql ="UPDATE Station set phoneNum= '"+newData +"' where address= '"+address+"'"; //phoneNum
			if(option ==2) sql ="UPDATE Station set contact= '"+newData +"' where address= '"+address+"'"; //contact
			if(option ==3) sql ="UPDATE Station set areasname= '"+newData +"' where address= '"+address+"'"; //areasname
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e) 
		{
			if(e.getMessage().equals("FOREIGN KEY constraint failed")) System.out.println("*ERROR: Area dosen't exist");
			else System.out.println("*ERROR: Station dosen't exist");
			return false;
		}
		return true;
	}
	
	
}
