package Application;

import Entities.Employee;
import Entities.Order;
import Entities.Supplier;

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
