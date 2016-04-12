package Application;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleMenuImplementation implements ConsoleMenu {
    private static final int MAX_ATTEMPTS = 3;

    private BufferedReader buffer;
    private Database database;
    private SuppliersMenu suppliersMenu;
    private OrdersMenu ordersMenu;
    private int selected;

    private static final String menuCommands[] = {
            "Manage suppliers.",
            "Manage orders.",
            "Exit."
    };

    public ConsoleMenuImplementation(Database database){
        this.database = database;
        suppliersMenu = new SuppliersMenu(database);
        ordersMenu = new OrdersMenu(database);
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
        String username = Utils.readLine();
        System.out.println("Password:");
        String password = Utils.readLine();

        if(database.checkCredentials(username,password))
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
                case 1: // Suppliers
                    suppliersMenu.DisplaySuppliersMenu();
                    break;
                case 2: // Orders
                    ordersMenu.DisplayOrdersMenu();
                    break;
                case 3: // Exit
                    return;
            }
        }
    }
}