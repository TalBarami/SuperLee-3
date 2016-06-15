package Employees_Transports.DL;

import Employees_Transports.Backend.*;
import Store.SQLiteConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;


public class ShiftHandler {

	private Connection c;
	private static ShiftHandler instance = null;

	public ShiftHandler() {
		c = SQLiteConnector.getInstance().getConnection();
	}

	public static ShiftHandler getInstance(){
		if (instance == null)
			instance = new ShiftHandler();
		return instance;
	}

	public LinkedList<Shift> getAll() {
		LinkedList<Shift> ans = new LinkedList<Shift>();
		ResultSet rs = null;
		Statement s = null;
		try {
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM Shifts");
			while (rs.next()) {
				ans.add(new Shift(new SimpleDateFormat("dd/MM/yyyy")
				.parse(rs.getString("Shift_Date")), rs
				.getBoolean("Is_Morning"), StationHandler.getInstance().getstation(rs.getString("Station"))));
			}
			rs.close();
			s.close();
		} catch (Exception e) {
			System.out.println("*ERROR: Something went wrong!");
			ans = null;
		}
		return ans;
	}

	public boolean deleteShift(Date date, boolean isM, String station){
		Statement s = null;
		int bool = 0;
		if(isM) bool=1;
		try {
			s = c.createStatement();
			s.executeUpdate("DELETE from Shifts "+
					"where Shift_Date = '"+(new SimpleDateFormat("dd/MM/yyyy").format(date))+"' and Is_Morning = "+bool+ " and Station='"+"'");
			s.close();
			return true;
		}
		catch (Exception e){
			System.out.println("*ERROR: Something went wrong!");
			return false;
		}
	}

	public boolean addShift(Date date, boolean isMorning, String station){
		Statement s = null;
		int bool = 0;
		if(isMorning) bool=1;
		try {
			s = c.createStatement();
			s.executeUpdate("INSERT INTO Shifts "+
					"VALUES ('"+(new SimpleDateFormat("dd/MM/yyyy").format(date))+"', "+bool+", '"+station+"')");
			s.close();
			return true;
		}
		catch (Exception e){
			System.out.println("*ERROR: Something went wrong!");
			if((""+e).contains("UNIQUE")){
				System.out.println("*ERROR: This shift already exists!");
			}
			if((""+e).contains("FOREIGN")){
				System.out.println("*ERROR: Station does not exist!");
			}
			return false;
		}
	}

	public LinkedList<ShiftArrangement> getAllArrangements() {
		LinkedList<ShiftArrangement> ans = new LinkedList<ShiftArrangement>();
		ResultSet rs = null;
		Statement s = null;
		try {
			s = c.createStatement();

			rs = s.executeQuery("SELECT * FROM Shifts");
			while (rs.next()) {
				ans.add(new ShiftArrangement(new Shift(new SimpleDateFormat("dd/MM/yyyy")
				.parse(rs.getString("Shift_Date")), rs
				.getBoolean("Is_Morning"), StationHandler.getInstance().getstation(rs.getString("Station"))), new LinkedList<Pair<Employee, Job>>()));
			}
			rs.close();
			for(int i=0; i<ans.size();i++)
			{
				rs = s.executeQuery("SELECT * FROM Employees_In_Shifts");
				while (rs.next()) {
					if (((ShiftArrangement)ans.get(i)).getShift().getShiftDate().equals(
							new SimpleDateFormat("dd/MM/yyyy").parse(rs
									.getString("Shift_Date")))
									&& (((ShiftArrangement)ans.get(i)).getShift()).getIsMorning() == rs
									.getBoolean("Is_Morning")
									&& (((ShiftArrangement)ans.get(i)).getShift()).getStation().getAddress().equals(rs.getString("Station"))) {
						String temp = rs.getString("Job");
						((ShiftArrangement)ans.get(i)).AddEmployee(EmployeeHandler.getInstance().getEmployeeByID(rs
								.getString("EmployeeID")), JobHandler.getInstance().getJobByName(temp));
					}
				}
			}
			rs.close();
			s.close();
		} catch (Exception e) {
			System.out.println("*ERROR: Something went wrong!");
			ans = null;
		}
		return ans;
	}
	
	public LinkedList<ShiftPriorities> getAllPriorities() {
		LinkedList<ShiftPriorities> ans = new LinkedList<ShiftPriorities>();
		ResultSet rs = null;
		Statement s = null;
		try {
			s = c.createStatement();
				rs = s.executeQuery("SELECT * FROM Shifts");
				while (rs.next()) {
					ans.add(new ShiftPriorities(new Shift(new SimpleDateFormat("dd/MM/yyyy")
					.parse(rs.getString("Shift_Date")), rs
					.getBoolean("Is_Morning"), StationHandler.getInstance().getstation(rs.getString("Station"))), new LinkedList<Employee>()));
				}
				rs.close();
				for(int i=0; i<ans.size();i++)
				{
					rs = s.executeQuery("SELECT * FROM Shift_Priorities");
					while (rs.next()) {
						if (((ShiftPriorities)ans.get(i)).getShift().getShiftDate().equals(
								new SimpleDateFormat("dd/MM/yyyy").parse(rs
										.getString("Shift_Date")))
										&& (((ShiftPriorities)ans.get(i)).getShift()).getIsMorning() == rs
										.getBoolean("Is_Morning")
										&& (((ShiftPriorities)ans.get(i)).getShift()).getStation().getAddress().equals(rs.getString("Station"))) {
							((ShiftPriorities)ans.get(i)).AddEmployee(EmployeeHandler.getInstance().getEmployeeByID(rs
									.getString("EmployeeID")));
						}
					}
				}
				rs.close();
			s.close();
		} catch (Exception e) {
			ans = null;
		}
		return ans;
	}
	
	public boolean addShiftPriorities(Date date, boolean isMorning, String id, String station){
		Statement s = null;
		int bool = 0;
		if(isMorning) bool=1;
		try {
			s = c.createStatement();
			s.executeUpdate("INSERT INTO Shift_Priorities "+
					"VALUES ('"+(new SimpleDateFormat("dd/MM/yyyy").format(date))+"', "+bool+", '"+id+"', '"+station+"')");
			s.close();
			return true;
		}
		catch (Exception e){
			System.out.println("*ERROR: Something went wrong!");
			if((""+e).contains("UNIQUE")){
				System.out.println("*ERROR: This priority already exists!");
			}
			if((""+e).contains("FOREIGN KEY")){
				System.out.println("*ERROR: Shift and/or Employee and/or Station do not exist!");
			}
			return false;
		}
	}

	public boolean addShiftArrngement(Date date, boolean isMorning, String id, String job, String station){
		Statement s = null;
		int bool = 0;
		int truck;
		if(isMorning) bool=1;

		if(job.equals("Driver"))
		{
			truck = TransportHandler.getInstance().getAvailableTruck(new SimpleDateFormat("dd/MM/yyyy").format(date)+"",id);
			if(truck==0)
			{
				System.out.print("No available trucks! Driver hasn't been added.");
				return false;
			}
			else
			{
				String leavingTime="";
				if(isMorning)
					leavingTime="06:30";
				else
					leavingTime="18:30";
				if(!TransportHandler.getInstance().insertTransportAhead(new SimpleDateFormat("dd/MM/yyyy").format(date)+"",leavingTime, ""+truck, id, station))
				{
					System.out.print("Could not add driver.");
					return false;
				}
			}
		}
		try {
			s = c.createStatement();
			s.executeUpdate("INSERT INTO Employees_In_Shifts "+
					"VALUES ('"+(new SimpleDateFormat("dd/MM/yyyy").format(date))+"', "+bool+", '"+id+"', '"+job+"', '"+station+"')");
			s.close();
			return true;
		}
		catch (Exception e){
			System.out.println("*ERROR: Something went wrong!");
			if((""+e).contains("UNIQUE")){
				System.out.println("*ERROR: This arrangement already exists!");
			}
			if((""+e).contains("FOREIGN KEY")){
				System.out.println("*ERROR: Shift and/or Employee and/or Station do not exist,"
						+ "\nor Emloyee is not attached to this Job!");

			}
			return false;
		}
	}
	
	public boolean deleteShiftPriorities(Date date, boolean isMorning, String id, String station){
		Statement s = null;
		int bool = 0;
		if(isMorning) bool=1;
		try {
			s = c.createStatement();
			s.executeUpdate("DELETE from Shift_Priorities "+
					"where '"+(new SimpleDateFormat("dd/MM/yyyy").format(date))+"' and Is_Morning = "+bool
					+" and EmployeeID='"+id+"' and Station='"+station+"'");
			s.close();
			return true;
		}
		catch (Exception e){
			System.out.println("*ERROR: Something went wrong!");
			return false;
		}
	}

	public boolean deleteShiftArrangement(Date date, boolean isMorning, String id, String station){
		Statement s = null;
		int bool = 0;
		if(isMorning) bool=1;
		try {
			s = c.createStatement();
			s.executeUpdate("DELETE from Employees_In_Shifts "+
					"where '"+(new SimpleDateFormat("dd/MM/yyyy").format(date))+"' and Is_Morning = "+bool
					+" and EmployeeID='"+id+"' and Station='"+station+"'");
			s.close();
			return true;
		}
		catch (Exception e){
			System.out.println("*ERROR: Something went wrong!");
			return false;
		}
	}

	public LinkedList<Employee> getManagers(Date date, boolean isM, String station){
		Statement s = null;
		LinkedList<Employee> ans = new LinkedList<Employee>();
		ResultSet rs = null;
		int bool = 0;
		if(isM) bool=1;
		try {
			s = c.createStatement();
			rs = s.executeQuery("SELECT Shift_Priorities.EmployeeID FROM Shift_Priorities Join Jobs_For_Employees "
					+"on Shift_Priorities.EmployeeID = Jobs_For_Employees.EmployeeID "
					+"where Jobs_For_Employees.Job = 'Shift Manager' and Shift_Priorities.Shift_Date='"+(new SimpleDateFormat("dd/MM/yyyy").format(date))+"' and "
					+"Shift_Priorities.Is_Morning="+bool+" and Shift_Priorities.Station='"+station+"'"
							+ " and Jobs_For_Employees.EmployeeID not in "
					+"(SELECT EmployeeID FROM Employees_In_Shifts where Shift_Date = '"+(new SimpleDateFormat("dd/MM/yyyy").format(date))+"' and Is_Morning="+bool+")");
			while (rs.next()) {
				ans.add(EmployeeHandler.getInstance().getEmployeeByID(rs.getString("EmployeeID")));
			}
			rs.close();
			s.close();
		}
		catch (Exception e){
		}
		return ans;
	}

	public LinkedList<Job> getAvailableJobs(Date date, boolean isM, String station){
		Statement s = null;
		LinkedList<Job> ans = new LinkedList<Job>();
		ResultSet rs = null;
		int bool = 0;
		if(isM) bool=1;
		try {
			s = c.createStatement();
			rs = s.executeQuery("SELECT DISTINCT Jobs_For_Employees.Job FROM Shift_Priorities join Jobs_For_Employees "
					+ " on Jobs_For_Employees.EmployeeID = Shift_Priorities.EmployeeID where "
					+ "Shift_Priorities.Shift_Date = '"+(new SimpleDateFormat("dd/MM/yyyy").format(date))+"' and Shift_Priorities.Is_Morning = "+bool
					+" and Jobs_For_Employees.Job <> 'Shift Manager'"
					+" and Shift_Priorities.Station = '"+station+"'"
					+" and Jobs_For_Employees.EmployeeID not in "
					+"(SELECT EmployeeID FROM Employees_In_Shifts where Shift_Date = '"+(new SimpleDateFormat("dd/MM/yyyy").format(date))+"' and Is_Morning="+bool+")");
			while (rs.next()) {
				ans.add(JobHandler.getInstance().getJobByName(rs.getString("Job")));
			}
			rs.close();
			s.close();
		}
		catch (Exception e){
		}
		return ans;
	}

	public LinkedList<Employee> getAvailableEmployees(Date date, boolean isM, Job j, String station){
		Statement s = null;
		LinkedList<Employee> ans = new LinkedList<Employee>();
		ResultSet rs = null;
		int bool = 0;
		if(isM) bool=1;
		try {
			s = c.createStatement();
			rs = s.executeQuery("SELECT Jobs_For_Employees.EmployeeID FROM Shift_Priorities join Jobs_For_Employees "
					+ " on Jobs_For_Employees.EmployeeID = Shift_Priorities.EmployeeID where "
					+ "Shift_Priorities.Shift_Date = '"+(new SimpleDateFormat("dd/MM/yyyy").format(date))+"' and Shift_Priorities.Is_Morning = "+bool
					+ " and Jobs_For_Employees.Job = '"+j.getName()+"'"
					+" and Shift_Priorities.Station = '"+station+"'"
					+" and Jobs_For_Employees.EmployeeID not in "
					+"(SELECT EmployeeID FROM Employees_In_Shifts where Shift_Date = '"+(new SimpleDateFormat("dd/MM/yyyy").format(date))+"' and Is_Morning="+bool+")");
			while (rs.next()) {
				ans.add(EmployeeHandler.getInstance().getEmployeeByID(rs.getString("EmployeeID")));
			}
			rs.close();
			s.close();
		}
		catch (Exception e){
		}
		return ans;
	}

	public boolean hasManager(Date date, boolean isM, String station){
		Statement s = null;
		boolean ans = false;
		ResultSet rs = null;
		int bool = 0;
		if(isM) bool=1;
		try {
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM Employees_In_Shifts "
					+"where Job = 'Shift Manager' and Shift_Date='"+(new SimpleDateFormat("dd/MM/yyyy").format(date))+"' and "
					+"Is_Morning="+bool+" and Station='"+station+"'");
			if (rs.next()) {
				ans = true;
			}
			rs.close();
			s.close();
		}
		catch (Exception e){
			System.out.println("Something went wrong!");
		}
		return ans;
	}
	
	public ShiftArrangement getShiftArrangement(Date date, boolean isM, String station){
		ResultSet rs = null;
		Statement s = null;
		Shift shift = new Shift(date, isM, StationHandler.getInstance().getstation(station));
		ShiftArrangement ans = new ShiftArrangement(shift, new LinkedList<Pair<Employee, Job>>());
		try {
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM Employees_In_Shifts");
			while (rs.next()) {
				if ((""+(new SimpleDateFormat("dd/MM/yyyy").format(date))).equals(rs
						.getString("Shift_Date"))
						&& shift.getIsMorning() == rs
						.getBoolean("Is_Morning") && station.equals(rs.getString("Station"))) {
					String temp = rs.getString("Job");
					ans.AddEmployee(EmployeeHandler.getInstance().getEmployeeByID(rs
							.getString("EmployeeID")), JobHandler.getInstance().getJobByName(temp));
				}
			}
		}
		catch (Exception e){
		}
		return ans;
	}

	public ShiftPriorities getShiftPriorities(Date date, boolean isM, String station){
		ResultSet rs = null;
		Statement s = null;
		Shift shift = new Shift(date, isM, StationHandler.getInstance().getstation(station));
		ShiftPriorities ans = new ShiftPriorities(shift, new LinkedList<Employee>());
		try {
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM Shift_Priorities");
			while (rs.next()) {
				if ((""+(new SimpleDateFormat("dd/MM/yyyy").format(date))).equals(rs
						.getString("Shift_Date"))
						&& shift.getIsMorning() == rs
						.getBoolean("Is_Morning") && station.equals(rs.getString("Station"))) {
					ans.AddEmployee(EmployeeHandler.getInstance().getEmployeeByID(rs
							.getString("EmployeeID")));
				}
			}
		}
		catch (Exception e){
		}
		return ans;
	}
	
	public boolean checkDriverInShift(String date,String leaving_time, String id, String station) 
	{
		Statement stmt;
		boolean condition=false;
		try {
			int isMorning = 0;
			int h=Integer.parseInt(leaving_time.substring(0, 2));
			if(h>=6&&h<18)
				isMorning =1;
			try {
				stmt = c.createStatement();

				String sql;
				sql = "Select * From Employees_In_Shifts where EmployeeID='"+id+"' and Job='Driver'"
						+ " and Shift_Date='"+date+"' and Is_Morning="+isMorning+" and Station ='"+station+"'";
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
					condition = true;
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				System.out.println("*ERROR: Something went wrong. Check connection");
			}

		}
		catch (Exception e) {
			System.out.println("*ERROR: Wrong input!");
			return false;
		}
		return condition;
	}
	
	public boolean checkIfStorekeeperExists(String date,String leaving_time, String station) 
	{
		Statement stmt;
		boolean condition = false;
		try {
			int isMorning = 0;
			int h=Integer.parseInt(leaving_time.substring(0, 2));
			if(h>=6&&h<18)
				isMorning =1;
			try {
				stmt = c.createStatement();

				String sql;
				sql = "Select * From Employees_In_Shifts"
						+ "  where Job='Storekeeper' and Shift_Date='"+date+"' and Is_Morning="+isMorning+" and Station='"+station+"'";
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next())
					condition = true;
			} catch (SQLException e) {
				System.out.println("*ERROR: Something went wrong. Check connection");
			}

		}
		catch (Exception e) {
			return false;
		}
		return condition;
	}

}
