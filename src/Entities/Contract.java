package Entities;

import javafx.util.Pair;

import java.net.Inet4Address;
import java.util.HashMap;


public class Contract {
    private DeliveryMethod deliveryMethod;
    private int deliveryTime;
    private Pair<Integer, Integer> discountLimits;
    private double discount;
    private HashMap<Product,Integer> products;


    @Override
    public String toString() {
        return "Contract{" +
                "deliveryMethod=" + deliveryMethod +
                ", deliveryTime=" + deliveryTime +
                ", discountLimits=" + discountLimits +
                ", discount=" + discount +
                '}';
    }

    public Contract(DeliveryMethod deliveryMethod, int deliveryTime, Pair<Integer, Integer> discountLimits, double discount, HashMap<Product,Integer> products) {
        this.deliveryMethod = deliveryMethod;
        this.deliveryTime = deliveryTime;
        this.discountLimits = discountLimits;
        this.discount = discount;
        this.products=products;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Pair<Integer, Integer> getDiscountLimits() {
        return discountLimits;
    }

    public void setDiscountLimits(Pair<Integer, Integer> discountLimits) {
        this.discountLimits = discountLimits;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Product, Integer> products) {
        this.products = products;
    }
}
