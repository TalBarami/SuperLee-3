package Employees_Transports.PL;

import Employees_Transports.BL.BLManager;
import Employees_Transports.Backend.Employee;
import Employees_Transports.Backend.station;
import Employees_Transports.Backend.Job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;


public class EmployeeMenu {
	private BLManager bl;
	private BufferedReader in;

	private void init(){
		boolean b = false;
		in = new BufferedReader(new InputStreamReader(System.in));
		String input;
		while(!b){
			try { 
				System.out.print("Please enter password: ");
				input=in.readLine();
				if(input.equals("1234")){
					System.out.println("Correct!");
					b=true;
				}
				else{
					System.out.println("Wrong!! Are you sure you need to be here?...");
				}
			} catch (IOException e) {
				System.out.println("Error reading!!");
			}
		}
	}
	public EmployeeMenu(BLManager bl){
		this.bl = bl;
	}

	public void run(){
		init();
		mainMenu();
	}

	public void printList(LinkedList l){
		for(int i=1;i<=l.size();i++){
			System.out.println("\n"+i+") "+l.get(i-1));
		}
	}
	public int isNumber(String s){
		int ans;
		try { 
			ans=Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			ans= -1; 
		} catch(NullPointerException e) {
			ans= -1;
		}
		return ans;
	}
	public double isNumberD(String s){
		double ans;
		try { 
			ans=Double.parseDouble(s); 
		} catch(NumberFormatException e) { 
			ans= (double)-1; 
		} catch(NullPointerException e) {
			ans= (double)-1;
		}
		return ans;
	}
	public boolean isNoNumber(String s){
		return s.matches("[a-zA-Z _]+")&&s.length()>0;
	}

	public void searchEmployee(){
		while(true){
			System.out.print("\n************************");
			System.out.print("\nEmployees -> Search\n");
			System.out.println("\nChoose an option: ");
			System.out.println("1) By ID\n2) By First Name\n3) By Last Name\n4) Back");
			String input;
			System.out.print("Your choice: ");
			try {
				input=in.readLine();
				switch(input){
				case "1":
					Employee e = null;
					System.out.print("\nPlease enter ID to search or c to cancel: ");
					input=in.readLine();
					if(input.equals("c"))
						return;
					else
					{
						e= bl.getEmployeeByID(input);
						if(e==null)
							System.out.println("\n\nEmployee with such ID does not exist!");
						else
							System.out.println("\n\n"+e);
					}
					break;
				case "2":
					LinkedList<Employee> eList = new LinkedList<Employee>();
					System.out.print("\nPlease enter First Name to search or c to cancel: ");
					input=in.readLine();
					if(input.equals("c"))
						return;
					else
					{
						eList= bl.getEmployeeByFirstName(input);
						System.out.println("\n");
						printList(eList);
						if(eList.size()==0)
							System.out.println("No results :(");
					}
					break;
				case "3":
					LinkedList<Employee> eList1 = new LinkedList<Employee>();
					System.out.print("\nPlease enter Last Name to search or c to cancel: ");
					input=in.readLine();
					if(input.equals("c"))
						return;
					else
					{
						eList1= bl.getEmployeeByLastName(input);
						System.out.println("\n");
						printList(eList1);
						if(eList1.size()==0)
							System.out.println("No results :(");
					}
					break;
				case "4":
					return;
				
					default:
						System.out.println("Incorrect selection.\nTry again");
						break;
						
				}
			}
			catch(Exception e)
			{
				System.out.println("ERROR!");
			}
		}

	}

	public void editEmploee(){
		while(true){
			boolean addB=true,bigL=true;
			String id="";
			String toEdit="";
			double salary=0;
			String input="";
			try{
				System.out.print("\n************************");
				System.out.print("\nEmployees -> Edit\n");
				while(addB){
					System.out.print("\nPlease enter ID or c to cancel: ");
					id=in.readLine();
					if(id.equals("c"))
						return;
					if(isNumber(id)>-1&&id.length()==9 && bl.getEmployeeByID(id)!=null)
						addB=false;
					else
						System.out.println("This is not an existing Employee ID!\nTry again\n");

				}
				while(bigL){
					System.out.println("\nChoose what to edit: ");
					System.out.println("1) First name\n2) Last name\n3) Salary\n4) BankAccount\n5) Edit another emplyoee\n6) Back to emploee menu");
					input=in.readLine();
					addB=true;
					switch(input){
					case "1":
						while(addB){
							System.out.print("\nPlease enter new first name or c to cancel: ");
							toEdit=in.readLine();
							if(toEdit.equals("c"))
								return;
							if(isNoNumber(toEdit)&&toEdit.length()<=20){
								addB=false;
								if(bl.editEmployeeFirstName(id,  toEdit)){

									System.out.println("Edit complete!");
								}
								else
									System.out.println("\nTry again");
							}
							else
								System.out.println("First name can contain only letters and up to 20 chercters!\ntry again\n");
						}
						break;
					case "2":
						while(addB){
							System.out.print("\nPlease enter new last name or c to cancel: ");
							toEdit=in.readLine();
							if(toEdit.equals("c"))
								return;
							if(isNoNumber(toEdit)&&toEdit.length()<=20){
								addB=false;
								if(bl.editEmployeeLastName(id, toEdit)){

									System.out.println("Edit complete!");
								}
								else
									System.out.println("\nTry again");
							}
							else
								System.out.println("Last name can contain only letters and up to 20 characters!\nTry again\n");
						}
						break;
					case "3":
						while(addB){
							System.out.print("\nPlease enter new salary or c to cancel: ");
							toEdit=in.readLine();
							if(toEdit.equals("c"))
								return;
							salary=isNumberD(toEdit);
							if(salary>0&&toEdit.length()<=14){
								addB=false;
								if(bl.editEmployeeSalary(id, salary)){

									System.out.println("Edit complete!");
								}
								else
									System.out.println("\nTry again");
							}
							else
								System.out.println("this is not a number!or the salary is to big!\nTry again\n");
						}
						break;
					case "4":
						while(addB){
							System.out.print("\nPlease enter new bank account number or c to cancel: ");
							toEdit=in.readLine();
							if(toEdit.equals("c"))
								return;
							if(isNumber(toEdit)>-1&&toEdit.length()<=20){
								addB=false;
								if(bl.editEmployeeBankAccount(id, toEdit)){

									System.out.println("Edit complete!");
								}
								else
									System.out.println("\nTry again");
							}
							else
								System.out.println("This is not a number! or the number is to long!\nTry again\n");

						}
						break;
					case "5":
						System.out.println("\n");
						bigL=false;
						break;
					case "6":
						System.out.println("\n\n\n");
						return;
					default:
						System.out.println("Incorrect selection.\nTry again");
						break;
					}
				}
			}
			catch(IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error reading!!");

			}
		}
	}
	public void emploeeMenu(){
		while(true){
			System.out.print("\n************************");
			System.out.print("\nEmployees\n");
			System.out.println("\nChoose an option: ");
			System.out.println("1) Show All\n2) Add\n3) Delete\n4) Edit\n5) Add Job to Employee\n6) Delete Job from Employee\n7) Search\n8) Back");
			String input;
			String[] add=new String[9];
			boolean addB=true,bigL=true,ca=true;
			double salary=0;
			int d=0;
			Date date=new Date();
			boolean isM=false;
			System.out.print("Your choice: ");
			try {
				input=in.readLine();
				switch(input){
				case "1":
					System.out.print("\n************************");
					System.out.print("\nEmployees -> Show All\n");
					printList(bl.getEmploees());
					break;
				case "2":
					System.out.print("\n************************");
					System.out.print("\nEmployees -> Add\n");
					while(bigL){
						while(addB){
							System.out.print("\nPlease enter ID or c to cancel: ");
							add[0]=in.readLine();
							if(add[0].equals("c"))
								return;
							if(isNumber(add[0])>-1&&add[0].length()==9)
								addB=false;
							else
								System.out.println("This is not a number! or the number is not 9 long!\nTry again\n");

						}
						addB=true;
						while(addB){
							System.out.print("\nPlease enter first name or c to cancel: ");
							add[1]=in.readLine();
							if(add[1].equals("c"))
								return;
							if(isNoNumber(add[1])&&add[1].length()<=20)
								addB=false;
							else
								System.out.println("First name can contain only letters and up to 20 chercters!\nTry again\n");
						}
						addB=true;
						while(addB){
							System.out.print("\nPlease enter last name or c to cancel: ");
							add[2]=in.readLine();
							if(add[2].equals("c"))
								return;
							if(isNoNumber(add[2])&&add[2].length()<=20)
								addB=false;
							else
								System.out.println("Last name can contain only letters and up to 20 chercters!\nTry again\n");
						}
						addB=true;
						while(addB){
							System.out.print("\nPlease enter bank account number or c to cancel: ");
							add[3]=in.readLine();
							if(add[3].equals("c"))
								return;
							if(isNumber(add[3])>-1&&add[3].length()<=20)
								addB=false;
							else
								System.out.println("This is not a number, or the number is too long!\nTry again\n");

						}
						addB=true;
						while(addB){
							System.out.print("\nPlease enter salary or c to cancel: ");
							add[4]=in.readLine();
							if(add[4].equals("c"))
								return;
							salary=isNumberD(add[4]);
							if(salary>0&&add[4].length()<=14)
								addB=false;
							else
								System.out.println("This is not a number, or the salary is too big!\nTry again\n");
						}
						addB=true;
						while(addB){
							System.out.print("\nPlease enter employment date (dd/mm/yyyy) or c to cancel: ");
							add[5]=in.readLine();
							if(add[5].equals("c"))
								return;
							String[] temp=add[5].split("/");
							int day=-1,mon=-1,year=-1;
							if(temp.length==3){
								day=isNumber(temp[0]);
								mon=isNumber(temp[1]);
								year=isNumber(temp[2]);
							}
							if((day>0&&day<32)&&(mon>0&&mon<13)&&(year>1900&&temp[2].length()<5)){
								date.setDate(day);
								date.setMonth(mon-1);
								date.setYear(year-1900);
								addB=false;
							}
							else
								System.out.println("Illegal date!");
						}

						if(bl.addEmploee(add[1], add[2], add[0], add[3], salary, date)){
							bigL=false;
							System.out.println("You just added "+add[1]+" to Employees!\n");
						}
						else
							System.out.println("\nTry again");

					}
					break;
				case "3":
					System.out.print("\n************************");
					System.out.print("\nEmployees -> Delete\n");
					while(addB){
						System.out.print("\nPlease enter ID you want to delete or c to cancel: ");
						input=in.readLine();
						if(isNumber(input)>-1&&input.length()==9){
							if(bl.deleteEmploee(input)){
								System.out.println("Delete completed!\n");
								addB=false;
							}
							else
							System.out.println("\nTry again");
						}
						else if(input.equals("c"))
							addB=false;
						else
							System.out.println("This is not a number, or the number is not 9 long!\nTry again\n");
					}
					break;
				case "4":
					editEmploee();
					break;
				case "5":
					System.out.print("\n************************");
					System.out.print("\nEmployees -> Add Job to Employee\n");
					while(ca){
						addB=true;
						System.out.println("\nPlease enter Employee ID or c to cancel: ");
						input=in.readLine();
						if(isNumber(input)>-1&&input.length()==9 && bl.getEmployeeByID(input)!=null){

							while(addB){
								System.out.println("jobs: ");
								LinkedList<Job> jobs=bl.getJobs();
								printList(jobs);
								System.out.println("\nPlease enter the number of job you want to add or c to cancel: ");
								add[0]=in.readLine();
								if(add[0].equals("c"))
									return;
								int i=isNumber(add[0]);
								if(i>0&&i<=jobs.size()){
									addB=false;
									if(bl.addJobToEmploee(input, jobs.get(i-1).getName())){
										System.out.println("Job added!");
										ca=false;
									}
									else
										System.out.println("\nTry again");
								}
								else
									System.out.println("This is not a job!\nTry again");
							}
						}
						else if(input.equals("c"))
							ca=false;
						else
							System.out.println("This is an illegal ID!\nTry again\n");
					}
					break;
				case "6":
					System.out.print("\n************************");
					System.out.print("\nEmployees -> Delete Job from Employee\n");
					while(ca){
						addB=true;
						System.out.println("\nPlease enter Employee ID or c to cancel: ");
						input=in.readLine();
						if(isNumber(input)>-1&&input.length()==9 && bl.getEmployeeByID(input)!=null){

							while(addB){
								System.out.println("jobs: ");
								LinkedList<Job> jobs=bl.getEmployeeByID(input).getJobs();
								printList(jobs);
								System.out.println("\nPlease enter the number of job you want to delete or c to cancel: ");
								add[0]=in.readLine();
								if(add[0].equals("c"))
									return;
								int i=isNumber(add[0]);
								if(i>0&&i<=jobs.size()){
									addB=false;
									if(bl.deleteJobToEmployee(input, jobs.get(i-1).getName())){
										System.out.println("Job deleted!");
										ca=false;
									}
									else
										System.out.println("\nTry again");
								}
								else
									System.out.println("This is not a job!\nTry again");
							}
						}
						else if(input.equals("c"))
							addB=false;
						else
							System.out.println("This is an illegal ID!\nTry again\n");
					}
					break;
				case "8":
					System.out.println("\n\n\n\n");
					return;
				case "7":
					searchEmployee();
					break;
				default:
					System.out.println("Incorrect selection.\nTryagain\n\n");
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error reading!!");
			}
		}
	}
	public void shiftMenu(){
		while(true){
			System.out.print("\n************************");
			System.out.print("\nShifts\n");
			System.out.println("\nChoose an option: ");
			System.out.println("1) Show All\n2) Add\n3) Delete\n4) Back");
			String input;
			String[] add=new String[2];
			boolean addB=true,bigL=true;
			int d=0;
			Date date=new Date();
			boolean isM=false;
			String station="";
			System.out.print("Your choice: ");
			try {
				input=in.readLine();
				switch(input){
				case "1":
					System.out.print("\n************************");
					System.out.print("\nShifts -> Show All\n");
					printList(bl.getshifts());
					break;
				case "2":
					System.out.print("\n************************");
					System.out.print("\nShifts -> Add\n");
					while(bigL){
						while(addB){
							System.out.println("\nBranches: ");
							ArrayList<station> stationsTemp=bl.getAllStations();
							LinkedList<station> stations = new LinkedList<station>();
							for(int i=0;i<stationsTemp.size();i++)
								stations.add(stationsTemp.get(i));
							printList(stations);
							System.out.println("\nPlease enter the number of branch or c to cancel: ");
							add[0]=in.readLine();
							if(add[0].equals("c"))
								return;
							int i=isNumber(add[0]);
							if(i>0&&i<=stations.size()){
								addB=false;
								station = stations.get(i-1).getAddress();
							}
							else
								System.out.println("This is not a branch!\nTry again");
						}
						addB=true;
						while(addB){
							System.out.print("\nPlease enter shift date (dd/mm/yyyy) or c to cancel: ");
							add[0]=in.readLine();
							if(add[0].equals("c"))
								return;
							String[] temp=add[0].split("/");
							int day=-1,mon=-1,year=-1;
							if(temp.length==3){
								day=isNumber(temp[0]);
								mon=isNumber(temp[1]);
								year=isNumber(temp[2]);
							}
							if((day>0&&day<32)&&(mon>0&&mon<13)&&(year>1900&&temp[2].length()<5)){
								date.setDate(day);
								date.setMonth(mon-1);
								date.setYear(year-1900);
								addB=false;
							}
							else
								System.out.println("Illegal date!");
						}
						addB=true;
						while(addB){
							System.out.print("\nPlease enter y or n if it's a morning shift or c to cancel: ");
							add[1]=in.readLine();
							if(add[1].equals("c"))
								return;
							switch(add[1]){
							case "y":
								addB=false;
								isM=true;
								break;
							case "n":
								addB=false;
								isM=false;
								break;
							default:
								System.out.println("Unrecognized signature\nTry again\n");	
							}
						}
						if(bl.addShift(date, isM, station)){
							bigL=false;
							System.out.println("Shift added successfully!");
						}
						else
							System.out.println("\nTry again");
					}
					break;
				case "3":
					System.out.print("\n************************");
					System.out.print("\nShifts -> Delete\n");
					while(addB){
						System.out.println("\nBranches: ");
						ArrayList<station> stationsTemp=bl.getAllStations();
						LinkedList<station> stations = new LinkedList<station>();
						for(int i=0;i<stationsTemp.size();i++)
							stations.add(stationsTemp.get(i));
						printList(stations);
						System.out.println("\nPlease enter the number of branch or c to cancel: ");
						add[0]=in.readLine();
						if(add[0].equals("c"))
							return;
						int i=isNumber(add[0]);
						if(i>0&&i<=stations.size()){
							addB=false;
							station = stations.get(i-1).getAddress();
						}
						else
							System.out.println("This is not a branch!\nTry again");
					}
					addB=true;
					while(addB){
						System.out.print("\nPlease enter date you want to delete or c to cancel (dd/mm/yyyy): ");
						input=in.readLine();
						String[] temp=input.split("/");
						int day=-1,mon=-1,year=-1;
						if(temp.length==3){
							day=isNumber(temp[0]);
							mon=isNumber(temp[1]);
							year=isNumber(temp[2]);
						}
						if((day>0&&day<32)&&(mon>0&&mon<13)&&(year>1900&&temp[2].length()<5)){
							date.setDate(day);
							date.setMonth(mon-1);
							date.setYear(year-1900);
							while(addB){
								System.out.print("\nPlease enter y or n if it's a morning shift or enter c to cancel: ");
								add[1]=in.readLine();
								switch(add[1]){
								case "y":

									isM=true;
									if(bl.deleteshift(date, isM, station)){
										addB=false;
										System.out.println("Shift deleted successfully!");
									}
									else
										System.out.println("\nTry again");
									break;
								case "n":

									isM=false;
									if(bl.deleteshift(date, isM, station)){
										addB=false;
										System.out.println("Shift deleted successfully!");
									}
									else
										System.out.println("\nTry again");
									break;
								case "c":
									addB=false;
									break;
								default:
									System.out.println("Unrecognized signature\ntry again\n");	
								}
							}
						}
						else if(input.equals("c"))
							addB=false;
						else
							System.out.println("This is not a number, or the date is incorrect!\nTry again\n");
					}
					break;
				case "4":
					System.out.println("\n\n\n\n");
					return;
				default:
					System.out.println("Incorrect selection.\nTryagain\n\n");
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error reading!!");
			}
		}
	}
	public void shiftPrioritiestMenu(){
		while(true){
			System.out.print("\n************************");
			System.out.print("\nShift Priorities\n");
			System.out.println("\nChoose an option: ");
			System.out.println("1) Show All\n2) Add\n3) Delete\n4) Search\n5)Back");
			String input; 
			boolean isM=false;
			Date date=new Date();
			String[] add=new String[2];
			boolean addB=true,bigL=true;
			String station="";
			System.out.print("your chose: ");
			try {
				input=in.readLine();
				switch(input){
				case "1":
					System.out.print("\n************************");
					System.out.print("\nShift Priorities -> Show All\n");
					printList(bl.getshiftPriorities());
					break;
				case "2":
					while(bigL){
						System.out.print("\n************************");
						System.out.print("\nShift Priorities -> Add\n");
						addB=true;
						while(addB){
							System.out.println("\nBranches: ");
							ArrayList<station> stationsTemp=bl.getAllStations();
							LinkedList<station> stations = new LinkedList<station>();
							for(int i=0;i<stationsTemp.size();i++)
								stations.add(stationsTemp.get(i));
							printList(stations);
							System.out.println("\nPlease enter the number of branch or c to cancel: ");
							add[0]=in.readLine();
							if(add[0].equals("c"))
								return;
							int i=isNumber(add[0]);
							if(i>0&&i<=stations.size()){
								addB=false;
								station = stations.get(i-1).getAddress();
							}
							else
								System.out.println("This is not a branch!\nTry again");
						}
						addB=true;
						while(addB){
							System.out.print("\nPlease enter date you want to add to an employee (dd/mm/yyyy) or c to cancel: ");
							input=in.readLine();
							if(input.equals("c"))
								return;
							String[] temp=input.split("/");
							int day=-1,mon=-1,year=-1;
							if(temp.length==3){
								day=isNumber(temp[0]);
								mon=isNumber(temp[1]);
								year=isNumber(temp[2]);
							}
							if((day>0&&day<32)&&(mon>0&&mon<13)&&(year>1900&&temp[2].length()<5)){
								date.setDate(day);
								date.setMonth(mon-1);
								date.setYear(year-1900);
								addB=false;
							}
						}
						addB=true;
						while(addB){
							System.out.print("\nPlease enter y or n if its a morning shift or enter c to cancel: ");
							add[1]=in.readLine();
							switch(add[1]){
							case "y":
								addB=false;
								isM=true;
								break;
							case "n":
								addB=false;
								isM=false;
								break;
							case "c":
								addB=false;
								return;
							default:
								System.out.println("Unrecognized signature\nTry again\n");	
							}
						}
						addB=true;
						while(addB){
							System.out.print("\nPlease enter ID or c to cancel: ");
							add[0]=in.readLine();
							if(add[0].equals("c"))
								return;
							if(isNumber(add[0])>-1&&add[0].length()==9)
								addB=false;
							else
								System.out.println("This is not a number, or the number is not 9 long!\nTry again\n");
						}
						if(bl.addShiftPriorities(date, isM, add[0], station)){
							bigL=false;
							System.out.println("Priority ended successfully!");
						}
						else
							System.out.println("\nTry again");
					}
					break;
				case "3":
					while(bigL){
						System.out.print("\n************************");
						System.out.print("\nShift Priorities -> Delete\n");
						while(addB){
							System.out.println("\nBranches: ");
							ArrayList<station> stationsTemp=bl.getAllStations();
							LinkedList<station> stations = new LinkedList<station>();
							for(int i=0;i<stationsTemp.size();i++)
								stations.add(stationsTemp.get(i));
							printList(stations);
							System.out.println("\nPlease enter the number of branch or c to cancel: ");
							add[0]=in.readLine();
							if(add[0].equals("c"))
								return;
							int i=isNumber(add[0]);
							if(i>0&&i<=stations.size()){
								addB=false;
								station = stations.get(i-1).getAddress();
							}
							else
								System.out.println("This is not a branch!\nTry again");
						}
						addB=true;
						while(addB){
							System.out.print("\nPlease enter date you want to delete from an employee (dd/mm/yyyy) or c to cancel: ");
							input=in.readLine();
							if(input.equals("c"))
								return;
							String[] temp=input.split("/");
							int day=-1,mon=-1,year=-1;
							if(temp.length==3){
								day=isNumber(temp[0]);
								mon=isNumber(temp[1]);
								year=isNumber(temp[2]);
							}
							if((day>0&&day<32)&&(mon>0&&mon<13)&&(year>1900&&temp[2].length()<5)){
								date.setDate(day);
								date.setMonth(mon-1);
								date.setYear(year-1900);
								addB=false;
							}
						}
						addB=true;
						while(addB){
							System.out.print("\nPlease enter y or n if it's a morning shift or c to cancel: ");
							add[1]=in.readLine();
							if(add[1].equals("c"))
								return;
							switch(add[1]){
							case "y":
								addB=false;
								isM=true;
								break;
							case "n":
								addB=false;
								isM=false;
								break;
							default:
								System.out.println("Unrecognized signature\ntry again\n");	
							}
						}
						addB=true;
						while(addB){
							System.out.print("\nPlease enter ID of employee you want to delete or c to cancel: ");
							add[0]=in.readLine();
							if(isNumber(add[0])>-1&&add[0].length()==9){
								addB=false;
								if(bl.deleteShiftPriorities(date, isM, add[0], station)){
									bigL=false;
									System.out.println("shift delete complete!");
								}
								else
									System.out.println("\ntry again");
							}
							else if(add[0].equals("c"))
								addB=false;
							else
								System.out.println("This is not a number, or the number is not 9 long!\nTry again\n");
						}
					}
					break;
				case "4":
					System.out.print("\n************************");
					System.out.print("\nShift Piorities -> Search\n");
					boolean addB1=true;
					while(bigL){
						while(addB){
							System.out.println("\nBranches: ");
							ArrayList<station> stationsTemp=bl.getAllStations();
							LinkedList<station> stations = new LinkedList<station>();
							for(int i=0;i<stationsTemp.size();i++)
								stations.add(stationsTemp.get(i));
							printList(stations);
							System.out.println("\nPlease enter the number of branch or c to cancel: ");
							add[0]=in.readLine();
							if(add[0].equals("c"))
								return;
							int i=isNumber(add[0]);
							if(i>0&&i<=stations.size()){
								addB=false;
								station = stations.get(i-1).getAddress();
							}
							else
								System.out.println("This is not a branch!\nTry again");
						}
						addB=true;
						while(addB1){
							System.out.print("\nPlease enter date (dd/mm/yyyy) or c to cancel: ");
							input=in.readLine();
							if(input.equals("c"))
								return;
							String[] temp=input.split("/");
							int day=-1,mon=-1,year=-1;
							if(temp.length==3){
								day=isNumber(temp[0]);
								mon=isNumber(temp[1]);
								year=isNumber(temp[2]);
							}
							if((day>0&&day<32)&&(mon>0&&mon<13)&&(year>1900&&temp[2].length()<5)){
								date.setDate(day);
								date.setMonth(mon-1);
								date.setYear(year-1900);
								addB1=false;
							}
							else
							{
								System.out.println("Illegal input. Try Again.");
							}
						}
						addB1=true;
						while(addB1){
							System.out.print("\nPlease enter y or n if its a morning shift or c to cancel: ");
							add[1]=in.readLine();
							if(add[1].equals("c"))
								return;
							switch(add[1]){
							case "y":
								addB1=false;
								isM=true;
								break;
							case "n":
								addB1=false;
								isM=false;
								break;
							default:
								System.out.println("Unrecognized signature\nTry again\n");	
							}
						}
						addB1=true;
						bigL=false;
						System.out.println("\n\n"+bl.getShiftPriorities(date, isM, station));
					}
					break;
				case "5":
					System.out.println("\n\n\n\n");
					return;
				default:
					System.out.println("Incorrect selection.\nTry again\n\n");
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error reading!!");
			}
		}
	}
	public void shifArrngementMenu(){
		while(true){
			System.out.print("\n************************");
			System.out.print("\nShift Arrangements\n");
			System.out.println("\nChoose an option: ");
			System.out.println("1) Show All\n2) Add\n3) Delete\n4) Search\n5) Back");
			String input;
			Date date=new Date();
			boolean addB1=true,addB2=true,isM=true,bigL=true;
			String[] add=new String[4];
			String id="";
			String job="";
			String station ="";
			System.out.print("Your choice: ");
			try {
				input=in.readLine();
				switch(input){
				case "1":
					System.out.print("\n************************");
					System.out.print("\nShift Arrangements -> Show All\n");
					printList(bl.getshifArrngement());
					break;
				case "2":
					System.out.print("\n************************");
					System.out.print("\nShift Arrangements -> Add\n");
					while(addB1){
						System.out.println("\nBranches: ");
						ArrayList<station> stationsTemp=bl.getAllStations();
						LinkedList<station> stations = new LinkedList<station>();
						for(int i=0;i<stationsTemp.size();i++)
							stations.add(stationsTemp.get(i));
						printList(stations);
						System.out.println("\nPlease enter the number of branch or c to cancel: ");
						add[0]=in.readLine();
						if(add[0].equals("c"))
							return;
						int i=isNumber(add[0]);
						if(i>0&&i<=stations.size()){
							addB1=false;
							station = stations.get(i-1).getAddress();
						}
						else
							System.out.println("This is not a branch!\nTry again");
					}
					addB1=true;
					while(addB1){
						System.out.print("\nPlease enter date you want to add to an employee (dd/mm/yyyy) or c to cancel: ");
						input=in.readLine();
						if(input.equals("c"))
							return;
						String[] temp=input.split("/");
						int day=-1,mon=-1,year=-1;
						if(temp.length==3){
							day=isNumber(temp[0]);
							mon=isNumber(temp[1]);
							year=isNumber(temp[2]);
						}
						if((day>0&&day<32)&&(mon>0&&mon<13)&&(year>1900&&temp[2].length()<5)){
							date.setDate(day);
							date.setMonth(mon-1);
							date.setYear(year-1900);
							addB1=false;
						}
					}
					addB1=true;
					while(addB1){
						System.out.print("\nPlease enter y or n if it's a morning shift or c to cancel: ");
						add[1]=in.readLine();
						if(add[1].equals("c"))
							return;
						switch(add[1]){
						case "y":
							addB1=false;
							isM=true;
							break;
						case "n":
							addB1=false;
							isM=false;
							break;
						default:
							System.out.println("Unrecognized signature\nTry again\n");	
						}
					}
					addB1=true;
					LinkedList<Employee> empL=bl.getManagers(date, isM, station);
					if(!bl.hasManager(date, isM, station))
						while(addB1){
							System.out.println("\nShift managers available for the shift: \n");
							printList(empL);
							System.out.print("\nPlease choose a shift manager or c to cancel: ");
							input=in.readLine();
							if(input.equals("c"))
								return;
							int i=isNumber(input);
							if(i>0&&i<=empL.size()){
								id=empL.get(i-1).getID();
								job="Shift Manager";
								if(bl.addShifArrngement(date, isM, id, job, station)){
									addB1=false;
									System.out.println("Shift manager added!");
								}
								else
									System.out.println("\nTry again");

							}
							else
								System.out.println("Unrecognized order: You need to enter the number of the employee.\nTry again\n");
						}
					addB1=true;
					LinkedList<Job> jobs;
					while(addB1){
						jobs=bl.getAvailableJobs(date, isM, station);
						if(!jobs.isEmpty()){
							System.out.println("\nJobs available for the shift: \n");
							printList(jobs);
							System.out.print("Please choose a job you want to add to the shift or d if you are done with this shift: ");
							input=in.readLine();
							int i=isNumber(input);
							if(i>0&&i<=jobs.size()){
								job=jobs.get(i-1).getName();
								while(addB2){
									System.out.println("\nEmployees available for this shift that are "+job+"s: ");
									empL=bl.getAvailableEmployees(date, isM, jobs.get(i-1), station);
									printList(empL);
									System.out.print("Please choose an employee you want to add to the shift as "+job+": ");
									input=in.readLine();
									int j=isNumber(input);
									if(j>0&&j<=empL.size()){
										id=empL.get(j-1).getID();
										addB2=false;
										if(bl.addShifArrngement(date, isM, id, job, station)){
											System.out.println(job+" added!");
										}
										else
											System.out.println("\nTry again");

									}
									else
										System.out.println("Unrecognized order: You need to enter the number of the employee.\nTry again\n");
								}
							}
							else if(input.equals("d"))
								addB1=false;
							else
								System.out.println("Unrecognized order: You need to enter the number of the job or d.\nTry again\n");
						}
						else{
							System.out.println("\nThere are no available employees on this date.\n");
							addB1=false;
						}
					}


					break;
				case "3":
					System.out.print("\n************************");
					System.out.print("\nShift Arrangements -> Delete\n");
					while(bigL){
						while(addB1){
							System.out.println("\nBranches: ");
							ArrayList<station> stationsTemp=bl.getAllStations();
							LinkedList<station> stations = new LinkedList<station>();
							for(int i=0;i<stationsTemp.size();i++)
								stations.add(stationsTemp.get(i));
							printList(stations);
							System.out.println("\nPlease enter the number of branch or c to cancel: ");
							add[0]=in.readLine();
							if(add[0].equals("c"))
								return;
							int i=isNumber(add[0]);
							if(i>0&&i<=stations.size()){
								addB1=false;
								station = stations.get(i-1).getAddress();
							}
							else
								System.out.println("This is not a branch!\nTry again");
						}
						addB1=true;
						while(addB1){
							System.out.print("\nPlease enter date you want to delete from an employee (dd/mm/yyyy) or c to cancel: ");
							input=in.readLine();
							if(input.equals("c"))
								return;
							String[] temp=input.split("/");
							int day=-1,mon=-1,year=-1;
							if(temp.length==3){
								day=isNumber(temp[0]);
								mon=isNumber(temp[1]);
								year=isNumber(temp[2]);
							}
							if((day>0&&day<32)&&(mon>0&&mon<13)&&(year>1900&&temp[2].length()<5)){
								date.setDate(day);
								date.setMonth(mon-1);
								date.setYear(year-1900);
								addB1=false;
							}
							else
							{
								System.out.println("Illegal input. Try Again.");
							}
						}
		
						addB1=true;
						while(addB1){
							System.out.print("\nPlease enter y or n if its a morning shift or c to cancel: ");
							add[1]=in.readLine();
							if(add[1].equals("c"))
								return;
							switch(add[1]){
							case "y":
								addB1=false;
								isM=true;
								break;
							case "n":
								addB1=false;
								isM=false;
								break;
							default:
								System.out.println("Unrecognized signature\nTry again\n");	
							}
						}
						addB1=true;
						while(addB1){
							System.out.print("\nPlease enter ID of employee you want to delete or c to cancel: ");
							add[0]=in.readLine();
							if(isNumber(add[0])>-1&&add[0].length()==9){
								addB1=false;
								id=add[0];
								if(bl.deleteShifArrngement(date, isM, id, station)){
									bigL=false;
									System.out.println("Arrangement Deleted!");
								}
								else
									System.out.println("\nTry again");
							}
							else if(add[0].equals("c"))
								addB1=false;
							else
								System.out.println("This is not a number, or the number is not 9 long!\nTry again\n");
						}
					}
					break;
				case "4":
					System.out.print("\n************************");
					System.out.print("\nShift Arrangements -> Search\n");
					while(bigL){
						while(addB1){
							System.out.println("\nBranches: ");
							ArrayList<station> stationsTemp=bl.getAllStations();
							LinkedList<station> stations = new LinkedList<station>();
							for(int i=0;i<stationsTemp.size();i++)
								stations.add(stationsTemp.get(i));
							printList(stations);
							System.out.println("\nPlease enter the number of branch or c to cancel: ");
							add[0]=in.readLine();
							if(add[0].equals("c"))
								return;
							int i=isNumber(add[0]);
							if(i>0&&i<=stations.size()){
								addB1=false;
								station = stations.get(i-1).getAddress();
							}
							else
								System.out.println("This is not a branch!\nTry again");
						}
						addB1=true;
						while(addB1){
							System.out.print("\nPlease enter date (dd/mm/yyyy) or c to cancel: ");
							input=in.readLine();
							if(input.equals("c"))
								return;
							String[] temp=input.split("/");
							int day=-1,mon=-1,year=-1;
							if(temp.length==3){
								day=isNumber(temp[0]);
								mon=isNumber(temp[1]);
								year=isNumber(temp[2]);
							}
							if((day>0&&day<32)&&(mon>0&&mon<13)&&(year>1900&&temp[2].length()<5)){
								date.setDate(day);
								date.setMonth(mon-1);
								date.setYear(year-1900);
								addB1=false;
							}
							else
							{
								System.out.println("Illegal input. Try Again.");
							}
						}
						addB1=true;
						while(addB1){
							System.out.print("\nPlease enter y or n if its a morning shift or c to cancel: ");
							add[1]=in.readLine();
							if(add[1].equals("c"))
								return;
							switch(add[1]){
							case "y":
								addB1=false;
								isM=true;
								break;
							case "n":
								addB1=false;
								isM=false;
								break;
							default:
								System.out.println("Unrecognized signature\nTry again\n");	
							}
						}
						addB1=true;
						bigL=false;
						System.out.println("\n\n"+bl.getShiftArrangement(date, isM, station));
					}
					break;
				case "5":
					System.out.println("\n\n\n\n");
					return;
				default:
					System.out.println("Incorrect selection.\nTryagain\n\n");
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("error reading!!");
			}
		}
	}
	public void jobsMenu(){
		while(true){
			System.out.print("\n************************");
			System.out.print("\nJobs\n");
			System.out.println("\nChoose an option: ");
			System.out.println("1) Show All\n2) Add\n3) Delete\n4) Back");
			String input;
			String[] add=new String[2];
			boolean addB=true,bigL=true;
			System.out.print("Your choice: ");
			try {
				input=in.readLine();
				switch(input){
				case "1":
					System.out.print("\n************************");
					System.out.print("\nJobs -> Show All\n");
					printList(bl.getJobs());
					break;
				case "2":
					System.out.print("\n************************");
					System.out.print("\nJobs -> Add\n");
					while(bigL){
						while(addB){
							System.out.println("\nPlease enter job name or c to cancel: ");
							add[0]=in.readLine();
							if(add[0].equals("c"))
								return;
							if(isNoNumber(add[0]))
								addB=false;
							else
								System.out.println("The name for the job can contain only letters!\nTry again");
						}
						System.out.println("Please enter the requirments for the job or c to cancel: ");
						add[1]=in.readLine();
						if(add[1].equals("c"))
							return;
						if(bl.addJob(add[0], add[1])){
							bigL=false;
							System.out.println("Job added successfully!");
						}
						else
							System.out.println("\nTry again");
					}
					break;
				case "3":
					System.out.print("\n************************");
					System.out.print("\nJobs -> Delete\n");
					while(addB){
						System.out.println("\nPlease enter the name of the job you want to delete or c to cancel: ");
						input=in.readLine();
						if(isNoNumber(input)){

							if(bl.deleteJob(input)){
								addB=false;
								System.out.println("Job deleted successfully!");
							}
							else
								System.out.println("\nTry again");
						}
						else if(input.equals("c"))
							addB=false;
						else 
							System.out.println("The name can contain only letters!\nTry again");


					}

					break;
				case "4":
					System.out.println("\n\n\n\n");
					return;
				default:
					System.out.println("Incorrect selection.\nTryagain\n\n");
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error reading!!");
			}
		}
	}
	public void mainMenu(){
		while(true){
			System.out.print("\n************************");
			System.out.println("\nEMPLOYEE MANAGEMENT\nChoose an option: \n");
			System.out.println("1) Employees\n2) Shifts\n3) Shift priorities\n4) Shift arrangements\n5) Jobs\n6) Exit");
			String input;
			System.out.print("\nYour choice: ");
			try {
				input=in.readLine();
				switch(input){
				case "1":
					emploeeMenu();
					break;
				case "2":
					shiftMenu();
					break;
				case "3":
					shiftPrioritiestMenu();
					break;
				case "4":
					shifArrngementMenu();
					break;
				case "5":
					jobsMenu();
					break;
				case "6":
					System.out.println("\n\n\n\n");
					return;
				default:
					System.out.println("Incorrect selection.\nTryagain");
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("error reading!!");
			}
		}
	}

	public void endConnection(){
		bl.endConnection();
	}
}
