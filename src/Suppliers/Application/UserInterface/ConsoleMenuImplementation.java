package Suppliers.Application.UserInterface;

import Suppliers.Application.Database.Database;
import Suppliers.Application.Utils;
import Suppliers.Entities.DeliveryMethod;
import Suppliers.Entities.Order;
import Suppliers.Entities.Supplier;

import java.util.ArrayList;
import java.util.List;

public class ConsoleMenuImplementation implements ConsoleMenu {
    private Database database;
    private SuppliersMenu suppliersMenu;
    private OrdersMenu ordersMenu;
    private int selected;

    private static final String menuCommands[] = {
            "Manage suppliers.",
            "Manage orders.",
            "Back."
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
        suppliersMenu = new SuppliersMenu(this);
        ordersMenu = new OrdersMenu(this);
    }

    public void RunStore(){
        MainMenu();
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
        if(suppliers == null)
            return null;
        if(suppliers.size() == 1) {
            System.out.printf("Supplier found: %s\n", suppliers.get(0));
            return suppliers.get(0);
        }
        selected = Utils.MenuSelect("Suppliers found:", suppliers);
        return suppliers.get(selected);
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
        if(orders == null)
            return null;
        if(orders.size() == 1) {
            System.out.printf("Order found: %s\n", orders.get(0));
            return orders.get(0);
        }
        selected = Utils.MenuSelect("Orders found:", orders);
        return orders.get(selected);
    }

    public Order getWeeklyOrder(){
        List<Order> orders = searchOrder();
        List<Order> weeklyOrders = new ArrayList<>();
        if(orders == null)
            return null;
        for(Order o : orders)
            if(o.getDeliveryMethod().equals(DeliveryMethod.Weekly) && !o.arrived())
                weeklyOrders.add(o);
        if(weeklyOrders.isEmpty())
            return null;
        if(weeklyOrders.size() == 1) {
            System.out.printf("Order found: %s\n", weeklyOrders.get(0));
            return weeklyOrders.get(0);
        }
        selected = Utils.MenuSelect("Orders found:", weeklyOrders);
        return weeklyOrders.get(selected);
    }

    public Database getDatabase() {
        return database;
    }
}