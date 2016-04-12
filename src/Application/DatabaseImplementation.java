package Application;

import Entities.Order;
import Entities.Supplier;

import java.util.Collection;


public class DatabaseImplementation implements Database {
    @Override
    public void AddSupplier() {

    }

    @Override
    public void EditSupplier() {

    }

    @Override
    public void RemoveSupplier() {

    }

    @Override
    public Collection<Supplier> FindSupplierByID(String id) {
        return null;
    }

    @Override
    public Collection<Supplier> FindSuppliersByName(String name) {
        return null;
    }

    @Override
    public void AddContract() {

    }

    @Override
    public void CreateOrder() {

    }

    @Override
    public Collection<Order> FindOrderByID(String id) {
        return null;
    }

    @Override
    public Collection<Order> FindOrdersByEmployee(String employeeID) {
        return null;
    }

    @Override
    public Collection<Order> FindOrdersBySupplier(String supplierID) {
        return null;
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        return true;
    }
}
