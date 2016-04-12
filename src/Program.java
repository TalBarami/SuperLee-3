import Application.ConsoleMenu;
import Application.ConsoleMenuImplementation;
import Application.DatabaseImplementation;


public class Program {
    public static void main(String[] args){
        ConsoleMenu store = new ConsoleMenuImplementation(new DatabaseImplementation());
    }
}
