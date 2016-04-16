package Application;

import Entities.*;
import javafx.util.Pair;
import org.sqlite.SQLiteConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.sql.*;
import java.util.*;

public class DatabaseImplementation implements Database {
    private Connection dbConnection;
    private final String DB_URL = "jdbc:sqlite:SuperLeeDB.db";
    private final String DRIVER = "org.sqlite.JDBC";

    public DatabaseImplementation(){
        dbConnection=null;
     }
    private void openConnection(){
         try{
             Class.forName(DRIVER);
             SQLiteConfig config=new SQLiteConfig();
             config.enforceForeignKeys(true);
             dbConnection = DriverManager.getConnection(DB_URL,config.toProperties());
             dbConnection.setAutoCommit(true);
         }
         catch ( Exception e ) {
             System.err.println( e.getClass().getName() + ": " + e.getMessage() );
             System.exit(0);
         }
     }
    private void closeConnection(){
        if (dbConnection==null)
            return;
        try{
            dbConnection.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    public Employee checkCredentials(String username, String password) {
        Employee connectedUser=null;
        PreparedStatement ps=null;
        ResultSet dbResult=null;
        try{
            openConnection();
            String query="SELECT COUNT(*) as result FROM Employees WHERE userName=? and password=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1,username);
            ps.setString(2,password);
            dbResult=ps.executeQuery();

            if(dbResult.getInt("result")==1){
                connectedUser=getEmployeeByUserName(username);
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                ps.close();
                dbResult.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return connectedUser;
        }
    }

    /** Suppliers managamemnt **/
    public void AddSupplier(Supplier supplier){
        PreparedStatement ps=null;
        try {
            openConnection();
            String query = "INSERT INTO Suppliers (ID, name, paymentMethod, bankAccount) VALUES (?,?,?,?)";
            ps = dbConnection.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(supplier.getId()));
            ps.setString(2, supplier.getName());
            ps.setInt(3, supplier.getPaymentMethod().ordinal());
            ps.setString(4, supplier.getBankAccount());
            ps.executeUpdate();
            for(String name : supplier.getContacts().keySet()){
                query = "INSERT INTO SuppliersContacts (supplierID, name, phone) VALUES (?,?,?)";
                ps = dbConnection.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(supplier.getId()));
                ps.setString(2,name);
                ps.setString(3,supplier.getContacts().get(name));
                ps.executeUpdate();
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
        }
    }
    public void EditSupplier(Supplier supplier) {
        PreparedStatement ps=null;
        System.out.println("entered");
        try{
            openConnection();
            String query="UPDATE Suppliers set name=?, paymentMethod=?, bankAccount=? WHERE ID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1, supplier.getName());
            ps.setInt(2, supplier.getPaymentMethod().ordinal());
            ps.setString(3, supplier.getBankAccount());
            ps.setInt(4, Integer.parseInt(supplier.getId()));
            ps.executeUpdate();

            query = "DELETE from SuppliersContacts where supplierID=?";
            ps = dbConnection.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(supplier.getId()));
            ps.executeUpdate();

            for(String name : supplier.getContacts().keySet()){
                query = "INSERT INTO SuppliersContacts (supplierID, name, phone) VALUES (?,?,?)";
                ps = dbConnection.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(supplier.getId()));
                ps.setString(2,name);
                ps.setString(3,supplier.getContacts().get(name));
                ps.executeUpdate();
            }

        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
        }
    }
    public void RemoveSupplier(Supplier supplier){
        PreparedStatement ps=null;
        try {
            openConnection();
            String query = "UPDATE Suppliers SET active=0 WHERE ID=?";
            ps = dbConnection.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(supplier.getId()));
            ps.executeUpdate();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
        }
    }
    public List<Supplier> FindSupplierByID(String id) {
        return FindSupplier("ID",id);
    }
    public List<Supplier> FindSuppliersByName(String suppName) {
        return FindSupplier("name",suppName);
    }
    private List<Supplier> FindSupplier(String idOrName,String pramater){
        List<Supplier> suppliers=new ArrayList<>();
        String name,bankAccount;
        PaymentMethod pm;
        Map<String,String> contacts;
        Contract contract;
        int id;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            openConnection();
            String queryID="SELECT * FROM Suppliers WHERE ID=?";
            String queryName="SELECT * FROM Suppliers WHERE name LIKE '%"+pramater+"%'";
            if(idOrName.equals("ID")){
                ps=dbConnection.prepareStatement(queryID);
                ps.setString(1,pramater);
            }
            else{
                ps=dbConnection.prepareStatement(queryName);
            }
            rs=ps.executeQuery();
            while(rs.next()){
                id=rs.getInt("ID");
                name=rs.getString("name");
                bankAccount=rs.getString("bankAccount");
                pm=getPaymentMethodByID(rs.getInt("paymentMethod"));
                contract=getContractBySupplierID(name);
                contacts=getContactsBySupplierID(name);
                Supplier supplier=new Supplier(String.valueOf(id),name,bankAccount,pm,contacts);
                supplier.setContract(contract);
                suppliers.add(supplier);
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return suppliers;
        }
    }
    private int getIdBySupplierName(String suppName){
        int id=0;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            openConnection();
            String query="SELECT ID FROM Suppliers WHERE name=?";
            ps.setString(1,suppName);
            rs=ps.executeQuery();
            while(rs.next()){
                id=rs.getInt("ID");
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return id;
        }
    }

    private Contract getContractBySupplierID(String id){
        DeliveryMethod dm;
        int deliveryTime,min,max;
        double discount;
        Map<Product,Double> products;
        Contract contract=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            openConnection();
            String query="SELECT supplierID,price  FROM SuppliersProductsPrices WHERE supplierID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                products=getProductsWithPricesBySupplierID(id);
                dm=getDeliveryBySupplierID(Integer.parseInt(id));
                deliveryTime=rs.getInt("deliveryTime");
                min=rs.getInt("minAmount");
                max=rs.getInt("maxAmount");
                discount=rs.getDouble("discount");
                contract=new Contract(dm,deliveryTime,min,max,discount,products);
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return contract;
        }

    }
    private Map<Product,Double> getProductsWithPricesBySupplierID(String id){
        Map<Product,Double> ans=new HashMap<>();
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{
            openConnection();
            String query="SELECT productID,price FROM SuppliersProductsPrices WHERE supplierID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                ans.put(getProductByID(String.valueOf(rs.getInt("productID"))),rs.getDouble("price"));
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return ans;
        }
    }
    public Product getProductByID(String id){
        Product ans=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{
            openConnection();
            String query="SELECT name,manufactureID FROM Products WHERE ID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                ans=new Product(id,rs.getString("name"),getManufacturerByID(rs.getInt("manufactureID")));
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return ans;
        }
    }
    private String getManufacturerByID(int id){
        String ans="";
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{
            openConnection();
            String query="SELECT name FROM Manufacturers WHERE ID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                ans=rs.getString("name");
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return ans;
        }
    }
    private DeliveryMethod getDeliveryBySupplierID(int id){
        DeliveryMethod dm=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            openConnection();
            String query="SELECT method FROM Contracts JOIN DeliveryMethods ON Contracts.deliveryMethod = DeliveryMethods.ID WHERE supplierID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                switch (rs.getString("method")){
                    case "Weekly":
                        dm=DeliveryMethod.Weekly;
                        break;
                    case "On demand":
                        dm=DeliveryMethod.onDemand;
                        break;
                    case "Self delivery":
                        dm=DeliveryMethod.Self;
                        break;
                }
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return dm;
        }
    }
    private PaymentMethod getPaymentMethodByID(int id){
        PaymentMethod method=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{
            openConnection();
            String query="SELECT method  FROM PaymentMethods JOIN Suppliers ON PaymentMethods.ID = Suppliers.paymentMethod WHERE Suppliers.ID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                switch(rs.getString("method")){
                    case "Cash":
                        method=PaymentMethod.Cash;
                        break;
                    case "Credit card":
                        method=PaymentMethod.CreditCard;
                        break;
                    case "Check":
                        method=PaymentMethod.Check;
                        break;
                }
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return method;
        }
    }
    private Map<String,String> getContactsBySupplierID(String id){
        Map<String,String> contacts=new HashMap<>();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            openConnection();
            String query="SELECT name,phone FROM SuppliersContacts WHERE supplierID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                contacts.put(rs.getString("name"),rs.getString("phone"));
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return contacts;
        }
    }


    /** Contract Management **/
        public void AddContract(Supplier supp) {

    }

    /** Order Managementm **/
    public void CreateOrder(Order order) {
        PreparedStatement ps=null;
        try {
            openConnection();
            String query = "INSERT INTO Orders (totalPrice, employeeID, supplierID) VALUES (?,?,?)";
            ps = dbConnection.prepareStatement(query);
            ps.setDouble(1, order.getTotalPrice());
            ps.setInt(2, Integer.parseInt(order.getSupplier().getId()));
            ps.setInt(3, Integer.parseInt(order.getEmployee().getId()));
            ps.executeUpdate();
            for(Product product : order.getItems().keySet()){
                query = "INSERT INTO ProductsInOrders (productID, orderID, amount) VALUES (?,?,?)";
                ps = dbConnection.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(product.getId()));
                ps.setInt(2, Integer.parseInt(order.getId()));
                ps.setInt(3, order.getItems().get(product));
                ps.executeUpdate();
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
        }
    }
    public void confirmOrder(Order order) {
        PreparedStatement ps=null;
        try{
            openConnection();
            String query="UPDATE Orders set arrivalStatus=1 WHERE ID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setInt(1,Integer.parseInt(order.getId()));
            ps.executeUpdate();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
        }
    }
    private Map<Product,Integer> getProductsAndAmountsInOrderByOrderID(int id){
        Map<Product,Integer> ans=new HashMap<>();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            openConnection();
            String query="SELECT productID,amount FROM ProductsInOrders WHERE orderID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                ans.put(getProductByID(String.valueOf(rs.getInt("productID"))),rs.getInt("amount"));
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return ans;
        }
    }
    public List<Order> FindOrdersByEmployee(String employeeID) {
        return null;
    }
    public List<Order> FindOrdersBySupplier(String supplierID) {
        return null;
    }
    public List<Order> FindOrderByID(String id) {
        List<Order> ans=new ArrayList<>();
        Employee emp;
        Supplier supp;
        Date date;
        boolean arrived;
        double totalPrice;
        Map<Product,Integer> products;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            openConnection();
            String query="SELECT * FROM Orders WHERE  ID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                emp=getEmployeeById(rs.getInt("employeeID"));
                supp=FindSupplierByID(String.valueOf(rs.getString("supplierID"))).get(0);
                arrived=rs.getBoolean("arrivalStatus");
                totalPrice=rs.getDouble("totalPrice");
                products=getProductsInOrderByOrderID(id);
                date=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getString("date"));
                Order order=new Order(id,emp,supp,date,arrived,totalPrice,products);
                ans.add(order);
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return ans;
        }
    }
    private Map<Product,Integer> getProductsInOrderByOrderID(String id){
        Map<Product,Integer> ans=new HashMap<>();
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{
            openConnection();
            String query="SELECT productID,amount FROM ProductsInOrders WHERE orderID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                ans.put(getProductByID(String.valueOf(rs.getInt("productID"))),rs.getInt("amount"));
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return ans;
        }
    }

    private Employee getEmployeeById(int id){
        Employee ans=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            openConnection();
            String query="SELECT userName,password FROM Employees WHERE ID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                ans=new Employee(String.valueOf(id),rs.getString("userName"),rs.getString("password"));
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return ans;
        }
    }
    private Employee getEmployeeByUserName(String userName){
        Employee ans=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            openConnection();
            String query="SELECT ID,userName,password FROM Employees WHERE userName=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1,userName);
            rs=ps.executeQuery();
            while(rs.next()){
                ans=new Employee(String.valueOf(rs.getInt("ID")),userName,rs.getString("password"));
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return ans;
        }
    }
}