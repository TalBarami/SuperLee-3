package Application;

import Entities.Employee;
import Entities.Order;
import Entities.Product;
import Entities.Supplier;

import java.util.List;

public interface Database {

    void AddSupplier(Supplier supplier);
    void EditSupplier(Supplier oldSupplier, Supplier newSupplier);
    void RemoveSupplier(Supplier supplier);
    List<Supplier> FindSupplierByID(String id);
    List<Supplier> FindSuppliersByName(String name);
    void AddContract();

    void CreateOrder(Order order);
    void confirmOrder(Order order);
    List<Order> FindOrderByID(String id);
    List<Order> FindOrdersByEmployee(String employeeID);
    List<Order> FindOrdersBySupplier(String supplierID);

    Product findProductByID(String id);


    Employee checkCredentials(String username, String password);
}
