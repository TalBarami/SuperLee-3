package Application;

import Entities.PaymentMethod;
import Entities.Product;
import Entities.Supplier;

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
            "View supplier's information",
            "View supplier's products.",
            "View supplier's manufacturers.",
            "Back."
    };

    public void displaySuppliersMenu(){
        while(true) {
            selected = Utils.MenuSelect(suppliersCommands);
            switch (selected) {
                case 1: // New supplier
                    addNewSupplier();
                    break;
                case 2: // Edit supplier
                    editSupplier();
                    break;
                case 3: // Remove supplier
                    removeSupplier();
                    break;
                case 4: // Add contract
                    addContract();
                    break;
                case 5: // View supplier
                    viewSupplier();
                    break;
                case 6: // View products
                    viewSuppliersProducts();
                    break;
                case 7: // View manufacturers
                    viewSuppliersManufacturers();
                    break;
                case 8: // Back
                    return;
            }
        }
    }

    private void addNewSupplier(){
        database.AddSupplier(createSupplier());
    }

    private void editSupplier(){
        System.out.println("Select supplier you would like to edit.");
        Supplier oldSupplier = consoleMenu.getSupplier();
        System.out.printf("Supplier found: %s\nPlease enter your new information.\n", oldSupplier);
        Supplier newSupplier = createSupplier(oldSupplier);
        database.EditSupplier(oldSupplier, newSupplier);
    }

    private void removeSupplier(){
        // TODO
    }

    private void addContract(){
        // TODO
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
        Map<Product,Integer> products = supplier.getProducts();
        System.out.println(products.size() == 1 ? "Product found:\n" : "Product found:\n");
        for (Product p : products.keySet()) {
            System.out.printf("%s\nSold by %s for %d.\n\n", p, supplier.getName(), products.get(p));
        }
    }

    private void viewSuppliersManufacturers() {
        Supplier supplier = consoleMenu.getSupplier();
        if(supplier == null)
            return;
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
        if(supplier != null && contacts.isEmpty())
            contacts = supplier.getContacts();
        return new Supplier(id, name, bankAccount, paymentMethod, contacts);
    }

    private PaymentMethod selectPaymentMethods(Supplier supplier){
        PaymentMethod values[] = PaymentMethod.values();
        System.out.println("Please choose one of the following:");
        for (int i = 0; i < values.length; i++)
            System.out.printf("%d. %s\n", i + 1, values[i]);
        String input = Utils.readLine();
        if(input.isEmpty() && supplier != null)
            return supplier.getPaymentMethod();

        selected = Utils.parseInt(input);
        while(selected<1 || selected > values.length){
            System.out.println("Invalid input.");
            for (int i = 0; i < values.length; i++)
                System.out.printf("%d. %s\n", i + 1, values[i]);
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

        return contacts;
    }

    private Map<String, String> selectContacts(){
        return selectContacts(null);
    }
}


