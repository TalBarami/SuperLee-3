package Entities;

import Application.Utils;

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

    public String toFullString() {
        return "\tID: " + id +
                "\n\tName: " + name +
                "\t\tBank account: " + bankAccount +
                "\t\tPayment method: " + paymentMethod +
                "\n\tContacts: " + contactsToString()+
                "\n\t" + ((contract != null) ? "Contract:" + contract.toString() : name + " has no contract.");
    }

    @Override
    public String toString(){
        return  "\tID: " + id +
                "\t\tName: " + name;
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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Map<String, String> getContacts() {
        return contacts;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Map<Product,ProductAgreement> getProducts(){
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
        return Utils.contains(getProducts(), p);
    }

    public ProductAgreement getAgreement(Product p){
        return Utils.getItem(getProducts(), p);
    }

    private String contactsToString(){
        String result = "";
        for(String contact : contacts.keySet())
            result += "\n\t\t" + contact + " - " + contacts.get(contact);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supplier supplier = (Supplier) o;

        return id != null ? id.equals(supplier.id) : supplier.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
