package Application;

import Entities.*;
import com.sun.corba.se.spi.orbutil.fsm.Guard;
import com.sun.javafx.collections.MappingChange;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Tal on 22/04/2016.
 */
public class DatabaseImplementationTest {
    private DatabaseImplementation db;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        db = new DatabaseImplementation();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test // ok 1/10
    public void testAddSupplier() throws Exception {
        assertEquals(0,db.findSupplierByID("-1").size());
        Map<String,String> contacts=new HashMap<>();
        contacts.put("Meni","05435345");
        db.addSupplier(new Supplier("-1","bla bla","123123", PaymentMethod.Cash,contacts));
        assertEquals(1,db.findSupplierByID("-1").size());

        openConnection();
        String query = "DELETE from Suppliers where ID=-1";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.executeUpdate();
        closeConnection();
    }

    @Test
    public void testEditSupplier() throws Exception {

    }

    @Test // ok 3/10
    public void testRemoveSupplier() throws Exception {
        Map<String,String> contacts=new HashMap<>();
        contacts.put("Meni","05435345");
        Supplier sup=new Supplier("-1","bla bla","123123", PaymentMethod.Cash,contacts);
        db.addSupplier(sup);

        openConnection();
        String query="SELECT active FROM Suppliers WHERE ID=-1";
        PreparedStatement ps=connection.prepareStatement(query);
        ResultSet rs=ps.executeQuery();
        assertTrue(rs.getBoolean("active"));
        closeConnection();

        db.removeSupplier(sup);

        openConnection();
        ps=connection.prepareStatement(query);
        rs=ps.executeQuery();

        assertFalse(rs.getBoolean("active"));


        query = "DELETE from Suppliers where ID=-1";
        ps = connection.prepareStatement(query);
        ps.executeUpdate();
        closeConnection();
    }

    @Test
    public void testFindSupplierByID() throws Exception {
        assertEquals(0, db.findSupplierByID("-1").size());
        Map<String,String> contacts=new HashMap<>();
        contacts.put("Meni","05435345");
        Supplier sup=new Supplier("-1","bla bla","123123", PaymentMethod.Cash,contacts);
        db.addSupplier(sup);
        assertEquals(1, db.findSupplierByID("-1").size());
        System.out.println("entering query...");
        executeUpdate("DELETE from Suppliers where ID=-1");
    }

    @Test // ok 6/10
    public void testReactivateSupplier() throws Exception {
        Map<String,String> contacts=new HashMap<>();
        contacts.put("Meni","05435345");
        Supplier sup=new Supplier("-1","bla bla","123123", PaymentMethod.Cash,contacts);
        db.addSupplier(sup);
        db.removeSupplier(sup);

        openConnection();
        String query="SELECT active FROM Suppliers WHERE ID=-1";
        PreparedStatement ps=connection.prepareStatement(query);
        ResultSet rs=ps.executeQuery();
        assertFalse(rs.getBoolean("active"));
        closeConnection();

        db.reactivateSupplier(sup);

        openConnection();
        ps=connection.prepareStatement(query);
        rs=ps.executeQuery();

        assertTrue(rs.getBoolean("active"));

        query = "DELETE from Suppliers where ID=-1";
        ps = connection.prepareStatement(query);
        ps.executeUpdate();
        closeConnection();
    }

    @Test
    public void testGetProductByID() throws Exception {
        ResultSet resultSet = executeQuery("SELECT name FROM Products WHERE ID=1");
        assertTrue(resultSet.getString("name").equals(db.getProductByID("1").getName()));
        closeConnection();
    }

    @Test
    public void testAddContract() throws Exception {
        Map<Product, Double> products = new HashMap();
        products.put(db.getProductByID("1"), 50.0);
        Contract contract = new Contract(DeliveryMethod.onDemand, 2, 30, 1, 50, products);
        // TODO
    }

    @Test
    public void testEditContract() throws Exception {

    }

    @Test //ok test 4/10
    public void testCreateOrder() throws Exception {
        openConnection();
        String query="SELECT MAX(ID) as max FROM Orders";
        PreparedStatement ps=connection.prepareStatement(query);
        int maxOrderNumber=ps.executeQuery().getInt("max") ;
        closeConnection();

        assertEquals(0,(db.findOrderByID(String.valueOf(maxOrderNumber+1))).size());

        Map<Product,Integer> products=new HashMap<>();
        products.put(db.getProductByID("1"),2);
        Order newOrder=new Order(new Employee("1","a","a"),db.findSupplierByID("1").get(0),123,products);
        db.createOrder(newOrder);
        assertEquals(1,(db.findOrderByID(String.valueOf(maxOrderNumber+1))).size());

        openConnection();
        query = "DELETE from Orders where ID=?";
        ps=connection.prepareStatement(query);
        ps.setInt(1,(maxOrderNumber+1));
        ps.executeUpdate();
        //Fixing the auto increment field
        query="UPDATE sqlite_sequence SET seq=? WHERE name=?";
        ps=connection.prepareStatement(query);
        ps.setInt(1,maxOrderNumber);
        ps.setString(2,"Orders");
        ps.executeUpdate();
        closeConnection();
    }

    @Test //ok test 5/10
    public void testConfirmOrder() throws Exception {
        openConnection();
        String query="SELECT MAX(ID) as max FROM Orders";
        PreparedStatement ps=connection.prepareStatement(query);
        int maxOrderNumber=ps.executeQuery().getInt("max") ;
        closeConnection();
        // Adding new order, default arrival status =false
        Map<Product,Integer> products=new HashMap<>();
        products.put(db.getProductByID("1"),2);
        Order newOrder=new Order(new Employee("1","a","a"),db.findSupplierByID("1").get(0),123,products);
        db.createOrder(newOrder);
        // Check that is arrival status is really false
        openConnection();
        query="SELECT arrivalStatus FROM Orders WHERE ID=?";
        ps=connection.prepareStatement(query);
        ps.setInt(1,maxOrderNumber+1);
        assertFalse(ps.executeQuery().getBoolean("arrivalStatus"));
        closeConnection();

        db.confirmOrder((db.findOrderByID(String.valueOf(maxOrderNumber+1))).get(0));
        // Check that is arrival status is really true
        openConnection();
        query="SELECT arrivalStatus FROM Orders WHERE ID=?";
        ps=connection.prepareStatement(query);
        ps.setInt(1,maxOrderNumber+1);
        assertTrue(ps.executeQuery().getBoolean("arrivalStatus"));
        closeConnection();


        openConnection();
        query = "DELETE from Orders where ID=?";
        ps=connection.prepareStatement(query);
        ps.setInt(1,(maxOrderNumber+1));
        ps.executeUpdate();
        //Fixing the auto increment field
        query="UPDATE sqlite_sequence SET seq=? WHERE name=?";
        ps=connection.prepareStatement(query);
        ps.setInt(1,maxOrderNumber);
        ps.setString(2,"Orders");
        ps.executeUpdate();
        closeConnection();
    }

    @Test
    public void testFindOrderByID() throws Exception {


        int maxID = executeQuery("SELECT MAX(ID) as max FROM Orders").getInt("max");
        assertEquals(0,(db.findOrderByID(String.valueOf(maxID+1))).size());
        closeConnection();

        Map<Product,Integer> products=new HashMap<>();
        products.put(db.getProductByID("1"),2);
        Order newOrder=new Order(new Employee("1","a","a"),db.findSupplierByID("1").get(0),123,products);
        db.createOrder(newOrder);
        assertEquals(1,(db.findOrderByID(String.valueOf(maxID+1))).size());

        executeUpdate("DELETE from Orders where ID="+String.valueOf(maxID+1));

        openConnection();// TODO: WTF.
        String query="UPDATE sqlite_sequence SET seq=? WHERE name=?";
        PreparedStatement ps=connection.prepareStatement(query);
        ps.setInt(1,maxID);
        ps.setString(2,"Orders");
        ps.executeUpdate();
        closeConnection();
    }

    @Test// ok 2/10
    public void testCheckCredentials() throws Exception {
        assertNull(db.checkCredentials("Hacker","423423"));
        openConnection();
        String query = "INSERT INTO Employees (ID, userName, password) VALUES (9999,\"Hacker\",\"423423\")";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.executeUpdate();
        closeConnection();
        assertNotNull(db.checkCredentials("Hacker","423423"));

        openConnection();
        query = "DELETE from Employees where ID=9999";
        ps = connection.prepareStatement(query);
        ps.executeUpdate();
        closeConnection();

    }

    // Db utils
    private void openConnection(){
        try{
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config=new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection("jdbc:sqlite:SuperLeeDB.db",config.toProperties());
            connection.setAutoCommit(true);
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    private void closeConnection(){
        if (connection==null)
            return;
        try{
            connection.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    private int executeUpdate(String update) throws java.sql.SQLException{
        openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        int updateValue = preparedStatement.executeUpdate();
        closeConnection();
        return updateValue;
    }

    private ResultSet executeQuery(String query) throws java.sql.SQLException{
        openConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        ResultSet resultSet=preparedStatement.executeQuery();
        return resultSet;
    }
}