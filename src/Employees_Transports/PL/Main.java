package Employees_Transports.PL;

import Employees_Transports.BL.BLManager;
import Employees_Transports.BL.BLManagerImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	
	

	public static void main(String[] args) {
		BLManager bl = new BLManagerImpl();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String input;
		boolean running = true; 
		while(running){
			System.out.println("************************");
			System.out.println("WELCOME TO SUPER-LI!");
			System.out.println("\nChoose an option: ");
			System.out.println("	1. Employee Management ");
			System.out.println("	2. Transport Mangement ");
			System.out.println("	3. Exit ");
			try {
				input = in.readLine();
				if(input.equals("1")){
					EmployeeMenu m=new EmployeeMenu(bl);
					Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
						public void run() {
							m.endConnection();
						}
					}));
				}
				else if(input.equals("2"))
				{
					TransportMenu m = new TransportMenu(bl);
					Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
						public void run() {
							m.endConnection();
						}
					}));
				}
				else if(input.equals("3")){
					running = false;
					bl.endConnection();
				}
			} catch (IOException e) {
				System.out.println("Error!");
			}
		}
	}


}

