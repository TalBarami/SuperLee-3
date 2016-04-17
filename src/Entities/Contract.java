package Entities;

import java.util.Map;


public class Contract {
    private DeliveryMethod deliveryMethod;
    private int deliveryTime;
    private int minDiscountLimits;
    private int maxDiscountLimits;
    private double discount;
    private Map<Product,Double> products;

    @Override
    public String toString() {
        return "Contract{" +
                "deliveryMethod=" + deliveryMethod +
                ", deliveryTime=" + deliveryTime +
                ", discountLimits=" + minDiscountLimits + " , " + maxDiscountLimits +
                ", discount=" + discount +
                '}';
    }

    public Contract(DeliveryMethod deliveryMethod, int deliveryTime, int minDiscountLimits, int maxDiscountLimits, double discount, Map<Product,Double> products) {
        this.deliveryMethod = deliveryMethod;
        this.deliveryTime = deliveryTime;
        this.minDiscountLimits = minDiscountLimits;
        this.maxDiscountLimits = maxDiscountLimits;
        this.discount = discount;
        this.products = products;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Map<Product, Double> getProducts(){
        return products;
    }

    public void setProducts(Map<Product, Double> products) {
        this.products = products;
    }

    public int getMinDiscountLimits() {
        return minDiscountLimits;
    }

    public int getMaxDiscountLimits() {
        return maxDiscountLimits;
    }
}
