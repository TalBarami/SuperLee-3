package Store;

import Employees_Transports.BL.BLManager;
import Employees_Transports.BL.BLManagerImpl;
import Employees_Transports.PL.EmployeeMenu;
import Employees_Transports.PL.TransportMenu;
import Inventory.screens.MainScreen;
import Suppliers.Application.Database.DatabaseImplementation;
import Suppliers.Application.UserInterface.ConsoleMenu;
import Suppliers.Application.UserInterface.ConsoleMenuImplementation;
import Suppliers.Application.Utils;

public class Store {
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    private ConsoleMenu suppliersConsoleMenu;
    private MainScreen inventoryMainScreen;
    private EmployeeMenu employeeMenu;
    private TransportMenu transportMenu;

    private int selected;


    private static final String moduleSelect[] = {
            "Suppliers and Orders.",
            "Inventory.",
            "Employees.",
            "Transports.",
            "Exit."
    };
    public Store(){
        this.suppliersConsoleMenu = new ConsoleMenuImplementation(new DatabaseImplementation());
        this.inventoryMainScreen = new MainScreen();
        BLManager bl = new BLManagerImpl();
        this.employeeMenu = new EmployeeMenu(bl);
        this.transportMenu = new TransportMenu(bl);
    }

    public void Run(){
        System.out.println("Welcome to 'Super-Lee', please log-in to continue.");
        //Login(MAX_LOGIN_ATTEMPTS);
        ModuleSelection();
    }


    /*private void Login(int attempts){
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
    }*/

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
                    employeeMenu.run();
                    break;
                case 4:
                    transportMenu.run();
                    break;
                case 5:
                    SQLiteConnector.getInstance().endConnection();
                    return;
            }
        }
    }


}
