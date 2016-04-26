package Suppliers.Entities;

import Inventory.entities.ProductCatalog;

import java.util.Map;


public class Contract {
    private DeliveryMethod deliveryMethod;
    private int deliveryTime;
    private Map<ProductCatalog,ProductAgreement> products;

    @Override
    public String toString() {
        String result = "\n\tDelivery method: " + deliveryMethod +
                        "\t\tDelivery time: " + deliveryTime +
                        "\n\tProducts:";
        for(ProductCatalog p : products.keySet())
            result+="\n\t\tID: " + p.get_id() + "\tName: " + p.get_name() + "\n\t\tInformation: " + products.get(p);
        return result;
    }

    public Contract(DeliveryMethod deliveryMethod, int deliveryTime, Map<ProductCatalog,ProductAgreement> products) {
        this.deliveryMethod = deliveryMethod;
        this.deliveryTime = deliveryTime;
        this.products = products;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public Map<ProductCatalog, ProductAgreement> getProducts(){
        return products;
    }
}
