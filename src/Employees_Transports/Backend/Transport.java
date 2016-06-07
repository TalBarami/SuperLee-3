package Employees_Transports.Backend;

import java.util.ArrayList;


public class Transport {
	
	private String date;
	private String leaving_time;
	private truck truck_id;
	private driver driver_id;
	private station source_address;
	private ArrayList<Pair<station,Integer>> stops_invation;
	
	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getLeaving_time() {
		return leaving_time;
	}



	public void setLeaving_time(String leaving_time) {
		this.leaving_time = leaving_time;
	}



	public truck getTruck_id() {
		return truck_id;
	}



	public void setTruck_id(truck truck_id) {
		this.truck_id = truck_id;
	}



	public driver getDriver_id() {
		return driver_id;
	}



	public void setDriver_id(driver driver_id) {
		this.driver_id = driver_id;
	}



	public station getSource_address() {
		return source_address;
	}



	public void setSource_address(station source_address) {
		this.source_address = source_address;
	}



	public ArrayList<Pair<station, Integer>> getStops_invation() {
		return stops_invation;
	}



	public void setStops_invation(ArrayList<Pair<station, Integer>> stops_invation) {
		this.stops_invation = stops_invation;
	}



	public Transport(String date, String leaving_time, truck truck_id, driver driver_id, station source_address,
			ArrayList<Pair<station, Integer>> stops_invation) 
	{
		this.date = date;
		this.leaving_time = leaving_time;
		this.truck_id = truck_id;
		this.driver_id = driver_id;
		this.source_address = source_address;
		this.stops_invation = stops_invation;
	}
	
	public void addStop(Pair<station,Integer> stops_invation)
	{
		this.stops_invation.add(stops_invation);
	}

	

	public String toString() {
		return "Transport [date=" + date + ", leaving_time=" + leaving_time + ", truck_id=" + truck_id + ", driver_id="
				+ driver_id + ", source_address=" + source_address + ", stops_invation=" + stops_invation + "]";
	}
	
	
	
	
	
}
