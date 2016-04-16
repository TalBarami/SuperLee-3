package Entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tal on 10/04/2016.
 */
public class Order {
    private String id;
    private Employee employee;
    private Supplier supplier;
    private Date date;
    private boolean arrived;
    private double totalPrice;
    private Map<Product, Integer> order;

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
        for(Product product : order.keySet()){
            toString+="\t\tProduct : "+product.getName() + "\tAmount : " + order.get(product) +".\n";
        }
        return toString;
    }
    public Order(String id, Employee employee, Supplier supplier, Date date, boolean arrived, double totalPrice, HashMap<Product, Integer> order) {
        this.id = id;
        this.employee = employee;
        this.supplier = supplier;
        this.date = date;
        this.arrived = arrived;
        this.totalPrice = totalPrice;
        this.order = order;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<Product, Integer> getOrder() {
        return order;
    }

    public void setOrder(HashMap<Product, Integer> order) {
        this.order = order;
    }
}
