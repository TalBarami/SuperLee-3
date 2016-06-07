package Employees_Transports.Backend;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Employee {
	private String _firstname;
	private String _lastname;
	private String _ID;
	private String _bankAccountNum;
	private double _salary;
	private Date _employmentDate;
	private LinkedList<Job> _jobs;

	public Employee(String firstName, String lastName, String id, String bankAccountNum, double salary, Date employmentDate, LinkedList<Job> jobs) {
		_firstname = firstName;
		_lastname = lastName;
		_ID = id;
		_bankAccountNum = bankAccountNum;
		_salary = salary;
		_employmentDate = employmentDate;
		_jobs = jobs;
	}

	public double getSalary() {
		return this._salary;
	}


	public Date getEmploymentDate() {
		return this._employmentDate;
	}


	public void addJob(Job aJob) {
		_jobs.add(aJob);
	}

	public LinkedList<Job> getJobs() {
		return _jobs;
	}

	public void setFirstname(String aFirstname) {
		this._firstname = aFirstname;
	}

	public String getFirstname() {
		return this._firstname;
	}

	public void setLastname(String aLastname) {
		this._lastname = aLastname;
	}

	public String getLastname() {
		return this._lastname;
	}

	public void setID(String aID) {
		this._ID = aID;
	}

	public String getID() {
		return this._ID;
	}

	public void setBankAccountNum(String aBankAccountNum) {
		this._bankAccountNum = aBankAccountNum;
	}

	public String getBankAccountNum() {
		return this._bankAccountNum;
	}

	public void setSalary(double aSalary) {
		this._salary = aSalary;
	}

	public void setEmploymentDate(Date aEmploymentDate) {
		this._employmentDate = aEmploymentDate;
	}

	public void set_jobs(LinkedList<Job> _jobs) {
		this._jobs = _jobs;
	}
	
	public String toString(){
		String str = "Name: "+_firstname +" "+_lastname+"\nID: "+_ID+"\nBank Account: "+_bankAccountNum;
		str=str+"\nSalary: "+_salary+"\nEmployment Date: " +(new SimpleDateFormat("dd/MM/yyyy").format(_employmentDate));
		str=str+"\nJobs: ";
		for(int i=0; i<_jobs.size();i++){
			str = str+ _jobs.get(i).getName();
			if(i!=_jobs.size()-1)
				str = str+", ";
		}	
		return str;
	}
}