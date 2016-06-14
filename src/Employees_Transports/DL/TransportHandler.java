package Employees_Transports.DL;
import Employees_Transports.Backend.*;
import Store.SQLiteConnector;
import Suppliers.Entities.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TransportHandler {
	private Connection c;
	private static TransportHandler instance = null;

	public TransportHandler() {
		c = SQLiteConnector.getInstance().getConnection();
	}

	public static TransportHandler getInstance(){
		if (instance == null)
			instance = new TransportHandler();
		return instance;
	}

	public Transport getTranspot(String date, String time, String truckId)
	{
		Statement stmt;
		try{
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Transport where TracktrackId="+truckId+" and Date="+"'"+date+"'"+" and leavingTime="+"'"+time+"'");
			String leaving_time=rs.getString("leavingTime");
			String dateans= rs.getString("Date");
			int truck_id=rs.getInt("TracktrackId");
			int driver_id=rs.getInt("DriverdriverId");
			String source_address=rs.getString("sourceStation");
			ArrayList<Pair<station, Integer>> temp = new ArrayList<Pair<station, Integer>>();

			Transport ans=new Transport(dateans, leaving_time, TruckHandler.getInstance().getruck(""+truck_id), DriverHandler.getInstance().getdriver(""+driver_id),  StationHandler.getInstance().getstation(source_address),temp);
			rs.close();
			stmt.close();

			stmt = c.createStatement();
			ResultSet rt = stmt.executeQuery("SELECT * FROM Transport_Station where TransportTracktrackId="+truckId+" and TransportDate="+"'"+date+"'"+" and TransportleavingTime="+"'"+time+"'");
			while(rt.next())
			{
				station temp1 = StationHandler.getInstance().getstation(rt.getString("Stationaddress"));
				int orderNum= rt.getInt("orderNum");
				ans.addStop(new Pair<station, Integer>(temp1, orderNum));
			}
			rt.close();
			stmt.close();
			return ans;
		}

		catch (SQLException e) {
			return null;
		}
	}

	public ArrayList<Transport> getAllTransport()
	{
		Statement stmt;
		ArrayList<Transport> ans = new ArrayList<Transport>();
		try{
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Transport");
			while(rs.next())
			{
				String leaving_time=rs.getString("leavingTime");
				String dateans= rs.getString("Date");
				int truck_id=rs.getInt("TracktrackId");
				int driver_id=rs.getInt("DriverdriverId");
				String source_address=rs.getString("sourceStation");
				ArrayList<Pair<station, Integer>> temp = new ArrayList<Pair<station, Integer>>();
				Transport tempt=new Transport(dateans, leaving_time, TruckHandler.getInstance().getruck(""+truck_id), DriverHandler.getInstance().getdriver(""+driver_id), StationHandler.getInstance().getstation(source_address),temp);
				ResultSet rt = stmt.executeQuery("SELECT * FROM Transport_Station where TransportTracktrackId="+truck_id+" and TransportDate="+"'"+dateans+"'"+" and TransportleavingTime="+"'"+leaving_time+"'");
				while(rt.next())
				{
					station temp1 = StationHandler.getInstance().getstation(rt.getString("Stationaddress"));
					int orderNum= rt.getInt("orderNum");
					tempt.addStop(new Pair<station, Integer>(temp1, orderNum));
				}

				ans.add(tempt);
			}
			rs.close();
			stmt.close();
			return ans;

		}
		catch (SQLException e) {
			return null;
		}
	}
	
	public boolean addTransport(Order o){
		// TODO
		return true;
	}
	
	
	
	public int getAvailableTruck(String date, String driverID){
		int ans = 0;
		driver d = DriverHandler.getInstance().getdriver(driverID);
		Statement s = null;
		ResultSet rs = null;
		try {
			s = c.createStatement();
			rs = s.executeQuery("SELECT trackId from Track where licenseDeg= '"+d.getLicense_type()+"' and trackId NOT IN"
					+ " (SELECT trackId from Transport where Date ='"+date+"')");
			while (rs.next()) {
				ans = rs.getInt("trackId");
			}
			rs.close();
			s.close();
		} catch (Exception e) {
		}
		return ans;
	}
	
/*	public Transport getRelevantTransport(Order o){
		Statement s = null;
		ResultSet rs = null;
		Transport ans = null;
		ArrayList<Transport> allTransports = getAllTransport();
		for(int i=0;i<allTransports.size();i++){
			if( )
		}
		
		
		return ans; 
	} */

/*	public boolean checkTransport(Order o){
		int completed = o.get;
		Date today=new Date();
		long ltime=today.getTime()+completed*24*60*60*1000;
		Date dayReady=new Date(ltime);
		for(int i=0;i<7;i++){
			long temp=dayReady.getTime()+i*24*60*60*1000;

		}

	}*/
	
	public boolean insertTransport(String date, String leaving_time, String truck_id, String driver_id, String source_address, ArrayList<Pair<String,String>> station_ordernum)
	{
		if(!ShiftHandler.getInstance().checkDriverInShift(date, leaving_time, driver_id, source_address))
		{
			System.out.println("*ERROR: This driver does not appear on this shift!");
			System.out.println();
			return false;

		}
		for(int i=0;i<station_ordernum.size();i++){
			if(!ShiftHandler.getInstance().checkIfStorekeeperExists(date, leaving_time, station_ordernum.get(i).getA()))
			{
				System.out.println("*ERROR: There is no Storekeeper for station "+station_ordernum.get(i).getA()+"!");
				System.out.println();

				return false;
			}
		}
		if(station_ordernum.size()<1)
		{
			System.out.println("*ERROR: No station to stop!");
			return false;
		}
		truck tempT = TruckHandler.getInstance().getruck(truck_id);
		driver drivert = DriverHandler.getInstance().getdriver(driver_id);
		if(tempT!=null&&drivert!=null&&tempT.licenseDeg>drivert.getLicense_type())
		{
			System.out.println("*ERROR: Unable to embed the driver to the transport mission due to lwo level licence type!");
		}

		Statement stmt;
		int j=0;
		try
		{
			stmt = c.createStatement();
			String sql = "INSERT INTO Transport (Date,leavingTime,TracktrackId,DriverdriverId,sourceStation) " +
					"VALUES ('"+date+"'"+",'"+leaving_time+"',"+truck_id+","+driver_id+",'"+source_address+"')"; 
			stmt.executeUpdate(sql);
		}
		catch (SQLException e) 
		{
			if(e.getMessage().contains("UNIQUE constraint")) System.out.println("*ERROR: Transport already exists, or the driver is assigned to another transport!");
			else System.err.println(e.getMessage());
			if(DriverHandler.getInstance().getdriver(driver_id)== null)  System.out.println("*ERROR: Driver dosen't exist");
			if(TruckHandler.getInstance().getruck(truck_id)== null)  System.out.println("*ERROR: Truck dosen't exist");
			if(StationHandler.getInstance().getstation(source_address)== null)  System.out.println("*ERROR: source station dosen't exist");
			return false;
		}
		try 
		{

			for(int i=0;i<station_ordernum.size();i++)
			{
				j=i;
				stmt = c.createStatement();
				String sql2 = "INSERT INTO Transport_Station (TransportDate,TransportleavingTime,TransportTracktrackId,Stationaddress,orderNum) " +
						"VALUES ('"+date+"'"+",'"+leaving_time+"',"+truck_id+","+"'"+station_ordernum.get(i).getA()+"','"+station_ordernum.get(i).getB()+"')"; 
				stmt.executeUpdate(sql2);
			}
		}


		catch (SQLException e) 
		{
			deleteTransport(date, leaving_time, truck_id);
			if(e.getMessage().equals("UNIQUE constraint failed: Transport_Station.orderNum"))  System.out.println("*ERROR: Order number "+station_ordernum.get(j).getB() +" already exists");
			else if(e.getMessage().equals("FOREIGN KEY constraint failed"))  System.out.println("*ERROR: Station "+station_ordernum.get(j).getA() +" dosen't exist");
			else System.out.println(e.getMessage());
			return false;
		}
		return true;
	}


	public boolean deleteTransport(String date, String leaving_time,String truck_id)
	{
		Statement stmt;
		try 
		{
			stmt = c.createStatement();
			if(getTranspot(date, leaving_time, truck_id) == null) throw new SQLException();
			String sql2 = "delete from transport where date ='"+date+"' and leavingTime='"+leaving_time+"' and TracktrackId ="+truck_id;
			stmt.executeUpdate(sql2);
			stmt.close();
		}
		catch (SQLException e) 
		{
			System.out.println("*ERROR: Transport dosen't exist");
			return false;
		}
		return true;
	}

	public boolean updateTransport(String date,String time,String truckId,String newData,int option){// 1-driverId 2-sorceStation1

		Statement stmt;
		try 
		{
			String sql ="";
			stmt = c.createStatement();                                                                                                                                                                                                          
			if(getTranspot(date, time, truckId)== null) throw new SQLException();

			if(option ==1)
			{ //DriverdriverId
				truck tempT = TruckHandler.getInstance().getruck(truckId);
				driver drivert = DriverHandler.getInstance().getdriver(newData);
				if(tempT!=null&&drivert!=null&&tempT.licenseDeg>drivert.getLicense_type())
				{
					System.out.println("*ERROR: Unable to embed the driver to the transport mission due to lwo level licence type ");
					return false;
				}
				sql ="UPDATE Transport set DriverdriverId= '"+newData +"' where TracktrackId="+truckId+" and Date="+"'"+date+"'"+" and leavingTime="+"'"+time+"'"; 
			}

			if(option ==2) sql ="UPDATE Transport set sourceStation= '"+newData +"' where TracktrackId="+truckId+" and Date="+"'"+date+"'"+" and leavingTime="+"'"+time+"'"; //sourceStation
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e) 
		{
			if(e.getMessage().equals("FOREIGN KEY constraint failed"))
			{
				if(option==1&&DriverHandler.getInstance().getdriver(newData)== null)  System.out.println("*ERROR: Driver dosen't exist");
				else if(option==2&&StationHandler.getInstance().getstation(newData)== null)  System.out.println("*ERROR: Station dosen't exist");
			}
			else System.out.println("*ERROR: Transport dosen't exist");

			return false;
		}
		return true;
	}



}
