package Application;

import Entities.Order;
import Entities.Supplier;

import java.util.Collection;

public interface Database {

    void AddSupplier();
    void EditSupplier();
    void RemoveSupplier();
    Supplier FindSupplierByID(String id);
    Collection<Supplier> FindSuppliersByName(String name);

    void AddContract();

    void CreateOrder();
    Order FindOrderByID(String id);
    Collection<Order> FindOrdersByEmployee(String employeeID);
    Collection<Order> FindOrdersBySupplier(String supplierID);

    boolean checkCredentials(String username, String password);
}
