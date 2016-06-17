package Employees_Transports.BL;

import Employees_Transports.Backend.*;
import Employees_Transports.DL.*;
import Store.SQLiteConnector;
import Suppliers.Entities.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;



public class BLManagerImpl implements BLManager {
	private SQLiteConnector c;

	public BLManagerImpl(){
		c = SQLiteConnector.getInstance();
	}

	@Override
	public LinkedList<Employee> getEmploees() {
		return EmployeeHandler.getInstance().getAll();
	}

	@Override
	public LinkedList<Shift> getshifts() {
		return ShiftHandler.getInstance().getAll();
	}

	@Override
	public LinkedList<ShiftPriorities> getshiftPriorities() {
		return ShiftHandler.getInstance().getAllPriorities();
	}

	@Override
	public LinkedList<ShiftArrangement> getshifArrngement() {
		return ShiftHandler.getInstance().getAllArrangements();
	}

	@Override
	public LinkedList<Job> getJobs() {
		return JobHandler.getInstance().getAll();
	}

	@Override
	public boolean addEmploee(String firstName, String lastName, String id,
			String bankAccountNum, double salary, Date employmentDate) {
		return EmployeeHandler.getInstance().addEmployee(firstName, lastName, id, bankAccountNum, salary, employmentDate);
	}

	@Override
	public boolean addShift(Date date, boolean isMorning, String station) {
		return ShiftHandler.getInstance().addShift(date, isMorning, station);
	}

	@Override
	public boolean addShiftPriorities(Date date, boolean isMorning, String id, String station) {
		return ShiftHandler.getInstance().addShiftPriorities(date, isMorning, id, station);
	}

	@Override
	public boolean addShifArrngement(Date date, boolean isMorning, String id, String job, String station)
	{
		return ShiftHandler.getInstance().addShiftArrngement(date, isMorning, id, job, station);
	}

	@Override
	public boolean addJob(String name, String req) {
		return JobHandler.getInstance().addJob(name,req);
	}

	@Override
	public boolean deleteEmploee(String id) {
		return EmployeeHandler.getInstance().deleteEmployee(id);
	}

	@Override
	public boolean deleteshift(Date date, boolean isM, String station) {
		return ShiftHandler.getInstance().deleteShift(date, isM, station);
	}

	@Override
	public boolean deleteShiftPriorities(Date date, boolean isM, String ID, String station) {
		return ShiftHandler.getInstance().deleteShiftPriorities(date, isM, ID, station);
	}

	@Override
	public boolean deleteShifArrngement(Date date, boolean isM, String ID, String station) {
		return ShiftHandler.getInstance().deleteShiftArrangement(date, isM, ID, station);
	}

	@Override
	public boolean deleteJob(String name) {
		return JobHandler.getInstance().deleteJob(name);
	}

	@Override
	public void endConnection() {
		c.endConnection();
	}

	@Override
	public LinkedList<Employee> getAvailableEmployees(Date date, boolean isM,
			Job j, String station) {
		return ShiftHandler.getInstance().getAvailableEmployees(date,isM,j,station);
	}

	@Override
	public LinkedList<Job> getAvailableJobs(Date date, boolean isM, String station) {
		return ShiftHandler.getInstance().getAvailableJobs(date,isM,station);
	}
	

	@Override
	public LinkedList<Employee> getManagers(Date date, boolean isM, String station) {
		return ShiftHandler.getInstance().getManagers(date,isM,station);
	}

	@Override
	public boolean addJobToEmploee(String ID, String job) {
		return EmployeeHandler.getInstance().addJobToEmployee(ID, job);
	}

	@Override
	public boolean editEmployeeFirstName(String id, String fname) {
		return EmployeeHandler.getInstance().editEmployeeFirstName(id, fname);
	}

	@Override
	public boolean editEmployeeLastName(String id, String lname) {
		return EmployeeHandler.getInstance().editEmployeeLastName(id, lname);
	}

	@Override
	public boolean editEmployeeSalary(String id, double salary) {
		return EmployeeHandler.getInstance().editEmployeeSalary(id, salary);
	}

	@Override
	public boolean editEmployeeBankAccount(String id, String bank) {
		return EmployeeHandler.getInstance().editEmployeeBankAccount(id, bank);
	}

	@Override
	public boolean hasManager(Date date, boolean isM, String station) {
		return ShiftHandler.getInstance().hasManager(date, isM,station);
	}

	@Override
	public Employee getEmployeeByID(String id) {
		return EmployeeHandler.getInstance().getEmployeeByID(id);
	}

	@Override
	public boolean deleteJobToEmployee(String id, String job) {
		return EmployeeHandler.getInstance().deleteJobToEmployee(id, job);
	}

	@Override
	public LinkedList<Employee> getEmployeeByFirstName(String name) {
		return EmployeeHandler.getInstance().getEmployeeByFirstName(name);
	}

	@Override
	public LinkedList<Employee> getEmployeeByLastName(String name) {
		return EmployeeHandler.getInstance().getEmployeeByLastName(name);
	}
	
	public ShiftArrangement getShiftArrangement(Date date, boolean isM, String station){
		return ShiftHandler.getInstance().getShiftArrangement(date, isM, station);
	}
	
	public ShiftPriorities getShiftPriorities(Date date, boolean isM, String station){
		return ShiftHandler.getInstance().getShiftPriorities(date, isM, station);
	}

	@Override
	public driver getDriver(String id) {
		return DriverHandler.getInstance().getdriver(id);
	}

	@Override
	public truck getTruck(String id) {
		return TruckHandler.getInstance().getruck(id);
	}

	@Override
	public station getStation(String address) {
		return StationHandler.getInstance().getstation(address);
	}

	@Override
	public ArrayList<area> getsAreaList() {
		return AreaHandler.getInstance().getsAreaList();
	}

	@Override
	public Transport getTranspot(String date, String time, String truckId) {
		return TransportHandler.getInstance().getTranspot(date, time, truckId);
	}

	@Override
	public ArrayList<driver> getAllDrivers() {
		return DriverHandler.getInstance().getAllDrivers();
	}

	@Override
	public ArrayList<station> getAllStations() {
		return StationHandler.getInstance().getAllStations();
	}

	@Override
	public ArrayList<truck> getAllTrucks() {
		return TruckHandler.getInstance().getAllTrucks();
	}

	@Override
	public ArrayList<Transport> getAllTransport() {
		return TransportHandler.getInstance().getAllTransport();
	}

	@Override
	public boolean insertDriver(String id, String license_type) {
		return DriverHandler.getInstance().insertDriver(id, license_type);
	}

	@Override
	public boolean insertArea(String name) {
		return AreaHandler.getInstance().insertArea(name);
	}

	@Override
	public boolean insertTruck(String id, String model, String color,
			String net_weight, String max_weight, String licenseDeg) {
		return TruckHandler.getInstance().insertTruck(id, model, color, net_weight, max_weight, licenseDeg);
	}

	@Override
	public boolean insertStation(String address, String phone_Number,
			String name, String areasname) {
		return StationHandler.getInstance().insertStation(address, phone_Number, name, areasname);
	}



	@Override
	public boolean deleteTransport(String date, String leaving_time,
			String truck_id) {
		return TransportHandler.getInstance().deleteTransport(date, leaving_time, truck_id);
	}

	@Override
	public boolean deleteTrack(String id) {
		return TruckHandler.getInstance().deleteTrack(id);
	}

	@Override
	public boolean deleteDriver(String id) {
		return DriverHandler.getInstance().deleteDriver(id);
	}

	@Override
	public boolean deleteArea(String name) {
		return AreaHandler.getInstance().deleteArea(name);
	}

	@Override
	public boolean deleteStation(String address) {
		return StationHandler.getInstance().deleteStation(address);
	}

	@Override
	public boolean updateTruck(String truckId, String newData, int option) {
		return TruckHandler.getInstance().updateTruck(truckId, newData, option);
	}

	@Override
	public boolean updateDriver(String driverId, String newData) {
		return DriverHandler.getInstance().updateDriver(driverId, newData);
	}

	@Override
	public boolean updateStation(String address, String newData, int option) {
		return StationHandler.getInstance().updateStation(address, newData, option);
	}

	@Override
	public boolean updateTransport(String date, String time, String truckId,
			String newData, int option) {
		return TransportHandler.getInstance().updateTransport(date, time, truckId, newData, option);
	}

	@Override
	public boolean multipleArea(ArrayList<String> stationList) {
		return AreaHandler.getInstance().multipleArea(stationList);
	}

	public boolean addOrderToTransport(Transport t, Order o){
		return TransportHandler.getInstance().addOrderToTransport(t, o);
	}

	public ArrayList<Order> ordersWithNoTrans() {
		return TransportHandler.getInstance().ordersWithNoTrans();
	}
}
