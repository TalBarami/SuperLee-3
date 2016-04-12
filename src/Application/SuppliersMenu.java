package Application;

import Entities.Product;
import Entities.Supplier;

import java.util.HashMap;
import java.util.List;

public class SuppliersMenu {
    private Database database;
    private int selected;

    public SuppliersMenu(Database database){
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
    private static final String supplierSearch[] = {
            "By supplier ID",
            "By supplier name",
            "Back"
    };

    public void DisplaySuppliersMenu(){
        while(true) {
            selected = Utils.MenuSelect(suppliersCommands);
            switch (selected) {
                case 1: // New supplier
                    AddNewSupplier();
                    break;
                case 2: // Edit supplier
                    EditSupplier();
                    break;
                case 3: // Remove supplier
                    RemoveSupplier();
                    break;
                case 4: // Add contract
                    AddContract();
                    break;
                case 5: // View supplier
                    ViewSupplier();
                    break;
                case 6: // View products
                    ViewSuppliersProducts();
                    break;
                case 7: // View manufacturers
                    ViewSuppliersManufacturers();
                    break;
                case 8: // Back
                    return;
            }
        }
    }

    private void AddNewSupplier(){
        // TODO
    }

    private void EditSupplier(){
        // TODO
    }

    private void RemoveSupplier(){
        // TODO
    }

    private void AddContract(){
        // TODO
    }

    private void ViewSupplier(){
        List<Supplier> suppliers = SearchSupplier();
        if(suppliers == null)
            return;
        System.out.println(suppliers.size() == 1 ? "Supplier found:\n" : "Suppliers found:\n");
        for(Supplier s : suppliers){
            System.out.printf("%s\n\n",s.toString());
        }
    }

    private void ViewSuppliersProducts() {
        Supplier supplier = GetSupplier();
        if(supplier == null)
            return;
        HashMap<Product,Integer> products = supplier.getProducts();
        System.out.println(products.size() == 1 ? "Product found:\n" : "Product found:\n");
        for (Product p : products.keySet()) {
            System.out.printf("%s\nSold by %s for %d.\n\n", p, supplier.getName(), products.get(p));
        }
    }

    private void ViewSuppliersManufacturers() {
        Supplier supplier = GetSupplier();
        if(supplier == null)
            return;
        List<String> manufacturers = supplier.getManufacturers();
        System.out.println(manufacturers.size() == 1 ? "Manufacturer found:\n" : "Manufacturers found:\n");
        for (String manufacturer : manufacturers) {
            System.out.printf("%s\n", manufacturer);
        }
    }

    public List<Supplier> SearchSupplier(){
        List<Supplier> suppliers = null;
        while(true) {
            selected = Utils.MenuSelect("How would you like to search?",supplierSearch);
            switch (selected) {
                case 1: // By id
                    System.out.println("Please enter supplier ID:");
                    suppliers = database.FindSupplierByID(Utils.readLine());
                    break;
                case 2: // By name
                    System.out.println("Please enter supplier name:");
                    suppliers = database.FindSuppliersByName(Utils.readLine());
                    break;
                case 3: // Back
                    return null;
            }
            if(suppliers == null || suppliers.isEmpty())
                System.out.println("There were no suppliers matching this search.");
        }
    }

    public Supplier GetSupplier(){
        List<Supplier> suppliers = SearchSupplier();
        if (suppliers == null)
            return null;
        return suppliers.get(0);
    }
}


