package Application;

import Entities.*;
import javafx.util.Pair;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.sql.*;
import java.util.*;

public class DatabaseImplementation implements Database {
    private Connection dbConnection;

    public DatabaseImplementation(){
        dbConnection=null;
     }
    private void openConnection(){
         try{
             Class.forName("org.sqlite.JDBC");
             dbConnection = DriverManager.getConnection("jdbc:sqlite:SuperLeeDB.db");
             dbConnection.setAutoCommit(false);
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

    }
    public void EditSupplier(Supplier oldSupplier, Supplier newSupplier){

    }
    public void RemoveSupplier(Supplier supplier){

    }
    public List<Supplier> FindSupplierByID(String id) {
        return FindSupplier("ID",id);
        /*List<Supplier> suppliers=new ArrayList<>();
        String name,bankAccount;
        PaymentMethod pm;
        HashMap<String,String> contacts;
        Contract contract;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            openConnection();
            String query="SELECT * FROM Suppliers WHERE ID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                name=rs.getString("name");
                bankAccount=rs.getString("bankAccount");
                pm=getPaymentMethodByID(rs.getInt("paymentMethod"));
                contract=getContractBySupplierID(id);
                contacts=getContactsBySupplierID(id);
                Supplier supplier=new Supplier(id,name,bankAccount,pm,contacts);
                supplier.setContract(contract);
                suppliers.add(supplier);
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        closeConnection();
        return suppliers;*/
    }
    public List<Supplier> FindSuppliersByName(String suppName) {
        return FindSupplier("name",suppName);
        /*List<Supplier> suppliers=new ArrayList<>();
        String name,bankAccount;
        PaymentMethod pm;
        HashMap<String,String> contacts;
        Contract contract;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            openConnection();
            String query="SELECT * FROM Suppliers WHERE name LIKE '%"+suppName+"%'";
            ps=dbConnection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                name=rs.getString("name");
                bankAccount=rs.getString("bankAccount");
                pm=getPaymentMethodByID(rs.getInt("paymentMethod"));
                contract=getContractBySupplierID(suppName);
                contacts=getContactsBySupplierID(suppName);
                Supplier supplier=new Supplier(name,name,bankAccount,pm,contacts);
                supplier.setContract(contract);
                suppliers.add(supplier);
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        closeConnection();
        return suppliers;*/
    }
    private List<Supplier> FindSupplier(String idOrName,String pramater){
        List<Supplier> suppliers=new ArrayList<>();
        String name,bankAccount;
        PaymentMethod pm;
        HashMap<String,String> contacts;
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
                id=Integer.parseInt(pramater);
            }
            else{
                ps=dbConnection.prepareStatement(queryName);
                id=getIdBySupplierName(pramater);

            }
            ps.setString(1,pramater);
            rs=ps.executeQuery();
            while(rs.next()){
                name=rs.getString("name");
                bankAccount=rs.getString("bankAccount");
                pm=getPaymentMethodByID(rs.getInt("paymentMethod"));

                contract=getContractBySupplierID(String.valueOf(id));
                contacts=getContactsBySupplierID(String.valueOf(id));
                Supplier supplier=new Supplier(name,name,bankAccount,pm,contacts);
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
        int deliveryTime;
        double discount;
        Pair<Integer,Integer> amounts;
        HashMap<Product,Integer> products;
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
                amounts=new Pair<>(rs.getInt("minAmount"),rs.getInt("maxAmount"));
                discount=rs.getDouble("discount");
                contract=new Contract(dm,deliveryTime,amounts,discount,products);
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
    private HashMap<Product,Integer> getProductsWithPricesBySupplierID(String id){
        HashMap<Product,Integer> ans=new HashMap<>();
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{
            openConnection();
            String query="SELECT productID,price FROM SuppliersProductsPrices WHERE supplierID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                ans.put(getProductByID(String.valueOf(rs.getInt("productID"))),rs.getInt("price"));
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
    private Product getProductByID(String id){
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
    private HashMap<String,String> getContactsBySupplierID(String id){
        HashMap<String,String> contacts=new HashMap<>();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            openConnection();
            String query="SELECT name,phone FROM SuupliersContacts WHERE supplierID=?";
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
    public void AddContract() {

    }

    /** Order Managementm **/
    public void CreateOrder() {

    }
    private HashMap<Product,Integer> getProductsAndAmountsInOrderByOrderID(int id){
        HashMap<Product,Integer> ans=new HashMap<>();
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
        System.out.println("asasd");
        List<Order> ans=new ArrayList<>();
        Employee emp;
        Supplier supp;
        Date date;
        boolean arrived;
        double totalPrice;
        HashMap<Product,Integer> products;
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
    private HashMap<Product,Integer> getProductsInOrderByOrderID(String id){
        HashMap<Product,Integer> ans=new HashMap<>();
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
            String query="SELECT userName,password FROM Employees WHERE userName=?";
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