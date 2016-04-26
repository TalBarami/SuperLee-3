package Suppliers.Entities;

import java.util.HashMap;
import java.util.Map;

public enum DeliveryMethod {
    Weekly(0),
    onDemand(1),
    Self(2);

    private int number;
    private static Map<Integer, DeliveryMethod> map = new HashMap<>();

    static{
        for(DeliveryMethod deliveryMethod : DeliveryMethod.values()){
            map.put(deliveryMethod.number, deliveryMethod);
        }
    }

    DeliveryMethod(final int number){this.number = number;}

    public static DeliveryMethod valueOf(int number){
        return map.get(number);
    }
}
