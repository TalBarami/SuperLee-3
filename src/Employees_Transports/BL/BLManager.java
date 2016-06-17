package Employees_Transports.BL;

import Employees_Transports.Backend.*;
import Suppliers.Entities.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;


public interface  BLManager {
	 boolean addJobToEmploee(String ID,String job);
	 
	 
	 LinkedList<Employee> getEmploees();
	 LinkedList<Shift> getshifts();
	 LinkedList<ShiftPriorities> getshiftPriorities();
	 LinkedList<ShiftArrangement> getshifArrngement();
	 LinkedList<Job> getJobs();
	 
	 boolean addEmploee(String firstName, String lastName, String id, String bankAccountNum, double salary, Date employmentDate);
	 boolean addShift(Date date, boolean isMorning, String station);
	 boolean addShiftPriorities(Date date, boolean isMorning, String id, String station);
	 boolean addShifArrngement(Date date, boolean isMorning, String id, String job, String station);
	 boolean addJob(String name, String req);
	 
	 boolean deleteEmploee(String id);
	 boolean deleteshift(Date date,boolean isM, String station);
	 boolean deleteShiftPriorities(Date date,boolean isM,String ID, String station);
	 boolean deleteShifArrngement(Date date,boolean isM,String ID, String station);
	 boolean deleteJob(String name);
	 boolean deleteJobToEmployee(String id, String job);
	 
	 LinkedList<Employee> getAvailableEmployees(Date date, boolean isM, Job j, String station);
	 // Returns a list of Employees that can do Job j on date, isM
	 
	 LinkedList<Job> getAvailableJobs(Date date, boolean isM, String station);
	 // Returns a list of Jobs that can be done on date, isM
	 // excluding "Shift_Manager"
	 
	 LinkedList<Employee> getManagers(Date date, boolean isM, String station);
	 // Returns a list of Employees that are available on date, isM
	 // and can be shift managers
	 
	 boolean editEmployeeFirstName(String id, String fname);
	 boolean editEmployeeLastName(String id, String lname);
	 boolean editEmployeeSalary(String id, double salary);
	 boolean editEmployeeBankAccount(String id, String bank);
	 
	 boolean hasManager(Date date, boolean isM, String station);
	 // Return 'true' if the give shift already has a manager or else otherwise
	 
	 Employee getEmployeeByID(String id);
	 LinkedList<Employee> getEmployeeByFirstName(String name);
	 LinkedList<Employee> getEmployeeByLastName(String name);
	 ShiftArrangement getShiftArrangement(Date date, boolean isM, String station);
	 ShiftPriorities getShiftPriorities(Date date, boolean isM, String station);
	 
	 driver getDriver(String id);
	 truck getTruck(String id);
	 station getStation(String address);
	 ArrayList<area> getsAreaList();
	 Transport getTranspot(String date,String time,String truckId);
	 ArrayList<driver> getAllDrivers();
	 ArrayList<station> getAllStations();
	 ArrayList<truck> getAllTrucks();
	 ArrayList<Transport> getAllTransport();
	 boolean insertDriver(String id,String license_type);
	 boolean insertArea(String name);
	 boolean insertTruck(String id, String model, String color, String net_weight, String max_weight,String licenseDeg);
	 boolean insertStation(String address, String phone_Number, String name, String areasname);
	 boolean deleteTransport(String date, String leaving_time,String truck_id);
	 boolean deleteTrack(String id);
	 boolean deleteDriver(String id);
	 boolean deleteArea(String name);
	 boolean deleteStation(String address);
	 boolean updateTruck(String truckId,String newData,int option);
	 boolean updateDriver(String driverId,String newData);
	 boolean updateStation(String address,String newData,int option);
	 boolean updateTransport(String date,String time,String truckId,String newData,int option);
	 boolean multipleArea(ArrayList<String> stationList);
	 boolean addOrderToTransport(Transport t, Order o);

	ArrayList<Order> ordersWithNoTrans();
	 
	 void endConnection();
	 
}
