package Application;

import Entities.Order;
import java.util.List;

public class OrdersMenu {
    private  ConsoleMenuImplementation consoleMenu;
    private Database database;
    private int selected;

    private static final String ordersCommands[] = {
            "Create new order",
            "View order",
            "Back"
    };

    public OrdersMenu(ConsoleMenuImplementation consoleMenu, Database database){
        this.consoleMenu=consoleMenu;
        this.database = database;
    }

    public void displayOrdersMenu(){
        while(true) {
            selected = Utils.MenuSelect(ordersCommands);
            switch (selected) {
                case 1: // New order
                    createNewOrder();
                    break;
                case 2: // View Order
                    viewOrder();
                    break;
                case 3: // Back
                    return;
            }
        }
    }

    private void createNewOrder(){
    }

    private void viewOrder(){
        List<Order> orders = consoleMenu.searchOrder();
        if(orders == null)
            return;
        System.out.println(orders.size() == 1 ? "Order found:\n" : "Orders found:\n");
        for(Order o : orders){
            System.out.printf("%s\n\n",o.toString());
        }
    }
}
