package Application;

import Entities.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersMenu {
    private  ConsoleMenu consoleMenu;
    private int selected;

    private static final String ordersCommands[] = {
            "Create new order",
            "Confirm order",
            "View order",
            "Back"
    };

    public OrdersMenu(ConsoleMenu consoleMenu){
        this.consoleMenu=consoleMenu;
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
        if(supplier == null)
            return;
        if(supplier.getContract() == null){
            System.out.printf("%s does not have a contract.\n", supplier.getName());
            return;
        }
        items = selectItems(supplier);
        totalPrice = calculatePrice(supplier, items);
        Order order = new Order(employee, supplier, totalPrice, items);
        consoleMenu.getDatabase().createOrder(order);
        System.out.println("Order created successfully!");
    }

    private void confirmOrder(){
        System.out.println("Please select the order you would like to confirm:");
        Order order = consoleMenu.getOrder();
        if(order == null) {
            System.out.println("No orders found.");
            return;
        }
        if(order.isArrived()){
            System.out.printf("Order %s is already confirmed.\n", order.getId());
            return;
        }
        consoleMenu.getDatabase().confirmOrder(order);
        System.out.printf("Order %s confirmed successfully!\n", order.getId());
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

    private Map<Product, Integer> selectItems(Supplier supplier){
        Map<Product, Integer> items = new HashMap<>();
        Product product;
        int amount;
        String input;
        System.out.println("Add products. Leave the product id field blank when you are done.");
        while(true){
            System.out.println("Please enter product id:");
            input = Utils.readLine();
            if(input.isEmpty()){
                if (items.isEmpty()) {
                    System.out.println("You must enter at least 1 product.");
                    continue;
                }else
                    break;
            }
            if((product = consoleMenu.getDatabase().getProductByID(input)) == null){
                System.out.printf("Product id %s does not exists in the system.\n", input);
                continue;
            }
            if(!supplier.sells(product)){
                System.out.printf("%s does not sell %s (id %s).\n",supplier.getName(),product.getName(), product.getId());
                continue;
            }
            System.out.println("Please enter amount:");
            while((amount = Utils.checkIntBounds(0)) == -1)
                System.out.println("Invalid input");
            items.put(product, amount);
        }
        return items;
    }

    private double calculatePrice(Supplier supplier, Map<Product, Integer> items){
        Contract contract = supplier.getContract();
        double totalPrice = 0;
        int totalAmount = 0;
        for(Product p : items.keySet()) {
            totalAmount += items.get(p);
            totalPrice += items.get(p) * supplier.getPrice(p);
        }
        double discount = getDiscount(contract.getBaseDiscount(), contract.getMaxDiscount(), totalAmount, contract.getMinDiscountLimit());
        totalPrice *= (1-discount);
        return totalPrice;
    }

    private double getDiscount(double baseDiscount, double maxDiscount, int amount, int minDiscountLimit) {
        return amount < minDiscountLimit ? 0 : Math.min(maxDiscount,((double)amount)*baseDiscount/((double)minDiscountLimit))/100;
    }
}
