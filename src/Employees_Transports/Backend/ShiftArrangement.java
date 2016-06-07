package Employees_Transports.Backend;

import java.util.LinkedList;

public class ShiftArrangement {
	private Shift _shift;
	private LinkedList<Pair<Employee, Job>> _employees;

	public ShiftArrangement(Shift shift, LinkedList<Pair<Employee, Job>> employees) {
		_shift = shift;
		_employees = employees;
	}

	public Shift getShift() {
		return this._shift;
	}

	public void AddEmployee(Employee e, Job j) {
		_employees.add(new Pair<Employee, Job>(e, j));
	}

	public LinkedList<Pair<Employee, Job>> getEmployees() {
		return this._employees;
	}
	
	private boolean hasManager(){
		boolean ans = false;
		for(int i=0;i<_employees.size();i++){
			if(_employees.get(i).getB().getName().equals("Shift Manager"))
				ans=true;
		}
		return ans;
	}
	
	public String toString(){
		String str = ""+ _shift;
		for(int i=0;i<_employees.size();i++)
			str=str+"\n   "+_employees.get(i);
		if(_employees.size()==0)
			str=str+"\n   No Arrangement";
		else
		if(!this.hasManager())
			str=str+"\nPAY ATTENTION!\n  This shift has no manager!";
			
		return str;
	}
	
}