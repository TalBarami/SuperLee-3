package Employees_Transports.Backend;

import java.util.LinkedList;

public class ShiftPriorities {
	private Shift _shift;
	private LinkedList<Employee> _employees;
	
	public ShiftPriorities(Shift shift, LinkedList<Employee> employees) {
		_shift = shift;
		_employees = employees;
	}

	public Shift getShift() {
		return this._shift;
	}

	public LinkedList<Employee> getEmployees() {
		return this._employees;
	}

	public void AddEmployee(Employee e) {
		_employees.add(e);
	}
	
	public String toString(){
		String str = ""+ _shift;
		for(int i=0;i<_employees.size();i++)
			str=str+"\n   "+_employees.get(i);
		if(_employees.size()==0)
			str=str+"\n   No Priorities";
		return str;
	}
}