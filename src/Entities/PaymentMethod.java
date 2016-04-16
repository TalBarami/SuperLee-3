package Entities;

import java.util.HashMap;
import java.util.Map;

public enum PaymentMethod {
    CreditCard(0),
    Cash(1),
    Check(2);
    private int number;
    private static Map<Integer, PaymentMethod> map = new HashMap<>();

    static{
            for(PaymentMethod paymentMethod : PaymentMethod.values()){
                map.put(paymentMethod.number, paymentMethod);
            }
    }

    PaymentMethod(final int number){this.number = number;}

    public static PaymentMethod valueOf(int number){
        return map.get(number);
    }
}
