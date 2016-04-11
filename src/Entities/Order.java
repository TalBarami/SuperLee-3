package Entities;

import java.util.Date;
import java.util.HashMap;

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
    private HashMap<Product, Integer> order;

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

    public HashMap<Product, Integer> getOrder() {
        return order;
    }

    public void setOrder(HashMap<Product, Integer> order) {
        this.order = order;
    }
}
