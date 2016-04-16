package Application;

import Entities.Employee;
import Entities.Order;
import Entities.Supplier;

import java.util.List;

public interface Database {

    void AddSupplier(Supplier supplier);
    void EditSupplier(Supplier oldSupplier, Supplier newSupplier);
    void RemoveSupplier(Supplier supplier);
    List<Supplier> FindSupplierByID(String id);
    List<Supplier> FindSuppliersByName(String name);
    void AddContract();

    void CreateOrder();
    List<Order> FindOrderByID(String id);
    List<Order> FindOrdersByEmployee(String employeeID);
    List<Order> FindOrdersBySupplier(String supplierID);

    Employee checkCredentials(String username, String password);
}
