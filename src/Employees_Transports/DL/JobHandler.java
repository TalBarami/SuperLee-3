package Employees_Transports.DL;

import Employees_Transports.Backend.Job;
import Store.SQLiteConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;


public class JobHandler {
	private Connection c;
	private static JobHandler instance = null;

	public JobHandler() {
		c = SQLiteConnector.getInstance().getConnection();
	}

	public static JobHandler getInstance(){
		if (instance == null)
			instance = new JobHandler();
		return instance;
	}

	public LinkedList<Job> getAll() {
		LinkedList<Job> ans = new LinkedList<Job>();
		ResultSet rs = null;
		Statement s = null;
		try {
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM Jobs");
			while (rs.next()) {
				ans.add(new Job(rs.getString("Name"), rs
						.getString("Requirements")));
			}
			rs.close();
			s.close();
		} catch (Exception e) {
			ans = null;
		}
		return ans;
	}
	
	public Job getJobByName(String name){
		Job ans = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			s = c.createStatement();
			rs = s.executeQuery("SELECT * FROM Jobs where Name = '"+name+"'");
			while (rs.next()) {
				ans = new Job(rs.getString("Name"), rs
						.getString("Requirements"));
			}
			rs.close();
			s.close();
		}
		catch(Exception e){}
		return ans;
	}
	
	public boolean addJob(String name, String req){
		Statement s = null;
		try {
			s = c.createStatement();
			s.executeUpdate("INSERT INTO Jobs "+
					"VALUES ('"+name+"', '"+req+"')");
			s.close();
			return true;
		}
		catch (Exception e){
			System.out.println("*ERROR: omething went wrong!");
			if((""+e).contains("UNIQUE")){
				System.out.println("*ERROR: This job already exists!");
			}
			return false;
		}
	}
	
	public boolean deleteJob(String name){
		if(!name.equals("Shift Manager") && !name.equals("Driver")){
			Statement s = null;
			try {
				s = c.createStatement();
				s.executeUpdate("DELETE from Jobs "+
						"where Name = '"+name+"'");
				s.close();
				return true;
			}
			catch (Exception e){
				System.out.println("*ERROR: Something went wrong!");
				return false;
			}
		}
		else
		{
			System.out.println("*ERROR: 'Shift Manager' and 'Driver' cannot be deleted!");
			return false;
		}
	}
}
