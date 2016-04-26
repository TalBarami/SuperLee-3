package Application;

import Entities.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class Utils {
    private static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
    private static int selected;

    public static <T> int MenuSelect(String message, T[] arr){
        while((selected = displayMenu(message, arr)) < 1 || selected > arr.length) {
            System.out.println("Invalid input.");
        }
        return selected;
    }

    public static <T> int MenuSelect(T[] arr){
        return MenuSelect("", arr);
    }

    private static <T> int displayMenu(String message, T[] arr){
        System.out.println(message.isEmpty() ? "Please choose one of the following:" : message);
        for (int i = 0; i < arr.length; i++)
            System.out.printf("%d. %s\n", i + 1, arr[i]);
        return parseInt(readLine());
    }

    public static <T> int MenuSelect(String message, List<T> list){
        while((selected = displayMenu(message, list)) < 1 || selected > list.size()) {
            System.out.println("Invalid input.");
        }
        return selected-1;
    }

    public static <T> int MenuSelect(List<T> list){
        return MenuSelect("", list);
    }

    private static <T> int displayMenu(String message, List<T> list){
        System.out.println(message.isEmpty() ? "Please choose one of the following:" : message);
        for (int i = 0; i < list.size(); i++)
            System.out.printf("%d. %s\n", i + 1, list.get(i));
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

    public static double parseDouble(String s){
        double retValue;
        try{
            retValue = Double.parseDouble(s);
        }catch(NumberFormatException e){
            retValue = -1;
        }
        return retValue;
    }

    public static int checkIntBounds(int min, int max){
        String input;
        int value;

        while(!(input = readLine()).isEmpty()){
            if((value = parseInt(input)) < min || value > max)
                System.out.println("Invalid input.");
            else
                return value;
        }
        return -1;
    }

    public static double checkDoubleBounds(int min, int max){
        String input;
        double value;

        while(!(input = readLine()).isEmpty()){
            if((value = parseDouble(input)) < min || value > max)
                System.out.println("Invalid input.");
            else
                return value;
        }
        return -1;
    }

    public static int checkIntBounds(int min){
        return checkIntBounds(min, Integer.MAX_VALUE);
    }

    public static double checkDoubleBounds(int min){
        return checkDoubleBounds(min, Integer.MAX_VALUE);
    }

    public static <T,K> boolean contains(Map<T, K> map, T item){
        for(T t : map.keySet())
            if(t.equals(item))
                return true;
        return false;
    }

    public static <T,K> K getItem(Map<T,K> map, T item){
        for(T t : map.keySet()){
            if(t.equals(item))
                return map.get(t);
        }
        throw new NullPointerException();
    }

    public static <T> void displayEnum(String input, T[] arr){
        selected = Utils.parseInt(input);
        while(selected<0 || selected >= arr.length){
            System.out.println("Invalid input.");
            for (int i = 0; i < arr.length; i++)
                System.out.printf("%d. %s\n", i, arr[i]);
            selected = Utils.parseInt(Utils.readLine());
        }
    }
}
