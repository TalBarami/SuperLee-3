package Suppliers.Entities;

import Employees_Transports.Backend.Employee;
import Inventory.entities.ProductCatalog;

import java.util.Date;
import java.util.Map;


public class Order {
    private String id;
    private Employee employee;
    private Supplier supplier;
    private Date date;
    private boolean arrived;
    private double totalPrice;
    private Map<ProductCatalog, Integer> items;
    private String sourceAddress;

    @Override
    public String toString() {
        return "Order ID: " + id +
                "\n\tEmployee ID:" + employee.getID() +
                "\t\tSupplier: " + supplier.getName() +
                "\n\tDate: " + date.toString() +
                "\t\tStatus: " + (arrived ? "Arrived." : "Waiting for delivery.") +
                "\n\tProducts in order:" + productsInOrderToString() +
                "\n\tTotal Price : " + totalPrice;
    }
    private String productsInOrderToString(){
        String toString="";
        for(ProductCatalog product : items.keySet())
            toString+="\n\t\tProduct: "+product.get_name() + "\tAmount: " + items.get(product);
        return toString;
    }

    public Order(String id, Employee employee, Supplier supplier, Date date, boolean arrived, double totalPrice,Map<ProductCatalog, Integer> items, String sourceAddress) {
        this.id = id;
        this.employee = employee;
        this.supplier = supplier;
        this.date = date;
        this.arrived = arrived;
        this.totalPrice = totalPrice;
        this.items = items;
        this.sourceAddress = sourceAddress;
    }

    public Order(Employee employee, Supplier supplier, double totalPrice,Map<ProductCatalog, Integer> items, String sourceAddress) {
        this("", employee, supplier, null, false, totalPrice, items, sourceAddress);
    }

    public void setId(String id){
        this.id = id;
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

    public boolean arrived() {
        return arrived;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Map<ProductCatalog, Integer> getItems() {
        return items;
    }

    public DeliveryMethod getDeliveryMethod(){
        return supplier.getContract().getDeliveryMethod();
    }

    public String getSourceAddress(){
        return sourceAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return id != null ? id.equals(order.id) : order.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public double calculateWeight(){
        double sum = 0;
        for(ProductCatalog p : items.keySet())
            sum+=(p.get_weight()*items.get(p));
        return sum;
    }
}
