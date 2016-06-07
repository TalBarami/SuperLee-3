package Employees_Transports.DL;
import Employees_Transports.Backend.Employee;
import Employees_Transports.Backend.driver;
import Store.SQLiteConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DriverHandler {
	private Connection c;
	private static DriverHandler instance = null;

	public DriverHandler() {
		c = SQLiteConnector.getInstance().getConnection();
	}

	public static DriverHandler getInstance(){
		if (instance == null)
			instance = new DriverHandler();
		return instance;
	}
	
	public driver getdriver(String id)
	{
		Statement stmt;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM Driver where driverId="+id);
			int  license = rs.getInt("licenseDeg");
			rs.close();
			stmt.close();
			Employee temp = EmployeeHandler.getInstance().getEmployeeByID(id);
			return new driver(temp.getFirstname(),temp.getLastname(),temp.getID(), temp.getBankAccountNum(), temp.getSalary(), temp.getEmploymentDate(), temp.getJobs(), license);


		} catch (SQLException e) {
			return null;
		}
	}
	
	public ArrayList<driver> getAllDrivers()
	{
		ArrayList<driver> ans = new ArrayList<driver>();
		Statement stmt;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Driver");
			while(rs.next())
			{
				int  license = rs.getInt("licenseDeg");
				Employee temp = EmployeeHandler.getInstance().getEmployeeByID(rs.getString("driverId"));
				ans.add(new driver(temp.getFirstname(),temp.getLastname(),temp.getID(), temp.getBankAccountNum(), temp.getSalary(), temp.getEmploymentDate(), temp.getJobs(), license));
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			return null;
		}
		return ans;
	}
	
	public boolean insertDriver(String id,String license_type)
	{
		try
		{
			Statement stmt1 = c.createStatement();
			String sql;
			if(EmployeeHandler.getInstance().checkIfDriver(id)){
				sql = "INSERT INTO driver " +
						"VALUES ("+id+","+license_type+")"; 
				stmt1.executeUpdate(sql);
			}
			else{
				System.out.println("*ERROR: Employee does not exist or not defined as a driver!");
				return false;
			}
		}
		catch (SQLException e) 
		{
			if(e.getMessage().equals("UNIQUE constraint failed: Driver.driverId")) System.out.println("*ERROR: Driver already exists");
			else System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean deleteDriver(String id)
	{
		
		Statement stmt;
		try 
		{
			stmt = c.createStatement();                                                                                                                                                                                                          
			if(getdriver(id) == null) throw new SQLException();
			String sql2 = "delete from Driver where driverId="+id;
			stmt.executeUpdate(sql2);
			stmt.close();
		}
		catch (SQLException e) 
		{
			System.out.println("*ERROR: Driver dosen't exist");
			return false;
		}
		return true;

	}
	
	public boolean updateDriver(String driverId,String newData){ 
		Statement stmt;
		try 
		{
			String sql ="";
			stmt = c.createStatement();                                                                                                                                                                                                          
			if(getdriver(driverId) == null) throw new SQLException();
			sql ="UPDATE Driver set licenseDeg = '"+newData +"' where driverId="+driverId;
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e) 
		{
			System.out.println("*ERROR: Driver dosen't exist");
			return false;
		}
		return true;
	}
}
