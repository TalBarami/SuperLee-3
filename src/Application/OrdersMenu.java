package Application;

import Entities.Order;

import java.util.List;

public class OrdersMenu {
    private Database database;
    private int selected;

    private static final String ordersCommands[] = {
            "Create new order",
            "View order",
            "Back"
    };
    private static final String orderSearch[] = {
            "By order ID",
            "By employee ID",
            "By supplier ID",
            "Back"
    };

    public OrdersMenu(Database database){
        this.database = database;
    }

    public void DisplayOrdersMenu(){
        while(true) {
            selected = Utils.MenuSelect(ordersCommands);
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
        // TODO
    }

    private void ViewOrder(){
        List<Order> orders = SearchOrder();
        if(orders == null)
            return;
        System.out.println(orders.size() == 1 ? "Order found:\n" : "Orders found:\n");
        for(Order o : orders){
            System.out.printf("%s\n\n",o.toString());
        }
    }

    public List<Order> SearchOrder(){
        List<Order> orders = null;
        while(true) {
            selected = Utils.MenuSelect("How would you like to search?",orderSearch);
            switch (selected) {
                case 1: // By id
                    System.out.println("Please enter order ID:");
                    orders = database.FindOrderByID(Utils.readLine());
                    break;
                case 2: // By employee
                    System.out.println("Please enter employee ID:");
                    orders = database.FindOrdersByEmployee(Utils.readLine());
                    break;
                case 3: // By supplier
                    System.out.println("Please enter supplier ID:");
                    orders = database.FindOrdersBySupplier(Utils.readLine());
                    break;
                case 4: // Back
                    return null;
            }
            if(orders == null || orders.isEmpty())
                System.out.println("There were no orders matching this search.");
        }
    }
}
