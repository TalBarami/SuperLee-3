package Employees_Transports.Backend;

public class Job {
	private String _name;
	private String _requirements;

	public Job(String aName, String aRequirements) {
		_name = aName;
		_requirements = aRequirements;
	}

	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	public String getRequirements() {
		return _requirements;
	}

	public void setRequirements(String _requirements) {
		this._requirements = _requirements;
	}
	
	public String toString(){
		return "Job: "+_name+"\nRequirements: "+_requirements;
	}


}