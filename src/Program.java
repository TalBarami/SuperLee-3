import Application.ConsoleInterface;
import Application.ConsoleInterfaceImplementation;
import Application.DatabaseImplementation;


public class Program {
    public static void main(String[] args){
        ConsoleInterface store = new ConsoleInterfaceImplementation(new DatabaseImplementation());
    }
}
