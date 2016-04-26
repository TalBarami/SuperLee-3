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

    private ConsoleMenu consoleMenu;
    private int selected;
    private static final String moduleSelect[] = {
            "Manage suppliers.",
            "Manage inventory.",
            "Exit."
    };
    public Store(){
        this.consoleMenu=new ConsoleMenuImplementation(new DatabaseImplementation());
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

        if((connected = consoleMenu.getDatabase().checkCredentials(username,password)) != null) {
            System.out.println("You have successfully logged into the system!");
            consoleMenu.setConnected(connected);
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
                    consoleMenu.RunStore();
                    break;
                case 2:
                    MainScreen.mainScreen();
                    break;
                case 3:
                    return;
            }
        }
    }


}
