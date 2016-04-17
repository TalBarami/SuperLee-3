package Application;

import Entities.*;

import java.util.List;

public interface Database {

    void addSupplier(Supplier supplier);
    void editSupplier(Supplier supplier);
    void removeSupplier(Supplier supplier);
    List<Supplier> findSupplierByID(String id);
    List<Supplier> findSuppliersByName(String name);
    void reactivateSupplier(Supplier supplier);

    void addContract(Supplier supp);
    void editContract(Supplier supp);

    void createOrder(Order order);
    void confirmOrder(Order order);
    List<Order> findOrderByID(String id);
    List<Order> findOrdersByEmployee(String employeeID);
    List<Order> findOrdersBySupplier(String supplierID);


    Product getProductByID(String id);

    Employee checkCredentials(String username, String password);
}
