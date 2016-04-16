package Application;

import Entities.Employee;
import Entities.Order;
import Entities.Product;
import Entities.Supplier;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersMenu {
    private  ConsoleMenuImplementation consoleMenu;
    private Database database;
    private int selected;

    private static final String ordersCommands[] = {
            "Create new order",
            "Confirm order",
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
                case 1:
                    createNewOrder();
                    break;
                case 2:
                    confirmOrder();
                    break;
                case 3:
                    viewOrder();
                    break;
                case 4:
                    return;
            }
        }
    }

    private void createNewOrder(){
        Employee employee = consoleMenu.getConnected();
        Supplier supplier;
        Map<Product, Integer> items;
        double totalPrice;

        System.out.println("Please select the supplier you would like to order from:");
        supplier = consoleMenu.getSupplier();
        if(supplier == null) {
            System.out.println("No suppliers found. terminating.");
            return;
        }
        items = selectItems();
        totalPrice = calculatePrice(supplier, items);
        //Order order = new Order("", employee, supplier, new Date(), false, totalPrice, items);
        //database.CreateOrder(order);
        System.out.println("Order created successfully!");
    }

    private void confirmOrder(){
        System.out.println("Please select the order you would like to confirm:");
        Order order = consoleMenu.getOrder();
        if(order == null) {
            System.out.println("No orders found. terminating.");
            return;
        }
        database.confirmOrder(order);
        System.out.println("Order confirmed successfully!");
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

    private Map<Product, Integer> selectItems(){
        Map<Product, Integer> items = new HashMap<>();
        Product product;
        int amount;
        System.out.println("Add products. Leave the product id field blank when you are done.");
        while(true){
            System.out.println("Please enter product id:");
            if((product = database.getProductByID(Utils.readLine())) == null) {
                if(items.isEmpty())
                    System.out.println("You must enter at least 1 product.");
                else
                    break;
            }
            System.out.println("Please enter amount:");
            amount = Utils.checkIntBounds(0);
            items.put(product, amount);
        }
        return items;
    }

    private double calculatePrice(Supplier supplier, Map<Product, Integer> items){
        Map<Product, Double> prices = supplier.getProducts();
        double totalPrice = 0;
        for(Product p : items.keySet())
            totalPrice += items.get(p) * prices.get(p);
        return totalPrice;
    }
}
