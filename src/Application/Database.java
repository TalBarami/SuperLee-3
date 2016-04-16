package Application;

import Entities.*;

import java.util.List;

public interface Database {

    void AddSupplier(Supplier supplier);
    void EditSupplier(Supplier supplier);
    void RemoveSupplier(Supplier supplier);
    List<Supplier> FindSupplierByID(String id);
    List<Supplier> FindSuppliersByName(String name);
    void AddContract(Supplier supp);

    void CreateOrder(Order order);
    void confirmOrder(Order order);
    List<Order> FindOrderByID(String id);
    List<Order> FindOrdersByEmployee(String employeeID);
    List<Order> FindOrdersBySupplier(String supplierID);


    Product getProductByID(String id);

    Employee checkCredentials(String username, String password);
}
