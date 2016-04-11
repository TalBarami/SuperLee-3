package Entities;

import javafx.util.Pair;

/**
 * Created by Tal on 10/04/2016.
 */
public class Contract {
    private DeliveryMethod deliveryMethod;
    private int deliveryTime;
    private Pair<Integer, Integer> discountLimits;
    private double discount;

    @Override
    public String toString() {
        return "Contract{" +
                "deliveryMethod=" + deliveryMethod +
                ", deliveryTime=" + deliveryTime +
                ", discountLimits=" + discountLimits +
                ", discount=" + discount +
                '}';
    }

    public Contract(DeliveryMethod deliveryMethod, int deliveryTime, Pair<Integer, Integer> discountLimits, double discount) {
        this.deliveryMethod = deliveryMethod;
        this.deliveryTime = deliveryTime;
        this.discountLimits = discountLimits;
        this.discount = discount;
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
}
