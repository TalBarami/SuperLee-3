package Inventory.program;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

public class Util {

	private static Scanner reader = new Scanner(System.in);
	public static int readIntFromUser(int minBound, int maxBound)
	{
		int result = -1;
		while(result == -1)
		{
			String input = reader.next();
			while(!tryParseInt(input)){
				System.out.println("Your choice was invalid please choose again");
				input = reader.next();
			}
			
			result = Integer.parseInt(input);
			
			if(result >= minBound && (result <= maxBound || maxBound == -1))
				return result;
			else{
				System.out.println("Your choice was invalid please choose again");
				result = -1;
			}
				
		}
		return result;
			
	}
	
	private static boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}
	
	public static String readStringFromUser()
	{
		Scanner reader = new Scanner(System.in);
		return reader.nextLine();
	}
	
	public static String readDateFromUser(){
		Scanner reader = new Scanner(System.in);
		String date= reader.next();
		while(!tryParseDate(date))
		{
			System.out.println("Invalid format please enter again");
			date= reader.next();
		}
		return date;
	}
	
	private static boolean tryParseDate(String date) {   
	    	 DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	    	 try {
				java.util.Date result =  df.parse(date);
			} catch (ParseException e) {
				return false;
			}
	     return true;
	}

}

