package Suppliers.Application.Database;

import Suppliers.Entities.*;

import java.util.List;

public interface Database {

    void addSupplier(Supplier supplier);
    void editSupplier(Supplier supplier);
    void removeSupplier(Supplier supplier);
    List<Supplier> findSupplierByID(String id);
    List<Supplier> findSuppliersByName(String name);
    void reactivateSupplier(Supplier supplier);
    public List<Supplier> findSuppliersByProductID(int id);

    void addContract(Supplier supp);
    void editContract(Supplier supp);

    void createOrder(Order order);
    void confirmOrder(Order order);
    List<Order> findOrderByID(String id);
    List<Order> findOrdersByEmployee(String employeeID);
    List<Order> findOrdersBySupplier(String supplierID);

    void updateWeeklyOrder(Order order);
    void cancelWeeklyOrder(Order order);

    /*Product getProductByID(String id);*/

    Employee checkCredentials(String username, String password);
}
