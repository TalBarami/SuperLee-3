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
                "Order ID: " + id +
                "\n\tEmployee ID:" + employee.getId() +
                "\t\tSupplier: " + supplier.getName() +
                "\n\tDate: " + date.toString() +
                "\t\tStatus: " + (arrived ? "Arrived." : "Waiting for delivery.") +
                "\n\tProducts in order:" + productsInOrderToString() +
                "\n\tTotal Price : " + totalPrice;
    }
    private String productsInOrderToString(){
        String toString="";
        for(Product product : items.keySet())
            toString+="\n\t\tProduct: "+product.getName() + "\tAmount: " + items.get(product);
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
