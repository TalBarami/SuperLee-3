package Store;

import Inventory.screens.MainScreen;
import Suppliers.Application.Database.DatabaseImplementation;
import Suppliers.Application.UserInterface.ConsoleMenu;
import Suppliers.Application.UserInterface.ConsoleMenuImplementation;
import Suppliers.Application.Utils;
import Suppliers.Entities.Employee;

public class Store {
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private Employee connected;

    private ConsoleMenu suppliersConsoleMenu;
    private MainScreen inventoryMainScreen;

    private int selected;
    private static final String moduleSelect[] = {
            "Suppliers and Orders.",
            "Inventory.",
            "Exit."
    };
    public Store(){
        this.suppliersConsoleMenu = new ConsoleMenuImplementation(new DatabaseImplementation());
        this.inventoryMainScreen = new MainScreen();
    }

    public void Run(){
        System.out.println("Welcome to 'Super-Lee', please log-in to continue.");
        Login(MAX_LOGIN_ATTEMPTS);
        ModuleSelection();
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

        if((connected = suppliersConsoleMenu.getDatabase().checkCredentials(username,password)) != null) {
            System.out.println("You have successfully logged into the system!");
            suppliersConsoleMenu.setConnected(connected);
        } else{
            System.out.printf("Invalid user name or password. (%d attempts left)\n",attempts-1);
            Login(attempts-1);
        }
    }

    private void ModuleSelection(){
        while(true){
            selected = Utils.MenuSelect(moduleSelect);
            switch(selected){
                case 1:
                    suppliersConsoleMenu.RunStore();
                    break;
                case 2:
                    inventoryMainScreen.mainScreen();
                    break;
                case 3:
                    SQLiteConnector.getInstance().CloseConnection();
                    return;
            }
        }
    }


}
