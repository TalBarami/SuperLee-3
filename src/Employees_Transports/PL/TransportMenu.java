package Employees_Transports.PL;

import Employees_Transports.BL.BLManager;
import Employees_Transports.Backend.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TransportMenu {

	private BLManager bl;
	private BufferedReader in;

	public TransportMenu(BLManager bl){
		this.bl=bl;
		in=new BufferedReader(new InputStreamReader(System.in));
		boolean b = false;
		String input;
		while(!b){
			try { 
				System.out.print("Please enter password: ");
				input=in.readLine();
				if(input.equals("2016")){
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
		mainMenu();
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

	private void mainMenu(){
		while(true){
			System.out.print("\n************************");
			System.out.println("\nTRANSPORT MANAGEMENT\nChoose an option: \n");
			System.out.println("Add - 1");
			System.out.println("Delete - 2");
			System.out.println("Update - 3");
			System.out.println("Queries - 4");
			System.out.println("Exit - 5");


			String userChoise="";
			try {
				userChoise = in.readLine();
			} catch (IOException e) {
				System.out.println("wrong input,try again");
				return;
			}

			switch (userChoise){
			case "1":
				addMenu();
				break;
			case "2":
				deleteMenu();
				break;
			case "3":
				updateMenu(); 
				break;
			case "4":
				try {
					queriesMenu();
				} catch (IOException e) {
					System.out.println("wrong input,try again");
				}
				break;
			case "5":
				System.out.println("\n\n\n\n");
				return;
			default:
				System.out.println("wrong input,try again");
			}
		}
	}

	private void deleteMenu(){
		while(true){
			System.out.print("\n************************");
			System.out.print("\nDelete\n\n");
			System.out.println("WELCOME TO UPDATE MENU!");
			System.out.println("press the relevant digit:");
			System.out.println("Delete a transport- 1");
			System.out.println("Delete a driver - 2");
			System.out.println("Delete a station - 3");
			System.out.println("Delete a truck - 4");
			System.out.println("Delete an area - 5");
			System.out.println("Back to main menu - 6");

			String userChoise="";
			try {
				userChoise = in.readLine();
			} catch (IOException e) {
				System.out.println("wrong input,try again");
				return;
			}

			switch (userChoise){
			case "1":
				try {
					deleteTransport();
				} catch (IOException e1) {
					System.out.println("wrong input,try again");
				}
				break;
			case "2":
				try {
					deleteDriver();
				} catch (IOException e1) {
					System.out.println("wrong input,try again");
				}
				break;
			case "3":
				try {
					deleteStation();
				} catch (IOException e) {
					System.out.println("wrong input,try again");
				}
				break;
			case "4":
				try {
					deleteTruck();
				} catch (IOException e1) {
				}
				break;
			case "5":
				try {
					deleteArea();
				} catch (IOException e) {
					System.out.println("wrong input,try again");
				}
				break;
			case "6":
				System.out.println("\n\n\n\n");
				return;
			default:
				System.out.println("wrong input,try again");
			}
		}
	}



	private void deleteArea() throws IOException{
		System.out.print("\n************************");
		System.out.print("\nDelete -> Area\n\n");
		System.out.println("Enter the area's name: ");
		String name="";

		name = in.readLine();

		if (bl.deleteArea(name)){
			System.out.println("area has been removed");
		}
		else{ 
			System.out.println("wrong input,try again");
		}
	}



	private void deleteStation() throws IOException{
		System.out.print("\n************************");
		System.out.print("\nDelete -> Station\n\n");
		System.out.println("Enter the station's address: ");
		String add="";
		add = in.readLine();


		if (bl.deleteStation(add)){
			System.out.println("station has been removed");
		}
		else{ 
			System.out.println("wrong input,try again");
		}
	}

	private void deleteTruck() throws IOException{
		System.out.print("\n************************");
		System.out.print("\nDelete -> Truck\n\n");
		System.out.println("Enter the driver's id: ");
		String id="";
		id = in.readLine();


		if (bl.deleteTrack(id)){
			System.out.println("truck has been removed");
		}
		else{ 
			System.out.println("wrong input,try again");
		}
	}

	private void deleteDriver() throws IOException{
		System.out.print("\n************************");
		System.out.print("\nDelete -> Driver\n\n");
		System.out.println("Enter the driver's id: ");
		String id="";
		id = in.readLine();


		if (bl.deleteDriver(id)){
			System.out.println("driver has been removed");
		}
		else{ 
			System.out.println("wrong input,try again");
		}
		return;
	}

	private void deleteTransport() throws IOException{
		//		public boolean deleteTransport(String date, String leaving_time,String truck_id);
		System.out.print("\n************************");
		System.out.print("\nDelete -> Transport\n\n");
		System.out.print("\nInsert transport date (dd/mm/yyyy) or c to cancel: ");
		String date=in.readLine();
		if(date.equals("c"))
			return;
		String[] temp=date.split("/");
		int day=-1,mon=-1,year=-1;
		if(temp.length==3){
			day=isNumber(temp[0]);
			mon=isNumber(temp[1]);
			year=isNumber(temp[2]);
		}
		if(!((day>0&&day<32)&&(mon>0&&mon<13)&&(year>1900&&temp[2].length()<5))){
			System.out.println("Illegal date!");
			return;
		}
			

		System.out.println("Insert the hour of the transport in this format: HH");
		String timeUserHour=in.readLine();
		if(!(isNumber(timeUserHour)>0&& isNumber(timeUserHour)<24 && timeUserHour.length()==2)){
			System.out.println("Illegal hour!");
			return;
		}
		System.out.println("Insert the minutes of leaving time in this format: MM");
		String timeUserMin=in.readLine();
		if(!(isNumber(timeUserMin)>0&& isNumber(timeUserMin)<60 && timeUserMin.length()==2)){
			System.out.println("Illegal hour!");
			return;
		}
		String time=timeUserHour+":"+timeUserMin;

		System.out.println("Insert the truck's id: ");
		String tId="";

		tId = in.readLine();

		if (bl.deleteTransport(date,time, tId))
		{
			System.out.println("tranport has been removed");
		}
		else{ 
			System.out.println("Wrong input, try again.");
		}
	}

	private void queriesMenu() throws IOException{
		while(true){
			System.out.print("\n************************");
			System.out.print("\nQueries\n\n");
			System.out.println("WELCOME TO QUERIES MENU!");
			System.out.println("press the relevant digit:");
			System.out.println("get information about a transport - 1");//in complete
			System.out.println("get information about a driver- 2");
			System.out.println("get information about a station - 3");
			System.out.println("get information about a truck - 4");
			System.out.println("get area's list - 5");
			System.out.println("get all information about transports - 6");
			System.out.println("get all information about drivers - 7");
			System.out.println("get all information about stations - 8");
			System.out.println("get all information about trucks - 9");
			System.out.println("back to main menu - 10");

			String ch="";
			String userChoise="";

			userChoise = in.readLine();


			switch (userChoise){
			case "1":
				getTransport();
				break;
			case "2":
				getDriver();
				break;
			case "3":
				getStation();
				break;
			case "4":
				getTruck();
				break;			
			case "5":
				getAreaList();
				break;
			case "6":
				ArrayList<Transport> t= bl.getAllTransport();
				for (Transport i: t){
					System.out.println(i);
				}
				System.out.println("press any key to get back to queries menu");
				ch=in.readLine();
				break;
			case "7":
				ArrayList<driver> d= bl.getAllDrivers();
				if(d==null)
					System.out.println("NULL!!");
				else
					for (driver i: d){
						System.out.println(i);
					}
				System.out.println("press any key to get back to queries menu");
				ch=in.readLine();
				break;
			case "8":
				ArrayList<station> s= bl.getAllStations();
				for (station i: s){
					System.out.println(i);
				}
				System.out.println("press any key to get back to queries menu");
				ch=in.readLine();
				break;
			case "9":
				ArrayList<truck> tr= bl.getAllTrucks();
				for (truck i: tr){
					System.out.println(i);
				}
				System.out.println("press any key to get back to queries menu");
				ch=in.readLine();
				break;

			case "10":
				return;

			default:
				System.out.println("Wrong input, try again.");
			}
		}
	}

	private void getAreaList(){

		ArrayList<area> areaGot=bl.getsAreaList();

		if (areaGot!=null){
			for (area i:areaGot){
				System.out.println(i.toString());
			}
		}
		else{ 
			System.out.println("There was a problem during the query");
		}
		System.out.println("press any key to get back to queries menu");
		try {
			String j=in.readLine();
		} catch (IOException e) {
			System.out.println("Wrong input, try again.");
		}

	}


	private void getDriver() throws IOException{

		System.out.println("Enter the driver's id: ");

		String id=in.readLine();

		driver d=bl.getDriver(id);
		if (d!=null){
			System.out.println(d.toString());
		}
		else{ 
			System.out.println("wrong input,try again");
		}
	}


	private void getTruck() throws IOException{

		System.out.println("Enter the truck's id: ");
		String id=in.readLine();

		truck d=bl.getTruck(id);
		if (d!=null){
			System.out.println(d.toString());
		}
		else{ 
			System.out.println("Wrong input, try again.");
		}
	}


	private void getStation() throws IOException{
		String add="";
		System.out.println("Enter the station's address: ");
		try {
			add=in.readLine();
		} catch (IOException e) {
			System.out.println("Wrong input.");
			getStation();
		}

		System.out.println(add);

		station d=bl.getStation(add);
		if (d!=null){
			System.out.println(d.toString());
		}
		else{ 
			System.out.println("Wrong input, try again.");
		}
	}

	private void getTransport() throws IOException{
		System.out.print("\nInsert transport date (dd/mm/yyyy) or c to cancel: ");
		String date=in.readLine();
		if(date.equals("c"))
			return;
		String[] temp=date.split("/");
		int day=-1,mon=-1,year=-1;
		if(temp.length==3){
			day=isNumber(temp[0]);
			mon=isNumber(temp[1]);
			year=isNumber(temp[2]);
		}
		if(!((day>0&&day<32)&&(mon>0&&mon<13)&&(year>1900&&temp[2].length()<5))){
			System.out.println("Illegal date!");
			return;
		}
			

		System.out.println("Insert the hour of the transport in this format: HH");
		String timeUserHour=in.readLine();
		if(!(isNumber(timeUserHour)>0&& isNumber(timeUserHour)<24 && timeUserHour.length()==2)){
			System.out.println("Illegal hour!");
			return;
		}
		System.out.println("Insert the minutes of leaving time in this format: MM");
		String timeUserMin=in.readLine();
		if(!(isNumber(timeUserMin)>0&& isNumber(timeUserMin)<60 && timeUserMin.length()==2)){
			System.out.println("Illegal hour!");
			return;
		}
		String time=timeUserHour+":"+timeUserMin;

		System.out.println("Enter the truck id: ");
		String truckId=in.readLine();
		Transport t=bl.getTranspot(date, time, truckId);
		if (t!=null){	
			System.out.println(t.toString());
		}
		else{ 
			System.out.println("Wrong input, try again.");
		}
	}


	private void addMenu(){
		while(true){
			System.out.print("\n************************");
			System.out.print("\nAdd\n\n");
			System.out.println("WELCOME TO ADD MENU!");
			System.out.println("press the relevant digit:");
			System.out.println("Add a driver - 1");
			System.out.println("Add a station - 2");
			System.out.println("Add a truck - 3");
			System.out.println("Add an area - 4");
			System.out.println("back to main menu - 5");


			String userChoise="";
			try {
				userChoise = in.readLine();
			} catch (IOException e) {
				System.out.println("Wrong input, try again.");
				return;
			}				

			switch (userChoise){
		/*	case "1":
				try {
					addTransport();
				} catch (IOException e) {
					System.out.println("Wrong input, try again.");
				}
				break; */
			case "1":
				try {
					addDriver();
				} catch (IOException e) {
					System.out.println("Wrong input, try again.");
				}
				break;
			case "2":
				try {
					addStation();
				} catch (IOException e) {
					System.out.println("Wrong input, try again.");
				}
				break;
			case "3":
				try {
					addTruck();
				} catch (IOException e) {
					System.out.println("Wrong input, try again.");
				}
				break;
			case "4":
				try {
					addArea();
				} catch (IOException e) {
					System.out.println("Wrong input, try again.");
				}
				break;
			case "5":
				return;
			default:
				System.out.println("Wrong input, try again.");
			}
		}
	}


	private void addArea() throws IOException{
		System.out.print("\n************************");
		System.out.print("\nAdd -> Area\n");
		System.out.println("Insert the area's name: ");
		String name="";
		name = in.readLine();

		while (!bl.insertArea(name)){
			System.out.println("Wrong input.");
			System.out.println("if you want to go back to the add menu, press 1. "
					+ "otherwise, press any key to continue");
			String chWrong=in.readLine();
			if (chWrong.equals("1")){
				return;
			}
			else{
				System.out.println("Insert the area's name: ");
				try {
					name=in.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("insert succeed");
		return;
	}

	private void addDriver() throws IOException{
		//		public boolean insertDriver(String id,String license_type, String name);
		System.out.print("\n************************");
		System.out.print("\nAdd -> Driver\n");
		System.out.println("Insert the driver's id: ");
		String id="";
		id = in.readLine();
		System.out.println("Insert the driver's license type: ");
		String lType="";
		lType = in.readLine();

		while (!bl.insertDriver(id,lType)){
			System.out.println("Wrong input, try again.");
			System.out.println("If you want to go back to the add menu, press 1. "
					+ "\nOtherwise, press any key to continue");
			String chWrong=in.readLine();
			if (chWrong.equals("1")){
				return;
			}
			else{
				System.out.println("Insert the driver's id: ");
				id=in.readLine();
				System.out.println("Insert the driver's license type: ");
				lType=in.readLine();
			}
		}
		System.out.println("insert succeed");
		return;
	}
	private void addTruck() throws IOException{
		System.out.print("\n************************");
		System.out.print("\nAdd -> Truck\n");
		System.out.println("Insert the truck's id: ");
		String id=in.readLine();
		System.out.println("Insert the truck's model: ");
		String model="";
		model = in.readLine();
		System.out.println("Insert the truck's color: ");
		String color="";
		color = in.readLine();

		System.out.println("Insert the truck's weigth: ");
		String weigth="";
		weigth = in.readLine();
		System.out.println("Insert the truck's max weigth: ");
		String mWeigth="";
		mWeigth = in.readLine();		
		System.out.println("Insert the truck's lidense degree: ");
		String lDeg="";
		lDeg = in.readLine();

		while (!bl.insertTruck(id, model, color, weigth, mWeigth, lDeg)){
			System.out.println("Wrong input, try again.");
			System.out.println("If you want to go back to the add menu, press 1. "
					+ "\nOtherwise, press any key to continue");
			String chWrong=in.readLine();
			if (chWrong.equals("1")){
				return;
			}
			else{
				System.out.println("Insert the truck's id: ");
				id=in.readLine();
				System.out.println("Insert the truck's model: ");
				model=in.readLine();
				System.out.println("Insert the truck's color: ");
				color=in.readLine();
				System.out.println("Insert the truck's weigth: ");
				weigth=in.readLine();
				System.out.println("Insert the truck's max weigth: ");
				mWeigth=in.readLine();
				System.out.println("Insert the truck's lidense degree: ");
				lDeg=in.readLine();


			}
		}
		System.out.println("insert succeed");
		return;
	}
	private void addStation() throws IOException{
		System.out.print("\n************************");
		System.out.print("\nAdd -> Station\n");
		System.out.println("Insert the station's address: ");
		String add="";
		add = in.readLine();
		System.out.println("Insert the station's phone-number: ");
		String pNumber="";
		pNumber = in.readLine();

		System.out.println("Insert the station's contact name: ");
		String name="";
		name = in.readLine();

		System.out.println("Insert the area name of the station: ");
		String areaName="";
		areaName = in.readLine();


		while (!bl.insertStation(add, pNumber, name, areaName)){
			System.out.println("Wrong input, try again.");
			System.out.println("If you want to go back to the add menu, press 1. "
					+ "\nOtherwise, press any key to continue");
			String chWrong=in.readLine();
			if (chWrong.equals("1")){
				return;
			}
			else{
				System.out.println("Insert the station's address: ");	
				add=in.readLine();
				System.out.println("Insert the station's phone-number: ");
				pNumber=in.readLine();
				System.out.println("Insert the station's name: ");
				name=in.readLine();
				System.out.println("Insert the area name of the station: ");
				areaName=in.readLine();
			}
		}
		System.out.println("insert succeed");
		return;
	}
	
	/*
	private void addTransport() throws IOException{
		//	public boolean insertTransport(String date, String leaving_time, String truck_id, String driver_id,
		//String source_address, ArrayList<pair<String,String>> station_ordernum)
		System.out.print("\n************************");
		System.out.print("\nAdd -> Transport\n");
		Pair<String, String> p;
		System.out.print("\nInsert transport date (dd/mm/yyyy) or c to cancel: ");
		String date=in.readLine();
		if(date.equals("c"))
			return;
		String[] temp=date.split("/");
		int day=-1,mon=-1,year=-1;
		if(temp.length==3){
			day=isNumber(temp[0]);
			mon=isNumber(temp[1]);
			year=isNumber(temp[2]);
		}
		if(!((day>0&&day<32)&&(mon>0&&mon<13)&&(year>1900&&temp[2].length()<5))){
			System.out.println("Illegal date!");
			return;
		}
			

		System.out.println("Insert the hour of the transport in this format: HH");
		String timeUserHour=in.readLine();
		if(!(isNumber(timeUserHour)>0&& isNumber(timeUserHour)<24 && timeUserHour.length()==2)){
			System.out.println("Illegal hour!");
			return;
		}
		System.out.println("Insert the minutes of leaving time in this format: MM");
		String timeUserMin=in.readLine();
		if(!(isNumber(timeUserMin)>0&& isNumber(timeUserMin)<60 && timeUserMin.length()==2)){
			System.out.println("Illegal hour!");
			return;
		}
		String time=timeUserHour+":"+timeUserMin;

		System.out.println("Insert the truck's id: ");
		String tId="";
		tId = in.readLine();

		System.out.println("Insert the driver's id: ");
		String dId="";
		dId = in.readLine();

		System.out.println("Insert transport's source address: ");
		String sAdd="";
		sAdd = in.readLine();

		ArrayList<String> allStations=new ArrayList<String>();
		allStations.add(sAdd);

		ArrayList<Pair<String,String>> station_ordernum=new ArrayList<Pair<String,String>>();
		System.out.println("Create a list of the transport's destinations. "
				+ "for each destination, insert its address and the order num.");
		System.out.println("in the end, insert 'done' as the order number and as the address");
		System.out.println("Insert a destination's address: ");
		String dName="";
		dName = in.readLine();

		allStations.add(dName);

		System.out.println("Insert its order number: ");
		String orderNum="";
		orderNum = in.readLine();



		while ((!(dName.equals("done")))&&(!(orderNum.equals("done")))){
			///////////////////////////////////////////////
			p=new Pair<String, String>(dName,orderNum);
			station_ordernum.add(p);
			System.out.println("Insert a destination's address: ");
			dName=in.readLine();
			allStations.add(dName);
			System.out.println("Insert its order number: ");
			orderNum=in.readLine();
		}

		//checks the transport is in the same area

		if (bl.multipleArea(allStations)){
			System.out.println("The transport you're inserting contains more than one area");
			System.out.println("if you want to go back to the add menu, press 1. "
					+ "otherwise, press any key to continue");
			String multCh=in.readLine();
			if (multCh.equals("1")){
				return;
			} 
		}



		while (!bl.insertTransport(date, time, tId, dId, sAdd, station_ordernum)){
			System.out.println("Wrong input, try again.");
			System.out.println("If you want to go back to the add menu, press 1. "
					+ "\nOtherwise, press any key to continue");
			String chWrong=in.readLine();
			if (chWrong.equals("1")){
				return;
			}
			else{

				allStations.clear();


				System.out.print("\nInsert transport date (dd/mm/yyyy) or c to cancel: ");
				date=in.readLine();
				if(date.equals("c"))
					return;
				temp=date.split("/");
				day=-1; mon=-1; year=-1;
				if(temp.length==3){
					day=isNumber(temp[0]);
					mon=isNumber(temp[1]);
					year=isNumber(temp[2]);
				}
				if(!((day>0&&day<32)&&(mon>0&&mon<13)&&(year>1900&&temp[2].length()<5))){
					System.out.println("Illegal date!");
					return;
				}
					

				System.out.println("Insert the hour of the transport in this format: HH");
				timeUserHour=in.readLine();
				if(!(isNumber(timeUserHour)>0&& isNumber(timeUserHour)<24 && timeUserHour.length()==2)){
					System.out.println("Illegal hour!");
					return;
				}
				System.out.println("Insert the minutes of leaving time in this format: MM");
				timeUserMin=in.readLine();
				if(!(isNumber(timeUserMin)>0&& isNumber(timeUserMin)<60 && timeUserMin.length()==2)){
					System.out.println("Illegal hour!");
					return;
				}
				time=timeUserHour+":"+timeUserMin;
				System.out.println("Insert the truck's id: ");
				tId=in.readLine();
				System.out.println("Insert the driver's id: ");
				dId=in.readLine();
				System.out.println("Insert transport's source address: ");

				sAdd=in.readLine();

				allStations.add(sAdd);

				station_ordernum=new ArrayList<Pair<String,String>>();
				System.out.println("Create a list of the transport's destinations. "
						+ "for each destination, insert its address and the order num.");
				System.out.println("in the end, insert 'done' as the order number and as the address ");
				System.out.println("Insert a destination's address: ");
				dName=in.readLine();

				allStations.add(dName);

				System.out.println("Insert its order number: ");
				orderNum=in.readLine();

				while ((!(dName.equals("done")))&&(!(orderNum.equals("done")))){
					/////////////////
					p=new Pair<String, String>(dName,orderNum);
					station_ordernum.add(p);
					System.out.println("Insert a destination address: ");
					dName=in.readLine();

					allStations.add(dName);

					System.out.println("Insert its order number: ");
					orderNum=in.readLine();
				}

				if (bl.multipleArea(allStations)){
					System.out.println("The transport you're inserting contains more than one area");
					System.out.println("if you want to go back to the add menu, press 1. "
							+ "otherwise, press any key to continue");
					String multCh=in.readLine();
					if (multCh.equals("1")){
						return;
					} 
					//else - continue, multch==1 
				}
			}
		}
		System.out.println("insert succeed");
		return;
	}
	
	*/



	private void updateMenu(){
		while(true){
			System.out.print("\n************************");
			System.out.print("\nUpdate\n");
			System.out.println("WELCOME TO UPDATE MENU!");
			System.out.println("press the relevant digit:");
			System.out.println("Update a transport- 1");
			System.out.println("Update a driver - 2");
			System.out.println("Update a station - 3");
			System.out.println("Update a truck - 4");
			System.out.println("Back to main menu - 5");


			String userChoise="";
			try {
				userChoise = in.readLine();
			} catch (IOException e) {
				System.out.println("Wrong input, try again.");
				return;
			}

			switch (userChoise){
			case "1":
				updateTransport();
				break;
			case "2":
				updateDriver();
				break;
			case "3":
				updateStation();
				break;
			case "4":
				updateTruck();
				break;
			case "5":
				return;
			default:
				System.out.println("Wrong input, try again.");
			}
		}
	}



	private void updateTransport(){
		//public boolean updateTransport(String date,String time,String truckId,String newData,int option);
		// 1-driverId 2-sorceStation1//
		while(true){
			System.out.print("\n************************");
			System.out.print("\nUpdate -> Transports\n");
			System.out.println("Welcome to update transport menu \n"
					+ "If you want to update a transport's driver's id, press 1. \n"
					+ "If you want to update a transport's source station, press 2. \n"
					+ "If you want to go back to update menu, press 9 \n");
			String updateCh="";
			try {
				updateCh = in.readLine();
			} catch (IOException e1) {
				System.out.println("Wrong input, try again.");
				return;
			}
			int updateChInt= isNumber(updateCh); 

			if ((updateChInt>3)||(updateChInt<1))
				return;
			else {
				try {
					specUpdateTransport(updateChInt);
				} catch (IOException e) {
					System.out.println("Wrong input, try again.");
				}
			}}
	}


	private void specUpdateTransport(int choise) throws IOException{
		System.out.print("\nInsert transport date (dd/mm/yyyy) or c to cancel: ");
		String date=in.readLine();
		if(date.equals("c"))
			return;
		String[] temp=date.split("/");
		int day=-1,mon=-1,year=-1;
		if(temp.length==3){
			day=isNumber(temp[0]);
			mon=isNumber(temp[1]);
			year=isNumber(temp[2]);
		}
		if(!((day>0&&day<32)&&(mon>0&&mon<13)&&(year>1900&&temp[2].length()<5))){
			System.out.println("Illegal date!");
			return;
		}
			

		System.out.println("Insert the hour of the transport in this format: HH");
		String timeUserHour=in.readLine();
		if(!(isNumber(timeUserHour)>0&& isNumber(timeUserHour)<24 && timeUserHour.length()==2)){
			System.out.println("Illegal hour!");
			return;
		}
		System.out.println("Insert the minutes of leaving time in this format: MM");
		String timeUserMin=in.readLine();
		if(!(isNumber(timeUserMin)>0&& isNumber(timeUserMin)<60 && timeUserMin.length()==2)){
			System.out.println("Illegal hour!");
			return;
		}
		String time=timeUserHour+":"+timeUserMin;

		System.out.println("Insert the truck's id: ");
		String tId="";
		tId = in.readLine();


		String updated="";


		switch (choise){
		case 1:
			System.out.println("Insert the updated transport's driver's id: ");
			updated=in.readLine();
			break;
		case 2:
			System.out.println("Insert the updated transport's source station: ");
			updated=in.readLine();
			break;

		default:
			System.out.println("Wrong input, try again.");
			return;
		}

		if (bl.updateTransport(date, time, tId, updated, choise))
			System.out.println("update succeed");
		else
			System.out.println("Wrong input, try again.");
		return;

	}




	private void updateStation(){
		//public boolean updateStation(String address,String newData,int option);// 1-phonenum, 2-contact 3-areaname
		while(true){
			System.out.print("\n************************");
			System.out.print("\nUpdate -> Stations\n");
			System.out.println("Welcome to update station menu \n"
					+ "If you want to update a station's phone number, press 1. \n"
					+ "If you want to update a station's contact , press 2. \n"
					+ "If you want to update a station's area name , press 3. \n"
					+ "If you want to go back to update menu, press 9 \n");
			String updateCh="";
			try {
				updateCh = in.readLine();
			} catch (IOException e1) {
				System.out.println("Wrong input, try again.");
				return;
			}

			
			int updateChInt= Integer.parseInt(updateCh); 

			if ((updateChInt>3)||(updateChInt<1))
				return;
			else {
				try {
					specUpdateStation(updateChInt);
				} catch (IOException e) {
					System.out.println("Wrong input, try again.");
				}
			}}
	}


	private void specUpdateStation(int choise) throws IOException{
		System.out.println("Insert the station's address: ");
		String id=in.readLine();
		String updated="";
		switch (choise){
		case 1:
			System.out.println("Insert the updated station's phone number: ");
			updated=in.readLine();
			break;
		case 2:
			System.out.println("Insert the updated station's contact: ");
			updated=in.readLine();
			break;
		case 3:
			System.out.println("Insert the updated station's area name: ");
			updated=in.readLine();
			break;

		default:
			System.out.println("Wrong input, try again.");
			specUpdateStation(choise);
		}

		if (bl.updateStation(id, updated, choise))
			System.out.println("update succeed");
		else
			System.out.println("Wrong input, try again.");
		return;
	}


	private void updateTruck(){
		//public boolean updateTruck(String truckId,String newData,int option); //1- modele 2- color 3- weight 4- max weight 5- licenseDeg
		while(true){
			System.out.print("\n************************");
			System.out.print("\nUpdate -> Trucks\n");
			System.out.println("Welcome to update truck menu \n"
					+ "If you want to update a truck's model, press 1. \n"
					+ "If you want to update a truck's color, press 2. \n"
					+ "If you want to update a truck's weight, press 3. \n"
					+ "If you want to update a truck's max weigth, press 4. \n"
					+ "If you want to update a truck's license degree, press 5. \n"
					+ "If you want to go back to update menu, press 9 \n");
			String updateCh="";
			try {
				updateCh = in.readLine();
			} catch (IOException e1) {
				System.out.println("Wrong input, try again.");
				return;
			}

			int updateChInt= Integer.parseInt(updateCh); 

			if ((updateChInt>5)||(updateChInt<1))
				return;
			else {
				try {
					specUpdateTruck(updateChInt);
				} catch (IOException e) {
					System.out.println("Wrong input, try again.");
				}
			}
		}
	}


	private void specUpdateTruck(int choise) throws IOException{
		System.out.println("Insert the truck's id: ");
		String id=in.readLine();
		String updated="";
		switch (choise){
		case 1:
			System.out.println("Insert the updated truck's model: ");
			updated=in.readLine();
			break;
		case 2:
			System.out.println("Insert the updated truck's color: ");
			updated=in.readLine();
			break;
		case 3:
			System.out.println("Insert the updated truck's weight: ");
			updated=in.readLine();
			break;
		case 4:
			System.out.println("Insert the updated truck's maximum weight: ");
			updated=in.readLine();
			break;
		case 5:
			System.out.println("Insert the updated truck's license degree: ");
			updated=in.readLine();
			break;

		default:
			System.out.println("Wrong input, try again.");
			return;
		}

		if (bl.updateTruck(id, updated, choise))
			System.out.println("update succeed");
		else
			System.out.println("Wrong input, try again. ");
		return;
	}



	private void updateDriver(){
		//		public boolean updateDriver(String driverId,String newData,int option); //1- licenseDeg 2-name
		while(true){
			System.out.print("\n************************");
			System.out.print("\nUpdate -> Drivers\n");
			System.out.println("Welcome to update driver menu \n"
					+ "If you want to update a driver's license deegree, press 1. \n"
					+ "If you want to go back to update menu, press 9 \n");
			String updateCh="";
			try {
				updateCh = in.readLine();
			} catch (IOException e1) {
				System.out.println("Wrong input, try again.");
			}
			switch(updateCh){
			case "1":
				try {
					specUpdateDriver();
				} catch (IOException e) {
					System.out.println("Wrong input, try again.");
				}
				break;
			case "9":
				return;
			default:
				System.out.println("Wrong input, try again.");
			}}
	}


	private void specUpdateDriver() throws IOException{
		System.out.println("Insert the driver's id: ");
		String id=in.readLine();
		String updated="";
		System.out.println("Insert the updated driver's license type: ");
		updated=in.readLine();
		if(bl.updateDriver(id, updated))
			System.out.println("update succeed");
		else
			System.out.println("Wrong input, try again.");
		return;
	}

	public void endConnection(){
		bl.endConnection();
	}

}



