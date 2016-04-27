package Suppliers.Application.UserInterface;

import Inventory.dbHandlers.ProductHandler;
import Inventory.dbHandlers.ProductStockHandler;
import Inventory.entities.ProductCatalog;
import Suppliers.Application.Utils;
import Suppliers.Entities.*;
import com.sun.xml.internal.bind.v2.TODO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersMenu {
    private  ConsoleMenu consoleMenu;
    private int selected;

    private static final String ordersCommands[] = {
            "Create new order",
            "Create restock order",
            "Confirm order",
            "Update weekly order",
            "Cancel weekly order",
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
                    createRestockOrder();
                    break;
                case 3:
                    confirmOrder();
                    break;
                case 4:
                    updateWeeklyOrder();
                    break;
                case 5:
                    cancelWeeklyOrder();
                    break;
                case 6:
                    viewOrder();
                    break;
                case 7:
                    return;
            }
        }
    }

    private void createNewOrder(){
        Employee employee = consoleMenu.getConnected();
        Supplier supplier;
        Map<ProductCatalog, Integer> items;
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

    private void createRestockOrder(){
        Map<ProductCatalog,Integer> products = consoleMenu.getDatabase().getMissingProducts();
        // String -representing the id of the supplier
        Map<Supplier,Map<ProductCatalog,Integer>> productsFromSuppliers=new HashMap<>();
        for(ProductCatalog product : products.keySet()){
            Supplier bestSupplier=getBestSupplierByProduct(product,products.get(product));
            if(bestSupplier == null){
                System.out.printf("WARNING: There are no registered suppliers that can supply %s (id: %d) which is out of stock.\n", product.get_name(), product.get_id());
            }else {
                if (!productsFromSuppliers.containsKey(bestSupplier))
                    productsFromSuppliers.put(bestSupplier, new HashMap<>());
                productsFromSuppliers.get(bestSupplier).put(product, products.get(product));
            }
        }
        if(productsFromSuppliers.isEmpty())
            System.out.println("There were no products to order.");
        else {
            // Make orders
            Order newOrder;
            String items;
            for (Supplier supp : productsFromSuppliers.keySet()) {
                items = "";
                newOrder = new Order(consoleMenu.getConnected(), supp, calculatePrice(supp, productsFromSuppliers.get(supp)), productsFromSuppliers.get(supp));
                consoleMenu.getDatabase().createOrder(newOrder);
                for(ProductCatalog p : newOrder.getItems().keySet())
                    items+=p.get_name()+", ";
                items = items.substring(0, items.length()-2);
                System.out.printf("%s received automatic re-stock order for %s.\n", supp.getName(), items);
            }
        }
    }

    private Supplier getBestSupplierByProduct(ProductCatalog product,int amount){
        double minimalPrice= Double.MAX_VALUE,currentPrice=0;
        Supplier best=null;
        List<Supplier> suppliersByProduct=consoleMenu.getDatabase().findSuppliersByProductID(product.get_id());
        for(Supplier supplier:suppliersByProduct){
            if((currentPrice=getDiscountedPrice(supplier, product,amount))<minimalPrice){
                minimalPrice=currentPrice;
                best=supplier;
            }
        }
        return best;
    }

    private void confirmOrder(){
        System.out.println("Please select the order you would like to confirm:");
        Order order = consoleMenu.getOrder();
        if(order == null) {
            System.out.println("No orders found.");
            return;
        }
        if(order.arrived()){
            System.out.printf("Order %s is already confirmed.\n", order.getId());
            return;
        }
        if(order.getDeliveryMethod().equals(DeliveryMethod.Weekly)) {
            System.out.printf("Order %s is a weekly order, would you like to schedule a new delivery for the next week? (press y)", order.getId());
            if(Utils.readLine().toLowerCase().equals("y")) {
                consoleMenu.getDatabase().createOrder(order);
                System.out.println("A new weekly order has been registered for the next week.");
            }
        }
        consoleMenu.getDatabase().confirmOrder(order);
        System.out.printf("Order %s confirmed successfully!\n", order.getId());
    }
    
    private void updateWeeklyOrder(){
        Order oldOrder;
        while((oldOrder = consoleMenu.getWeeklyOrder()) == null)
            System.out.println("There were no orders matching this search.");

        Employee employee = consoleMenu.getConnected();
        Supplier supplier = oldOrder.getSupplier();
        Map<ProductCatalog, Integer> items = selectItems(supplier);
        double totalPrice = calculatePrice(supplier, items);
        Order newOrder = new Order(oldOrder.getId(), employee, supplier, null, false, totalPrice, items);
        consoleMenu.getDatabase().updateWeeklyOrder(newOrder);
        System.out.printf("Order %s has been updated successfully!\n", newOrder.getId());
    }

    private void cancelWeeklyOrder(){
        Order order;
        while((order = consoleMenu.getWeeklyOrder()) == null)
            System.out.println("There were no orders matching this search.");
        consoleMenu.getDatabase().cancelWeeklyOrder(order);
        System.out.printf("Order %s has been canceled successfully!\n", order.getId());
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

    private Map<ProductCatalog, Integer> selectItems(Supplier supplier){
        Map<ProductCatalog, Integer> items = new HashMap<>();
        ProductCatalog product;
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
            if((product = consoleMenu.getDatabase().getProductByID(Utils.parseInt(input))) == null){
                System.out.printf("Product id %s does not exists in the system.\n", input);
                continue;
            }
            if(!supplier.sells(product)){
                System.out.printf("%s does not sell %s (id %s).\n",supplier.getName(),product.get_name(), product.get_id());
                continue;
            }
            System.out.println("Please enter amount:");
            while((amount = Utils.checkIntBounds(0)) == -1)
                System.out.println("Invalid input");
            items.put(product, amount);
        }
        return items;
    }

    private double calculatePrice(Supplier supplier, Map<ProductCatalog, Integer> items){
        double totalPrice = 0;
        for(ProductCatalog p : items.keySet())
            totalPrice += getDiscountedPrice(supplier, p, items.get(p));
        return totalPrice;
    }

    private double getDiscountedPrice(Supplier supplier, ProductCatalog productCatalog, int amount){
        ProductAgreement agreement = supplier.getAgreement(productCatalog);
        double discount = amount < agreement.getMinAmount() ? 0 :
                Math.min(agreement.getMaxDiscount(),amount*agreement.getBaseDiscount()/agreement.getMinAmount())/100;
        return (1-discount) * agreement.getPrice() * amount;
    }
}
