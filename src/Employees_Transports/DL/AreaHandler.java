package Employees_Transports.DL;

import Employees_Transports.Backend.area;
import Store.SQLiteConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class AreaHandler {
	private Connection c;
	private static AreaHandler instance = null;

	public AreaHandler() {
		c = SQLiteConnector.getInstance().getConnection();
	}

	public static AreaHandler getInstance(){
		if (instance == null)
			instance = new AreaHandler();
		return instance;
	}
	
	public ArrayList<area> getsAreaList()
	{
		Statement stmt;
		try{
			ArrayList<area> ans= new ArrayList<area>();
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM area");
			while (rs.next()) {
				String name = rs.getString("name");
				ans.add(new area(name));
			}
			rs.close();
			stmt.close();
			return ans;
		}
		catch (SQLException e) {
			return null;
		}
	}
	
	public boolean insertArea(String name)
	{
		Statement stmt;
		try
		{
			stmt = c.createStatement();
			String sql = "INSERT INTO area (name) " +
					"VALUES ('"+name+"'"+")"; 
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e) 
		{
			if(e.getMessage().equals("UNIQUE constraint failed: area.name")) System.out.println("*ERROR: Area already exists");
			else System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean deleteArea(String name)
	{
		Statement stmt;
		try 
		{
			stmt = c.createStatement();  
			ArrayList<area> temp= getsAreaList();
			for(int i =0; i<temp.size();i++)
			{
				if(temp.get(i).getName().equals(name))
				{
					String sql2 = "delete from area where name='"+name+"'";
					stmt.executeUpdate(sql2);
					stmt.close();
					return true;
				}
			}
			throw new SQLException();		
		}
		catch (SQLException e) 
		{
			System.out.println("*ERROR: Area dosen't exist");
			return false;
		}
	}
	
	public boolean multipleArea(ArrayList<String> stationList)
	{
		HashMap<String, Integer> temp= new HashMap<String,Integer>();
		int count=0;
		for(int i=0;i<stationList.size();i++)
		{
			if(stationList.get(i)!=null&&StationHandler.getInstance().getstation(stationList.get(i))!=null)
			{
				String ans=StationHandler.getInstance().getstation(stationList.get(i)).getArea().getName();
				if(ans!=null){
					if(temp.containsKey(ans));
					else
					{
						temp.put(ans, 0);
						count++;
					}
				}
			}
		}

		return count>1;

	}
}
