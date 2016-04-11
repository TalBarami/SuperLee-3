package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Tal on 10/04/2016.
 */

public class ConsoleInterfaceImplementation implements ConsoleInterface {
    private static final int MAX_ATTEMPTS = 3;
    private static final String menuCommands[] = {
            "Manage suppliers.",
            "Manage orders.",
            "Exit."
    };
    private static final String suppliersCommands[] = {
            "Add new supplier.",
            "Edit supplier.",
            "Remove supplier",
            "Add supplier's contract.",
            "View supplier's information",
            "View supplier's products.",
            "View supplier's manufacturers.",
            "Back."
    };
    private static final String ordersCommands[] = {
            "Create new order",
            "View order",
            "Back"
    };
    private static final String supplierSearch[] = {
            "By supplier ID",
            "By supplier name",
            "Back"
    };
    private static final String orderSearch[] = {
            "By employee ID",
            "By supplier ID",
            "By order ID",
            "Back"
    };
    private BufferedReader buffer;
    private int selected;

    public ConsoleInterfaceImplementation(){
        buffer = new BufferedReader(new InputStreamReader(System.in));
        selected = 0;
        System.out.println("Welcome to 'Super-Lee', please log-in to continue.");
        RunStore();
    }

    public void RunStore(){
        Login(MAX_ATTEMPTS);
        MainMenu();
        System.exit(0);
    }

    private void Login(int attempts){
        if(attempts == 0)
            System.exit(0);
        System.out.println("User Name:");
        String username = readLine();
        System.out.println("Password:");
        String password = readLine();

        // TODO: verify access.
        if(true)
            System.out.println("Welcome to 'Super-Lee'!");
        else{
            System.out.printf("Invalid user name or password. (%d attempts left)\n",attempts-1);
            Login(attempts-1);
        }
    }

    private void MainMenu(){
        while(true){
            selected = MenuSelect(menuCommands);
            switch(selected){
                case 1:
                    SuppliersMenu();
                    break;
                case 2:
                    OrdersMenu();
                    break;
                case 3:
                    return;
            }
        }
    }

    private void SuppliersMenu(){
        while(true) {
            selected = MenuSelect(suppliersCommands);
            switch (selected) {
                case 1:
                    AddNewSupplier();
                    break;
                case 2:
                    EditSupplier();
                    break;
                case 3:
                    RemoveSupplier();
                    break;
                case 4:
                    AddContract();
                    break;
                case 5:
                    ViewSupplier();
                    break;
                case 6:
                    ViewSuppliersProducts();
                    break;
                case 7:
                    ViewSuppliersManufacturers();
                    break;
                case 8:
                    return;
            }
        }
    }

    private void OrdersMenu(){
        while(true) {
            selected = MenuSelect(ordersCommands);
            switch (selected) {
                case 1:
                    CreateNewOrder();
                    break;
                case 2:
                    ViewOrder();
                    break;
                case 3:
                    return;
            }
        }
    }

    private void CreateNewOrder(){

    }

    private void ViewOrder(){

    }

    private void AddNewSupplier(){

    }

    private void EditSupplier(){

    }

    private void RemoveSupplier(){

    }

    private void AddContract(){

    }

    private void ViewSupplier(){

    }

    private void ViewSuppliersProducts(){

    }

    private void ViewSuppliersManufacturers() {

    }

    private void SearchSupplier(){
        while(true) {
            selected = MenuSelect(supplierSearch);
            switch (selected) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    return;
            }
        }
    }

    private void SearchOrder(){
        while(true) {
            selected = MenuSelect(orderSearch);
            switch (selected) {
                case 1:
                    CreateNewOrder();
                    break;
                case 2:
                    break;
                case 3:

                    break;
                case 4:
                    return;
            }
        }
    }

    private int MenuSelect(String message, String[] arr){
        int selected = displayMenu(message, arr);
        while(selected < 1 || selected > arr.length) {
            System.out.println("Invalid input.");
            selected = displayMenu(message, arr);
        }
        return selected;
    }

    private int MenuSelect(String[] arr){
        return MenuSelect("", arr);
    }

    private int displayMenu(String message, String[] arr){
        if(message.isEmpty())
            message = "Please choose one of the following:";
        System.out.println(message);
        for (int i = 0; i < arr.length; i++)
            System.out.printf("%d. %s\n", i + 1, arr[i]);
        return parseInt(readLine());
    }

    private String readLine(){
        String s = null;
        try {
            s = buffer.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    private static int parseInt(String s){
        int retValue;
        try{
            retValue = Integer.parseInt(s);
        }catch(NumberFormatException e){
            retValue = -1;
        }
        return retValue;
    }
}


