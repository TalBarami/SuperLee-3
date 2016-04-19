package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Supplier {
    private String id;
    private String name;
    private String bankAccount;
    private PaymentMethod paymentMethod;
    private Map<String, String> contacts;
    private Contract contract;
    private boolean active;

    public Supplier(String id, String name, String bankAccount, PaymentMethod paymentMethod, Map<String, String> contacts, boolean active) {
        this.id = id;
        this.name = name;
        this.bankAccount = bankAccount;
        this.paymentMethod = paymentMethod;
        this.contacts = contacts;
        this.contract = null;
        this.active = active;
    }

    public Supplier(String id, String name, String bankAccount, PaymentMethod paymentMethod, Map<String, String> contacts) {
        this(id, name, bankAccount, paymentMethod, contacts, true);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", paymentMethod=" + paymentMethod +
                '}';
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

    public Map<String, String> getContacts() {
        return contacts;
    }

    public void setContacts(Map<String, String> contacts) {
        this.contacts = contacts;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Map<Product,Double> getProducts(){
        return contract == null? null : contract.getProducts();
    }

    public List<String> getManufacturers(){
        if(contract == null)
            return null;
        List<String> manufacturers = new ArrayList<>();
        String m;
        for(Product p : getProducts().keySet()){
            m = p.getManufacturer();
            if(!manufacturers.contains(m))
                manufacturers.add(m);
        }
        return manufacturers;
    }

    public boolean isActive() {
        return active;
    }

    public boolean sells(Product p){
        if(contract == null)
            throw new NullPointerException();
        for(Product product : getProducts().keySet())
            if(product.getId().equals(p.getId()))
                return true;
        return false;
    }

    public Double getPrice(Product p){
        if(contract == null)
            throw new NullPointerException();
        for(Product product : getProducts().keySet())
            if(product.getId().equals(p.getId()))
                return getProducts().get(product);
        throw new NullPointerException();
    }
}
