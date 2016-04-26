package Entities;

import java.util.Map;


public class Contract {
    private DeliveryMethod deliveryMethod;
    private int deliveryTime;
    private Map<Product,ProductAgreement> products;

    @Override
    public String toString() {
        String result = "\n\tDelivery method: " + deliveryMethod +
                        "\t\tDelivery time: " + deliveryTime +
                        "\n\tProducts:";
        for(Product p : products.keySet())
            result+="\n\t\tID: " + p.getId() + "\tName: " + p.getName() + "\n\t\tInformation: " + products.get(p);
        return result;
    }

    public Contract(DeliveryMethod deliveryMethod, int deliveryTime, Map<Product,ProductAgreement> products) {
        this.deliveryMethod = deliveryMethod;
        this.deliveryTime = deliveryTime;
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

    public Map<Product, ProductAgreement> getProducts(){
        return products;
    }
}
