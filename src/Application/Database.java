package Application;

import Entities.Order;
import Entities.Supplier;

import java.util.List;

public interface Database {

    void AddSupplier();
    void EditSupplier();
    void RemoveSupplier();
    List<Supplier> FindSupplierByID(String id);
    List<Supplier> FindSuppliersByName(String name);
    void AddContract();

    void CreateOrder();
    List<Order> FindOrderByID(String id);
    List<Order> FindOrdersByEmployee(String employeeID);
    List<Order> FindOrdersBySupplier(String supplierID);

    boolean checkCredentials(String username, String password);
}
