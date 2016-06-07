package Employees_Transports.Backend;

public class truck {
	
	public int id;
	public String model;
	public String color;
	public double net_weight;
	public double max_weight;
	public int licenseDeg;
	
	
	
	public truck(int id, String model, String color, double net_weight, double max_weight,int licenseDeg)
	{
		
		this.id = id;
		this.model = model;
		this.color = color;
		this.net_weight = net_weight;
		this.max_weight = max_weight;
		this.licenseDeg=licenseDeg;
	}



	@Override
	public String toString() {
		return "truck [id=" + id + ", model=" + model + ", color=" + color + ", net weight=" + net_weight
				+ ", max weight=" + max_weight + ", license Deg=" + licenseDeg + "]";
	}
	
	
	
	

}
