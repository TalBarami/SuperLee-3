package Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Supplier {
    private String id;
    private String name;
    private String bankAccount;
    private PaymentMethod paymentMethod;
    private HashMap<String, String> contacts;
    private Contract contract;

    public Supplier(String id, String name, String bankAccount, PaymentMethod paymentMethod, HashMap<String, String> contacts) {
        this.id = id;
        this.name = name;
        this.bankAccount = bankAccount;
        this.paymentMethod = paymentMethod;
        this.contacts = contacts;
        this.contract = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public HashMap<String, String> getContacts() {
        return contacts;
    }

    public void setContacts(HashMap<String, String> contacts) {
        this.contacts = contacts;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public HashMap<Product,Integer> getProducts(){
        return contract.getProducts();
    }

    public List<String> getManufacturers(){
        List<String> manufacturers = new ArrayList<>();
        String m;
        for(Product p : getProducts().keySet()){
            m = p.getManufacturer();
            if(!manufacturers.contains(m))
                manufacturers.add(m);
        }
        return manufacturers;
    }
}
