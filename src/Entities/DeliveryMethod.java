package Entities;

import java.util.HashMap;
import java.util.Map;

public enum DeliveryMethod {
    Weekly(1),
    onDemand(2),
    Self(3);

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
