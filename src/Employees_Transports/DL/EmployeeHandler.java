package Employees_Transports.DL;

import Employees_Transports.Backend.Employee;
import Employees_Transports.Backend.Job;
import Store.SQLiteConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;


public class EmployeeHandler {
	private Connection c;
	private static EmployeeHandler instance = null;

	public EmployeeHandler() {
		c = SQLiteConnector.getInstance().getConnection();
	}

	public static EmployeeHandler getInstance(){
		if (instance == null)
			instance = new EmployeeHandler();
		return instance;
	}
	
	public LinkedList<Employee> getAll() {
		LinkedList<Employee> ans = new LinkedList<Employee>();
		ResultSet rs = null;
		Statement s = null;
		try {
			s = c.createStatement();
				LinkedList<Job> jobs = JobHandler.getInstance().getAll();
				rs = s.executeQuery("SELECT * FROM Employees");
				while (rs.next()) {
					ans.add(new Employee(rs.getString("First_Name"), rs
							.getString("Last_Name"), rs.getString("ID"), rs
							.getString("Bank_Account_Num"), rs
							.getDouble("Salary"),
							new SimpleDateFormat("dd/MM/yyyy").parse(rs
									.getString("Employment_Date")),
									new LinkedList<Job>()));
				}
				rs.close();
				rs = s.executeQuery("SELECT * FROM Jobs_For_Employees");
				while (rs.next()) {
					for (int i = 0; i < ans.size(); i++) {
						if (((Employee) (ans.get(i))).getID().equals(
								rs.getString("EmployeeID"))) {
							for (int j = 0; j < jobs.size(); j++) {
								if ((jobs.get(j)).getName().equals(
										rs.getString("Job")))
									((Employee) (ans.get(i))).addJob(jobs
											.get(j));
							}
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
	
	public Employee getEmployeeByID(String id){
		Employee ans = null;
		ResultSet rs = null;
		Statement s = null;
		try {
			s = c.createStatement();
			LinkedList<Job> jobs = JobHandler.getInstance().getAll();
			rs = s.executeQuery("SELECT * FROM Employees where ID = '"+id+"'");
			while (rs.next()) {
				ans = new Employee(rs.getString("First_Name"), rs
						.getString("Last_Name"), rs.getString("ID"), rs
						.getString("Bank_Account_Num"), rs
						.getDouble("Salary"),
						new SimpleDateFormat("dd/MM/yyyy").parse(rs
								.getString("Employment_Date")),
								new LinkedList<Job>());
			}
			rs.close();
			rs = s.executeQuery("SELECT * FROM Jobs_For_Employees where EmployeeID = " + id);
			while (rs.next()) {
				for (int j = 0; j < jobs.size(); j++) {
					if ((jobs.get(j)).getName().equals(
							rs.getString("Job")))
						ans.addJob(jobs.get(j));
				}
			}
			rs.close();
			s.close();
		} catch (Exception e) {
			ans = null;
		}
		return ans;
	}

	public LinkedList<Employee> getEmployeeByFirstName(String name){
		LinkedList<Employee> ans = new LinkedList<Employee>();
		ResultSet rs = null;
		Statement s = null;
		try {
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM Employees where First_Name = '"+name+"'");
			while (rs.next()) {
				ans.add(this.getEmployeeByID(rs.getString("ID")));
			}
			rs.close();
			s.close();
		} catch (Exception e) {
		}
		return ans;
	}

	public LinkedList<Employee> getEmployeeByLastName(String name){
		LinkedList<Employee> ans = new LinkedList<Employee>();
		ResultSet rs = null;
		Statement s = null;
		try {
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM Employees where Last_Name = '"+name+"'");
			while (rs.next()) {
				ans.add(this.getEmployeeByID(rs.getString("ID")));
			}
			rs.close();
			s.close();
		} catch (Exception e) {
		}
		return ans;
	}
	
	public boolean addEmployee(String firstName, String lastName, String id,
			String bankAccountNum, double salary, Date employmentDate){
		Statement s = null;
		try {
			s = c.createStatement();
			s.executeUpdate("INSERT INTO Employees "+
					"VALUES ("+id+", '"+firstName+"', '"+lastName+"', "
					+bankAccountNum+", "+salary+", '"
					+(new SimpleDateFormat("dd/MM/yyyy").format(employmentDate))+"')");
			s.close();
			return true;
		}
		catch (Exception e){
			System.out.println("*ERROR: Something went wrong!");
			if((""+e).contains("UNIQUE")){
				System.out.println("*ERROR: Employee with such ID already exists!");
			}
			return false;
		}
	}
	
	public boolean addJobToEmployee(String id, String job){
		Statement s = null;
		try {
			s = c.createStatement();
			s.executeUpdate("INSERT INTO Jobs_For_Employees "+
					"VALUES ('"+id+"', '"+job+"')");
			s.close();
			return true;
		}
		catch (Exception e){
			System.out.println("*ERROR: Something went wrong!");
			if((""+e).contains("UNIQUE")){
				System.out.println("*ERROR: This attachment already exists!");
			}
			if((""+e).contains("FOREIGN KEY")){
				System.out.println("*ERROR: Employee and/or job do not exist!");
			}
			return false;
		}
	}

	public boolean editEmployeeFirstName(String id, String fname){
		Statement s = null;
		try {
			s = c.createStatement();
			s.executeUpdate("UPDATE Employees set First_Name = '"+fname+"' where ID='"+id+"';");
			s.close();
			return true;
		}
		catch (Exception e){
			System.out.println("*ERROR: Something went wrong!");
			System.out.println("*ERROR: Employee with such ID does not exist!");
			return false;
		}
	}

	public boolean editEmployeeLastName(String id, String lname){
		Statement s = null;
		try {
			s = c.createStatement();
			s.executeUpdate("UPDATE Employees set Last_Name = '"+lname+"' where ID='"+id+"';");
			s.close();
			return true;
		}
		catch (Exception e){
			System.out.println("Something went wrong!");
			System.out.println("*ERROR: Employee with such ID does not exist!");
			return false;
		}
	}

	public boolean editEmployeeSalary(String id, double salary){
		Statement s = null;
		try {
			s = c.createStatement();
			s.executeUpdate("UPDATE Employees set Salary = "+salary+" where ID='"+id+"';");
			s.close();
			return true;
		}
		catch (Exception e){
			System.out.println("*ERROR: Something went wrong!");
			System.out.println("*ERROR: Employee with such ID does not exist!");
			return false;
		}
	}

	public boolean editEmployeeBankAccount(String id, String bank){
		Statement s = null;
		try {
			s = c.createStatement();
			s.executeUpdate("UPDATE Employees set Bank_Account_Num = '"+bank+"' where ID='"+id+"';");
			s.close();
			return true;
		}
		catch (Exception e){
			System.out.println("*ERROR: Something went wrong!");
			System.out.println("*ERROR: Employee with such ID does not exist!");
			return false;
		}
	}
	
	public boolean deleteEmployee(String id){
		Statement s = null;
		try {
			s = c.createStatement();
			s.executeUpdate("DELETE from Employees "+
					"where ID = '"+id+"'");
			s.close();
			return true;
		}
		catch (Exception e){
			System.out.println("*ERROR: Something went wrong!");
			return false;
		}
	}
	
	public boolean deleteJobToEmployee(String id, String job){
		Statement s = null;
		try {
			s = c.createStatement();
			s.executeUpdate("DELETE from Jobs_For_Employees "+
					"where EmployeeID = '"+id+"' and Job = '"+job+"'");
			s.close();
			return true;
		}
		catch (Exception e){
			System.out.println("*ERROR: Something went wrong!");
			return false;
		}
	}
	
	public boolean checkIfDriver(String id){
		Statement stmt;
		boolean condition = false;
		try
		{
			stmt = c.createStatement();
			String sql;
			sql = "Select * From Jobs_For_Employees where EmployeeID='"+id+"' and Job='Driver'";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
				condition = true;
			rs.close();
			stmt.close();
		}
		catch (SQLException e) 
		{
			if(e.getMessage().equals("UNIQUE constraint failed: Driver.driverId")) System.out.println("*ERROR: Driver already exists!");
			else System.out.println(e.getMessage());
		}
		return condition;
	}
}
