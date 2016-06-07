package Employees_Transports.Test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import Employees_Transports.Backend.Employee;
import Employees_Transports.Backend.Job;
import Employees_Transports.Backend.Shift;
import Employees_Transports.Backend.ShiftPriorities;
import Employees_Transports.DL.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DLManagerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddEmployee() {
		try {
			EmployeeHandler.getInstance().addEmployee("@Test", "@Test", "0", "1", 1, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000"));
			assertEquals(true, EmployeeHandler.getInstance().getEmployeeByID("0")!=null);
			EmployeeHandler.getInstance().deleteEmployee("0");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddShift() {
		try {
			AreaHandler.getInstance().insertArea("@TA");
			StationHandler.getInstance().insertStation("@Tester", "123456", "Test", "@TA");
			ShiftHandler.getInstance().addShift(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000"), false, "@Tester");
			Shift temp = null;
			LinkedList<Shift> all = ShiftHandler.getInstance().getAll();
			for(int i=0;i<all.size();i++){
				if(((Shift)(all.get(i))).getIsMorning()==false &&
						((Shift)(all.get(i))).getShiftDate().equals(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000")))
						temp = (Shift)(all.get(i));
			}
			assertEquals(true, temp!=null);
			AreaHandler.getInstance().deleteArea("@TA");
		} catch (ParseException e) {
		}

	}

	@Test

	public void testAddJob() {
		JobHandler.getInstance().addJob("@TestJob", "None");
		assertEquals(true, JobHandler.getInstance().getJobByName("@TestJob")!=null);
		JobHandler.getInstance().deleteJob("@TestJob");
	}

	@Test
	public void testAddJobToEmployee() {
		try {
			JobHandler.getInstance().addJob("@TestJob", "None");
			EmployeeHandler.getInstance().addEmployee("@Test", "@Test", "0", "1", 1, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000"));
			EmployeeHandler.getInstance().addJobToEmployee("0", "@TestJob");
			Employee temp = EmployeeHandler.getInstance().getEmployeeByID("0");
			Job j = null;
			for(int i=0;i<temp.getJobs().size();i++){
				if(temp.getJobs().get(i).getName().equals("@TestJob"))
					j= temp.getJobs().get(i);
			}
			assertEquals(true, j!=null);
			JobHandler.getInstance().deleteJob("@TestJob");
			EmployeeHandler.getInstance().deleteEmployee("0");
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void testEditEmployeeFirstName() {
		try {
			EmployeeHandler.getInstance().addEmployee("@Test", "@Test", "0", "1", 1, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000"));
			EmployeeHandler.getInstance().editEmployeeFirstName("0", "edit");
			Employee temp = EmployeeHandler.getInstance().getEmployeeByID("0");
			assertEquals("edit", EmployeeHandler.getInstance().getEmployeeByID("0").getFirstname());
			EmployeeHandler.getInstance().deleteEmployee("0");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEditEmployeeLastName() {
		try {
			EmployeeHandler.getInstance().addEmployee("@Test", "@Test", "0", "1", 1, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000"));
			EmployeeHandler.getInstance().editEmployeeLastName("0", "edit");
			Employee temp = EmployeeHandler.getInstance().getEmployeeByID("0");
			assertEquals("edit", EmployeeHandler.getInstance().getEmployeeByID("0").getLastname());
			EmployeeHandler.getInstance().deleteEmployee("0");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEditEmployeeSalary() {
		try {
			EmployeeHandler.getInstance().addEmployee("@Test", "@Test", "0", "1", 1, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000"));
			EmployeeHandler.getInstance().editEmployeeSalary("0", 30.5);
			assertTrue(EmployeeHandler.getInstance().getEmployeeByID("0").getSalary()==30.5);
			//assertEquals(30.5, dl.getEmployeeByID("0").getSalary());
			EmployeeHandler.getInstance().deleteEmployee("0");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEditEmployeeBankAccount() {
		try {
			EmployeeHandler.getInstance().addEmployee("@Test", "@Test", "0", "1", 1, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000"));
			EmployeeHandler.getInstance().editEmployeeBankAccount("0", "5465");
			assertEquals("5465", EmployeeHandler.getInstance().getEmployeeByID("0").getBankAccountNum());
			EmployeeHandler.getInstance().deleteEmployee("0");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddShiftPriorities() {
		try {
			AreaHandler.getInstance().insertArea("@TA");
			StationHandler.getInstance().insertStation("@Tester", "123456", "Test", "@TA");
			ShiftHandler.getInstance().addShift(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000"), false, "@Tester");
			EmployeeHandler.getInstance().addEmployee("@Test", "@Test", "0", "1", 1, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000"));
			ShiftHandler.getInstance().addShiftPriorities(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000"), false, "0", "@Tester");
			ShiftPriorities p = ShiftHandler.getInstance().getShiftPriorities(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000"), false, "@Tester");
			assertEquals(true, p.getEmployees().size()>0);
			EmployeeHandler.getInstance().deleteEmployee("0");
			AreaHandler.getInstance().deleteArea("@TA");
		} catch (ParseException e) {
		}
	}

	@Test
	public void testDeleteJob() {
		JobHandler.getInstance().addJob("test", "testing");
		assertNotNull(JobHandler.getInstance().getJobByName("test"));
		JobHandler.getInstance().deleteJob("test");
		assertNull(JobHandler.getInstance().getJobByName("test"));
	}


}
