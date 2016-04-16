package Entities;

import java.util.Date;
import java.util.Map;


public class Order {
    private String id;
    private Employee employee;
    private Supplier supplier;
    private Date date;
    private boolean arrived;
    private double totalPrice;
    private Map<Product, Integer> items;

    @Override
    public String toString() {
        return "===== Order Details =====\n" +
                " Order ID  : '" + id + '\'' +
                "\n Employee ID: " + employee.getId() +
                "\n Supplier : " + supplier.getName() +
                "\n Date : " + date.toString() +
                "\n Arrived : " + arrived +
                "\n Total Price : " + totalPrice +
                "\n Products in order : \n" + productsInOrderToString();
    }
    private String productsInOrderToString(){
        String toString="";
        for(Product product : items.keySet()){
            toString+="\t\tProduct : "+product.getName() + "\tAmount : " + items.get(product) +".\n";
        }
        return toString;
    }
    public Order(String id, Employee employee, Supplier supplier, Date date, boolean arrived, double totalPrice,Map<Product, Integer> order) {
        this.id = id;
        this.employee = employee;
        this.supplier = supplier;
        this.date = date;
        this.arrived = arrived;
        this.totalPrice = totalPrice;
        this.items = order;
    }

    public Order(Employee employee, Supplier supplier, double totalPrice,Map<Product, Integer> order) {
        this("", employee, supplier, null, false, totalPrice, order);
    }

    public String getId() {
        return id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Employee getEmployee() {
        return employee;
    }

    public boolean isArrived() {
        return arrived;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Map<Product, Integer> getItems() {
        return items;
    }
}
