package Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
    private Database database;
    private int selected;

    public ConsoleInterfaceImplementation(Database database){
        this.database = database;
        buffer = new BufferedReader(new InputStreamReader(System.in));
        RunStore();
    }

    public void RunStore(){
        System.out.println("Welcome to 'Super-Lee', please log-in to continue.");
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
        if(database.checkCredentials(username,password))
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
                case 1: // Suppliers
                    SuppliersMenu();
                    break;
                case 2: // Orders
                    OrdersMenu();
                    break;
                case 3: // Exit
                    return;
            }
        }
    }

    private void SuppliersMenu(){
        while(true) {
            selected = MenuSelect(suppliersCommands);
            switch (selected) {
                case 1: // New supplier
                    AddNewSupplier();
                    break;
                case 2: // Edit supplier
                    EditSupplier();
                    break;
                case 3: // Remove supplier
                    RemoveSupplier();
                    break;
                case 4: // Add contract
                    AddContract();
                    break;
                case 5: // View supplier
                    ViewSupplier();
                    break;
                case 6: // View products
                    ViewSuppliersProducts();
                    break;
                case 7: // View manufacturers
                    ViewSuppliersManufacturers();
                    break;
                case 8: // Back
                    return;
            }
        }
    }

    private void OrdersMenu(){
        while(true) {
            selected = MenuSelect(ordersCommands);
            switch (selected) {
                case 1: // New order
                    CreateNewOrder();
                    break;
                case 2: // View Order
                    ViewOrder();
                    break;
                case 3: // Back
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
                case 1: // By id
                    break;
                case 2: // By name
                    break;
                case 3: // Back
                    return;
            }
        }
    }

    private void SearchOrder(){
        while(true) {
            selected = MenuSelect(orderSearch);
            switch (selected) {
                case 1: // By id
                    break;
                case 2: // By employee
                    break;
                case 3: // By supplier
                    break;
                case 4: // Back
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
        System.out.println(message.isEmpty() ? "Please choose one of the following:" : message);
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


