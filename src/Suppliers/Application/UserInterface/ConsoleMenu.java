package Suppliers.Application.UserInterface;

import Suppliers.Application.Database.Database;
import Suppliers.Entities.Employee;
import Suppliers.Entities.Order;
import Suppliers.Entities.Supplier;

import java.util.List;

public interface ConsoleMenu {
    void RunStore();

    Database getDatabase();
    Employee getConnected();

    Supplier getSupplier();
    Order getOrder();
    List<Supplier> searchSupplier();
    List<Order> searchOrder();
}
