package Application;

import Entities.Employee;
import Entities.Order;
import Entities.Supplier;

import java.util.List;

public class ConsoleMenuImplementation implements ConsoleMenu {
    private static final int MAX_ATTEMPTS = 3;

    private Database database;
    private SuppliersMenu suppliersMenu;
    private OrdersMenu ordersMenu;
    private int selected;
    private Employee connected;

    private static final String menuCommands[] = {
            "Manage suppliers.",
            "Manage orders.",
            "Exit."
    };

    private static final String supplierSearch[] = {
            "By supplier ID",
            "By supplier name",
            "Back"
    };

    private static final String orderSearch[] = {
            "By order ID",
            "By employee ID",
            "By supplier ID",
            "Back"
    };

    public ConsoleMenuImplementation(Database database){
        this.database = database;
        suppliersMenu = new SuppliersMenu(this,database);
        ordersMenu = new OrdersMenu(this,database);
        RunStore();
    }

    public void RunStore(){
        System.out.println("Welcome to 'Super-Lee', please log-in to continue.");
        Login(MAX_ATTEMPTS);
        MainMenu();
        System.exit(0);
    }

    private void Login(int attempts){
        if(attempts == 0) {
            System.out.println("Terminating.");
            System.exit(0);
        }
        System.out.println("User Name:");
        String username = Utils.readLine();
        System.out.println("Password:");
        String password = Utils.readLine();

        if((connected = database.checkCredentials(username,password)) != null)
            System.out.println("You have successfully logged in to the system!");
        else{
            System.out.printf("Invalid user name or password. (%d attempts left)\n",attempts-1);
            Login(attempts-1);
        }
    }

    private void MainMenu(){
        while(true){
            selected = Utils.MenuSelect(menuCommands);
            switch(selected){
                case 1:
                    suppliersMenu.displaySuppliersMenu();
                    break;
                case 2:
                    ordersMenu.displayOrdersMenu();
                    break;
                case 3:
                    return;
            }
        }
    }

    public List<Supplier> searchSupplier(){
        List<Supplier> suppliers = null;
        while(true) {
            selected = Utils.MenuSelect("How would you like to search?",supplierSearch);
            switch (selected) {
                case 1:
                    System.out.println("Please enter supplier ID:");
                    suppliers = database.findSupplierByID(Utils.readLine());
                    break;
                case 2:
                    System.out.println("Please enter supplier name:");
                    suppliers = database.findSuppliersByName(Utils.readLine());
                    break;
                case 3:
                    return null;
            }
            if(suppliers == null || suppliers.isEmpty())
                System.out.println("There were no suppliers matching this search.");
            else
                return suppliers;
        }
    }

    public Supplier getSupplier(){
        List<Supplier> suppliers = searchSupplier();
        return suppliers == null ? null : suppliers.get(0);
    }

    public List<Order> searchOrder(){
        List<Order> orders = null;
        while(true) {
            selected = Utils.MenuSelect("How would you like to search?",orderSearch);
            switch (selected) {
                case 1:
                    System.out.println("Please enter order ID:");
                    orders = database.findOrderByID(Utils.readLine());
                    break;
                case 2:
                    System.out.println("Please enter employee ID:");
                    orders = database.findOrdersByEmployee(Utils.readLine());
                    break;
                case 3:
                    System.out.println("Please enter supplier ID:");
                    orders = database.findOrdersBySupplier(Utils.readLine());
                    break;
                case 4:
                    return null;
            }
            if(orders == null || orders.isEmpty())
                System.out.println("There were no orders matching this search.");
            else
                return orders;
        }
    }

    public Order getOrder(){
        List<Order> orders = searchOrder();
        return orders == null ? null : orders.get(0);
    }

    public Employee getConnected(){
        return connected;
    }
}