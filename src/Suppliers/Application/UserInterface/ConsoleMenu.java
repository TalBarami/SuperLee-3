package Suppliers.Application.UserInterface;

import Suppliers.Application.Database.Database;
import Suppliers.Entities.Order;
import Suppliers.Entities.Supplier;

import java.util.List;

public interface ConsoleMenu {
    void RunStore();

    Database getDatabase();

    Supplier getSupplier();
    Order getOrder();
    Order getWeeklyOrder();
    List<Supplier> searchSupplier();
    List<Order> searchOrder();
}
