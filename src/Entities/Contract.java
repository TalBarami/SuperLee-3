package Entities;

import java.util.Map;


public class Contract {
    private DeliveryMethod deliveryMethod;
    private int deliveryTime;
    private int minDiscountLimit;
    private double baseDiscount;
    private double maxDiscount;
    private Map<Product,Double> products;

    @Override
    public String toString() {
        String result = "\n\tDelivery method: " + deliveryMethod +
                        "\t\tDelivery time: " + deliveryTime +
                        "\n\tMinimum amount for discount: " + minDiscountLimit +
                        "\t\tBase discount: " + baseDiscount + "%" +
                        "\t\tMaximum discount: " + maxDiscount + "%" +
                        "\n\tProducts:";
        for(Product p : products.keySet())
            result+="\n\t\tID: " + p.getId() + "\tName: " + p.getName() + "\t\tPrice: " + products.get(p);
        return result;
    }

    public Contract(DeliveryMethod deliveryMethod, int deliveryTime, int minDiscountLimit, double baseDiscount, double maxDiscount, Map<Product,Double> products) {
        this.deliveryMethod = deliveryMethod;
        this.deliveryTime = deliveryTime;
        this.minDiscountLimit = minDiscountLimit;
        this.baseDiscount = baseDiscount;
        this.maxDiscount = maxDiscount;
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

    public Map<Product, Double> getProducts(){
        return products;
    }

    public void setProducts(Map<Product, Double> products) {
        this.products = products;
    }

    public int getMinDiscountLimit() {
        return minDiscountLimit;
    }

    public void setMinDiscountLimit(int minDiscountLimit) {
        this.minDiscountLimit = minDiscountLimit;
    }

    public double getBaseDiscount() {
        return baseDiscount;
    }

    public void setBaseDiscount(double baseDiscount) {
        this.baseDiscount = baseDiscount;
    }

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }
}
