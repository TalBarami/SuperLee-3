package Suppliers.Application.UserInterface;

import Employees_Transports.Backend.station;
import Employees_Transports.DL.StationHandler;
import Inventory.dbHandlers.ProductHandler;
import Inventory.entities.ProductCatalog;
import Suppliers.Application.Utils;
import Suppliers.Entities.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuppliersMenu {
    private ConsoleMenu consoleMenu;
    private int selected;

    public SuppliersMenu(ConsoleMenu consoleMenu){
        this.consoleMenu = consoleMenu;
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
                    break;
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
        String id;
        List<Supplier> suppliers;
        Supplier supplier;
        System.out.println("Please enter supplier id:");
        while ((id = Utils.readLine()).isEmpty() || Utils.parseInt(id) == -1)
            System.out.println("Invalid id. please try again.");
        if((suppliers = consoleMenu.getDatabase().findSupplierByID(id)).isEmpty()){
            supplier = createSupplier(id);
            consoleMenu.getDatabase().addSupplier(supplier);
            System.out.printf("%s added successfully!\n", supplier.getName());
        }else{
            supplier = suppliers.get(0);
            if(supplier.isActive())
                System.out.printf("%s (id: %s) already exists in the system.\n",supplier.getName(), supplier.getId());
            else {
                consoleMenu.getDatabase().reactivateSupplier(supplier);
                System.out.printf("%s reactivated successfully!\n", supplier.getName());
            }
        }
    }

    private void editSupplier(){
        System.out.println("Select supplier you would like to edit.");
        Supplier oldSupplier = consoleMenu.getSupplier();
        if(oldSupplier == null)
            return;
        System.out.printf("Supplier found: %s\nPlease enter your new information.\nYou can leave the field blank if you do not want to change it.", oldSupplier);
        Supplier newSupplier = createSupplier(oldSupplier.getId(),oldSupplier);
        consoleMenu.getDatabase().editSupplier(newSupplier);
        System.out.printf("%s edited successfully!\n", newSupplier.getName());
    }

    private void removeSupplier(){
        Supplier supplier = consoleMenu.getSupplier();
        if(supplier == null)
            return;
        consoleMenu.getDatabase().removeSupplier(supplier);
        System.out.printf("%s removed successfully!\n", supplier.getName());
    }

    private void addContract(){
        Supplier supplier;
        System.out.println("Please select which supplier you would like to add contract to:");
        supplier = consoleMenu.getSupplier();
        if(supplier == null)
            return;
        if(supplier.getContract() != null) {
            System.out.printf("%s already has a contract.\n", supplier.getName());
            return;
        }

        Contract contract = createContract();
        supplier.setContract(contract);
        consoleMenu.getDatabase().addContract(supplier);
        System.out.println("Contract added successfully!");
    }

    private void editContract(){
        System.out.println("Select supplier with the contract you would like to edit.");
        Supplier supplier = consoleMenu.getSupplier();
        if(supplier == null)
            return;
        if(supplier.getContract() == null){
            System.out.printf("%s does not have a contract.\n", supplier.getName());
            return;
        }
        Contract oldContract = supplier.getContract();
        System.out.printf("Contract found:\n%s\nPlease enter your new information.\n", oldContract);
        Contract newContract = createContract(oldContract);
        supplier.setContract(newContract);
        consoleMenu.getDatabase().editContract(supplier);
        System.out.println("Contract edited successfully!");
    }

    private void viewSupplier(){
        List<Supplier> suppliers = consoleMenu.searchSupplier();
        if(suppliers == null)
            return;
        System.out.println(suppliers.size() == 1 ? "Supplier found:" : "Suppliers found:");
        for(Supplier s : suppliers){
            System.out.printf("%s\n",s.toFullString());
        }
    }

    private void viewSuppliersProducts() {
        Supplier supplier = consoleMenu.getSupplier();
        if(supplier == null)
            return;
        if(supplier.getContract() == null) {
            System.out.printf("%s does not have a contract.\n", supplier.getName());
            return;
        }
        System.out.printf("Result for supplier: %s\n", supplier.getName());
        Map<ProductCatalog,ProductAgreement> products = supplier.getProducts();
        System.out.println(products.size() == 1 ? "Product found:" : "Products found:");
        for (ProductCatalog p : products.keySet()) {
            System.out.printf("%s\t\tInformation: %s.\n", p, products.get(p));
        }
    }

    private void viewSuppliersManufacturers() {
        Supplier supplier = consoleMenu.getSupplier();
        if(supplier == null)
            return;
        if(supplier.getContract() == null) {
            System.out.printf("%s does not have a contract.\n", supplier.getName());
            return;
        }
        System.out.printf("Result for supplier: %s\n", supplier.getName());
        List<String> manufacturers = supplier.getManufacturers();
        System.out.println(manufacturers.size() == 1 ? "Manufacturer found:" : "Manufacturers found:");
        for (String manufacturer : manufacturers) {
            System.out.printf("\t%s\n", manufacturer);
        }
    }

    private Supplier createSupplier(String id){
        return createSupplier(id, null);
    }

    private Supplier createSupplier(String id, Supplier supplier){
        String name;
        String bankAccount;
        PaymentMethod paymentMethod;
        station address;
        Map<String,String> contacts;

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
        System.out.println("Please enter supplier's address:");
        while((address = StationHandler.getInstance().getstation(Utils.readLine())) == null) {
            if(supplier != null){
                address = StationHandler.getInstance().getstation(supplier.getAddress());
                break;
            }
            System.out.println("Invalid address. please try again.");
        }
        contacts = selectContacts(supplier);
        return new Supplier(id, name, bankAccount, paymentMethod, address.getAddress(), contacts);
    }

    private Contract createContract(){
        return createContract(null);
    }

    private Contract createContract(Contract oldContract){
        DeliveryMethod deliveryMethod;
        int deliveryTime;
        Map<ProductCatalog,ProductAgreement> products;

        deliveryMethod = selectDeliveryMethod(oldContract);
        System.out.println(deliveryMethod.equals(deliveryMethod.Weekly) ? "Please enter the weekly delivery day:" : "Please enter the delivery time (in days):");
        while((deliveryTime = deliveryMethod == DeliveryMethod.Weekly ? Utils.checkIntBounds(1,7) : Utils.checkIntBounds(0)) == -1) {
            if(oldContract != null) {
                deliveryTime = oldContract.getDeliveryTime();
                break;
            }
        }
        products = selectProducts(oldContract);
        return new Contract(deliveryMethod, deliveryTime, products);
    }

    private PaymentMethod selectPaymentMethods(Supplier supplier){
        PaymentMethod values[] = PaymentMethod.values();
        System.out.println("Please choose one of the following:");
        for (int i = 0; i < values.length; i++)
            System.out.printf("%d. %s\n", i, values[i]);
        String input = Utils.readLine();
        if(input.isEmpty() && supplier != null)
            return supplier.getPaymentMethod();
        selected = Utils.displayEnum(input, values);
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
                if(supplier == null && contacts.isEmpty()) {
                    System.out.println("You must enter at least 1 contact.");
                    continue;
                }else
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
        selected = Utils.displayEnum(input, values);
        return DeliveryMethod.valueOf(selected);
    }

    private Map<ProductCatalog, ProductAgreement> selectProducts(Contract contract){
        Map<ProductCatalog, ProductAgreement> products = new HashMap<>();
        ProductCatalog product;
        ProductAgreement agreement;
        double price;
        int minAmount;
        double baseDiscount;
        double maxDiscount;
        String input;
        System.out.println("Add products. Leave the product id field blank when you are done.");
        while(true){
            System.out.println("Please enter product id:");
            input = Utils.readLine();
            if(input.isEmpty()) {
                if(contract == null && products.isEmpty()) {
                    System.out.println("You must enter at least one product.");
                    continue;
                }else
                    break;
            }
            if((product = consoleMenu.getDatabase().getProductByID(Utils.parseInt(input))) == null) {
                System.out.println("There is no such product.");
                continue;
            }
            if(Utils.contains(products, product)){
                System.out.println("You already added this product.");
                continue;
            }
            System.out.println("Please enter price:");
            while((price = Utils.checkDoubleBounds(0)) == -1)
                System.out.println("Invalid input.");
            System.out.println("If the contract contains a discount agreement for this product, press 'y', else press any key to continue:");
            if(Utils.readLine().toLowerCase().equals("y")){
                System.out.println("Please enter the minimum amount for discount:");
                while((minAmount = Utils.checkIntBounds(1)) == -1)
                    System.out.println("Invalid input.");
                System.out.println("Please enter the base discount:");
                while((baseDiscount = Utils.checkDoubleBounds(1, 100)) == -1)
                    System.out.println("Invalid input.");
                System.out.println("Please enter the maximum discount:");
                while((maxDiscount = Utils.checkDoubleBounds(1, 100)) == -1)
                    System.out.println("Invalid input.");
            }else{
                minAmount = 1;
                baseDiscount = 0;
                maxDiscount = 0;
            }
            agreement = new ProductAgreement(price, minAmount, baseDiscount, maxDiscount);
            products.put(product, agreement);
        }

        return products.isEmpty() ? contract.getProducts() : products;
    }
}