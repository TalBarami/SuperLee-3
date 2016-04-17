package Application;

import Entities.*;
import javafx.util.Pair;

import javax.rmi.CORBA.Util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuppliersMenu {
    private ConsoleMenuImplementation consoleMenu;
    private Database database;
    private int selected;

    public SuppliersMenu(ConsoleMenuImplementation consoleMenu, Database database){
        this.consoleMenu = consoleMenu;
        this.database = database;
    }

    private static final String suppliersCommands[] = {
            "Add new supplier.",
            "Edit supplier.",
            "Remove supplier",
            "Add supplier's contract.",
            "Edit supplier's contract",
            "View supplier's information",
            "View supplier's products.",
            "View supplier's manufacturers.",
            "Back."
    };

    public void displaySuppliersMenu(){
        while(true) {
            selected = Utils.MenuSelect(suppliersCommands);
            switch (selected) {
                case 1:
                    addNewSupplier();
                    break;
                case 2:
                    editSupplier();
                    break;
                case 3:
                    removeSupplier();
                    break;
                case 4:
                    addContract();
                    break;
                case 5:
                    editContract();
                case 6:
                    viewSupplier();
                    break;
                case 7:
                    viewSuppliersProducts();
                    break;
                case 8:
                    viewSuppliersManufacturers();
                    break;
                case 9:
                    return;
            }
        }
    }

    private void addNewSupplier(){
        database.AddSupplier(createSupplier());
        System.out.println("Supplier added successfully!");
    }

    private void editSupplier(){
        System.out.println("Select supplier you would like to edit.");
        Supplier oldSupplier = consoleMenu.getSupplier();
        if(oldSupplier == null)
            return;
        System.out.printf("Supplier found: %s\nPlease enter your new information.\n", oldSupplier);
        Supplier newSupplier = createSupplier(oldSupplier);
        database.EditSupplier(newSupplier);
        System.out.println("Supplier edited successfully!");
    }

    private void removeSupplier(){
        Supplier supplier = consoleMenu.getSupplier();
        if(supplier == null)
            return;
        database.RemoveSupplier(supplier);
        System.out.println("Supplier removed successfully!");
    }

    private void addContract(){
        Supplier supplier;
        System.out.println("Please select which supplier you would like to add contract to:");
        supplier = consoleMenu.getSupplier();
        if(supplier == null)
            return;
        if(supplier.getContract() != null) {
            System.out.println("This supplier already has a contract.");
            return;
        }

        Contract contract = createContract();
        supplier.setContract(contract);
        System.out.println("Contract added successfully!");
    }

    private void editContract(){
        System.out.println("Select supplier with the contract you would like to edit.");
        Supplier supplier = consoleMenu.getSupplier();
        if(supplier == null)
            return;
        if(supplier.getContract() == null){
            System.out.println("This supplier does not have a contract.");
            return;
        }
        Contract oldContract = supplier.getContract();
        System.out.printf("Contract found: %s\nPlease enter your new information.\n", oldContract);
        Contract newContract = createContract(oldContract);
        supplier.setContract(newContract);
        database.editContract(supplier);
        System.out.println("Contract edited successfully!");
    }

    private void viewSupplier(){
        List<Supplier> suppliers = consoleMenu.searchSupplier();
        if(suppliers == null)
            return;
        System.out.println(suppliers.size() == 1 ? "Supplier found:\n" : "Suppliers found:\n");
        for(Supplier s : suppliers){
            System.out.printf("%s\n\n",s.toString());
        }
    }

    private void viewSuppliersProducts() {
        Supplier supplier = consoleMenu.getSupplier();
        if(supplier == null)
            return;
        if(supplier.getContract() == null) {
            System.out.println("This supplier does not have a contract.");
            return;
        }
        System.out.printf("Result for supplier: %s\n", supplier.getName());
        Map<Product,Double> products = supplier.getProducts();
        System.out.println(products.size() == 1 ? "Product found:\n" : "Product found:\n");
        for (Product p : products.keySet()) {
            System.out.printf("%s\nSold by %s for %f.\n\n", p, supplier.getName(), products.get(p));
        }
    }

    private void viewSuppliersManufacturers() {
        Supplier supplier = consoleMenu.getSupplier();
        if(supplier == null)
            return;
        if(supplier.getContract() == null) {
            System.out.println("This supplier does not have a contract.");
            return;
        }
        System.out.printf("Result for supplier: %s\n", supplier.getName());
        List<String> manufacturers = supplier.getManufacturers();
        System.out.println(manufacturers.size() == 1 ? "Manufacturer found:\n" : "Manufacturers found:\n");
        for (String manufacturer : manufacturers) {
            System.out.printf("%s\n", manufacturer);
        }
    }

    private Supplier createSupplier(){
        return createSupplier(null);
    }

    private Supplier createSupplier(Supplier supplier){
        String id;
        String name;
        String bankAccount;
        PaymentMethod paymentMethod;
        Map<String,String> contacts;

        if(supplier == null) {
            System.out.println("Please enter supplier id:");
            while ((id = Utils.readLine()).isEmpty() || Utils.parseInt(id) == -1 || !database.FindSupplierByID(id).isEmpty())
                System.out.println("Invalid id. please try again.");
        }else id = supplier.getId();

        System.out.println("Please enter supplier's name:");
        while((name = Utils.readLine()).isEmpty()) {
            if(supplier != null){
                name = supplier.getName();
                break;
            }
            System.out.println("Invalid name. please try again.");
        }
        System.out.println("Please enter supplier's bank account:");
        while((bankAccount = Utils.readLine()).isEmpty()) {
            if(supplier != null){
                bankAccount = supplier.getBankAccount();
                break;
            }
            System.out.println("Invalid bank account. please try again.");
        }
        paymentMethod = selectPaymentMethods(supplier);
        contacts = selectContacts(supplier);
        return new Supplier(id, name, bankAccount, paymentMethod, contacts);
    }

    private Contract createContract(){
        return createContract(null);
    }

    private Contract createContract(Contract oldContract){
        DeliveryMethod deliveryMethod;
        int deliveryTime;
        int minDiscountLimit;
        int maxDiscountLimit;
        double discount;
        Map<Product,Double> products;

        deliveryMethod = selectDeliveryMethod(oldContract);
        System.out.println("Please enter the delivery time (in days):");
        deliveryTime = Utils.checkIntBounds(0);
        if(deliveryTime == -1)
            deliveryTime = oldContract.getDeliveryTime();
        System.out.println("Please enter the minimum amount for discount:");
        minDiscountLimit = Utils.checkIntBounds(0);
        if(minDiscountLimit == -1)
            minDiscountLimit = oldContract.getMinDiscountLimit();
        System.out.println("Please enter the maximum amount for discount:");
        maxDiscountLimit = Utils.checkIntBounds(minDiscountLimit);
        if(maxDiscountLimit == -1)
            maxDiscountLimit = oldContract.getMaxDiscountLimit();
        System.out.println("Please enter the discount (in percentage):");
        discount = Utils.checkDoubleBounds(0, 100);
        if(discount == -1)
            discount = oldContract.getDiscount();
        products = selectProducts(oldContract);

        Contract contract = new Contract(deliveryMethod, deliveryTime, minDiscountLimit, maxDiscountLimit, discount, products);
        return contract;
    }

    private PaymentMethod selectPaymentMethods(Supplier supplier){
        PaymentMethod values[] = PaymentMethod.values();
        System.out.println("Please choose one of the following:");
        for (int i = 0; i < values.length; i++)
            System.out.printf("%d. %s\n", i, values[i]);
        String input = Utils.readLine();
        if(input.isEmpty() && supplier != null)
            return supplier.getPaymentMethod();

        selected = Utils.parseInt(input);
        while(selected<0 || selected >= values.length){
            System.out.println("Invalid input.");
            for (int i = 0; i < values.length; i++)
                System.out.printf("%d. %s\n", i, values[i]);
            selected = Utils.parseInt(Utils.readLine());
        }

        return PaymentMethod.valueOf(selected);
    }

    private Map<String, String> selectContacts(Supplier supplier){
        Map<String, String> contacts = new HashMap<>();
        String contactName;
        String contactPhone;
        System.out.println("Add supplier's contacts. Leave the name field blank when you are done.");
        while(true){
            System.out.println("Please enter name:");
            if((contactName = Utils.readLine()).isEmpty()) {
                if(contacts.isEmpty() && supplier == null)
                    System.out.println("You must enter at least 1 contact.");
                else
                    break;
            }
            System.out.println("Please enter phone:");
            while((contactPhone = Utils.readLine()).isEmpty() || Utils.parseInt(contactPhone) == -1 || contacts.containsValue(contactPhone))
                System.out.println("Invalid phone number. please try again.");
            contacts.put(contactName, contactPhone);
        }

        return contacts.isEmpty() ? supplier.getContacts() : contacts;
    }

    private DeliveryMethod selectDeliveryMethod(Contract contract){
        DeliveryMethod values[] = DeliveryMethod.values();
        System.out.println("Please choose one of the following:");
        for (int i = 0; i < values.length; i++)
            System.out.printf("%d. %s\n", i, values[i]);
        String input = Utils.readLine();
        if(input.isEmpty() && contract != null)
            return contract.getDeliveryMethod();
        selected = Utils.parseInt(Utils.readLine());
        while(selected<0 || selected >= values.length){
            System.out.println("Invalid input.");
            for (int i = 0; i < values.length; i++)
                System.out.printf("%d. %s\n", i, values[i]);
            selected = Utils.parseInt(Utils.readLine());
        }
        return DeliveryMethod.valueOf(selected);
    }

    private Map<Product, Double> selectProducts(Contract contract){
        Map<Product, Double> products = new HashMap<>();
        Product product;
        double price;
        System.out.println("Add products. Leave the product id field blank when you are done.");
        while(true){
            System.out.println("Please enter product id:");
            if((product = database.getProductByID(Utils.readLine())) == null) {
                if(products.isEmpty() && contract == null)
                    System.out.println("You must enter at least 1 product.");
                else
                    break;
            }
            System.out.println("Please enter price:");
            price = Utils.checkDoubleBounds(0);
            products.put(product, price);
        }

        return products.isEmpty() ? contract.getProducts() : products;
    }
}