import Suppliers.Application.UserInterface.ConsoleMenu;
import Suppliers.Application.UserInterface.ConsoleMenuImplementation;
import Suppliers.Application.Database.DatabaseImplementation;


public class Program {
    public static void main(String[] args){
        ConsoleMenu store = new ConsoleMenuImplementation(new DatabaseImplementation());
    }
}
