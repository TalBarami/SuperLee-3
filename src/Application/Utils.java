package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {
    private static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

    public static int MenuSelect(String message, String[] arr){
        int selected = displayMenu(message, arr);
        while(selected < 1 || selected > arr.length) {
            System.out.println("Invalid input.");
            selected = displayMenu(message, arr);
        }
        return selected;
    }

    public static int MenuSelect(String[] arr){
        return MenuSelect("", arr);
    }

    public static int displayMenu(String message, String[] arr){
        System.out.println(message.isEmpty() ? "Please choose one of the following:" : message);
        for (int i = 0; i < arr.length; i++)
            System.out.printf("%d. %s\n", i + 1, arr[i]);
        return parseInt(readLine());
    }

    public static String readLine(){
        String s = null;
        try {
            s = buffer.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static int parseInt(String s){
        int retValue;
        try{
            retValue = Integer.parseInt(s);
        }catch(NumberFormatException e){
            retValue = -1;
        }
        return retValue;
    }
}
