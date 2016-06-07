package Employees_Transports.Backend;
import java.util.Date;
import java.util.LinkedList;

public class driver extends Employee {

	private int license_type;
	
	
	public String getId() {
		return super.getID();
	}


	public int getLicense_type() {
		return license_type;
	}


	public void setLicense_type(int license_type) {
		this.license_type = license_type;
	}


	public String getName() {
		return (super.getFirstname()+super.getLastname());
	}


	public driver(String firstName, String lastName, String id, String bankAccountNum, double salary, Date employmentDate, LinkedList<Job> jobs, int license_type) {
		super(firstName, lastName, id, bankAccountNum, salary, employmentDate, jobs);
		this.license_type = license_type;
	}


	@Override
	public String toString() {
		return super.toString()+"\nDriving License Type: "+this.license_type;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
