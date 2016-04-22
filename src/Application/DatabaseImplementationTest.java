package Application;

import Entities.Employee;
import Entities.PaymentMethod;
import Entities.Supplier;
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
        //System.out.println(db.findSupplierByID("-1").size());
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

    }

    @Test
    public void testReactivateSupplier() throws Exception {

    }

    @Test
    public void testGetProductByID() throws Exception {

    }

    @Test
    public void testAddContract() throws Exception {

    }

    @Test
    public void testEditContract() throws Exception {

    }

    @Test
    public void testCreateOrder() throws Exception {

    }

    @Test
    public void testConfirmOrder() throws Exception {

    }

    @Test
    public void testFindOrderByID() throws Exception {

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
}