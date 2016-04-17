package Application;

import Entities.*;
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


    /** Suppliers managamemnt **/
    public void addSupplier(Supplier supplier){
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
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
        }
    }
    public void editSupplier(Supplier supplier) {
        PreparedStatement ps=null;
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
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
        }
    }
    public void removeSupplier(Supplier supplier){
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
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
        }
    }
    public List<Supplier> findSupplierByID(String id) {
        return FindSupplier("ID",id);
    }
    public List<Supplier> findSuppliersByName(String suppName) {
        return FindSupplier("name",suppName);
    }
    public void reactivateSupplier(Supplier supplier) {
        PreparedStatement ps=null;
        try{
            openConnection();
            String query="UPDATE Suppliers SET active='1' WHERE ID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(supplier.getId()));
            ps.executeUpdate();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
        }
    }
    private List<Supplier> FindSupplier(String idOrName,String parameter){
        List<Supplier> suppliers=new ArrayList<>();
        String name,bankAccount;
        PaymentMethod pm;
        Map<String,String> contacts;
        Contract contract;
        boolean active;
        int id;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            openConnection();
            String queryID="SELECT * FROM Suppliers WHERE ID=?";
            String queryName="SELECT * FROM Suppliers WHERE name LIKE '%"+parameter+"%'";
            if(idOrName.equals("ID")){
                ps=dbConnection.prepareStatement(queryID);
                ps.setString(1,parameter);
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
                contract=getContractBySupplierID(String.valueOf(id));
                contacts=getContactsBySupplierID(String.valueOf(id));
                active=rs.getBoolean("active");
                Supplier supplier=new Supplier(String.valueOf(id),name,bankAccount,pm,contacts,active);
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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return suppliers;
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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return ans;
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
            String query="SELECT *  FROM Contracts WHERE supplierID=?";
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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
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
            String query="SELECT method FROM PaymentMethods WHERE ID=?";
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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return contacts;
        }
    }


    /** Contract Management **/
    public void addContract(Supplier supp) {
        PreparedStatement ps=null;
        try {
            openConnection();
            String query = "INSERT INTO Contracts (supplierID, deliveryMethod, deliveryTime, minAmount, maxAmount, discount) VALUES (?,?,?,?,?,?)";
            ps = dbConnection.prepareStatement(query);
            ps.setInt(1,Integer.parseInt(supp.getId()));
            ps.setInt(2,supp.getContract().getDeliveryMethod().ordinal());
            ps.setInt(3,supp.getContract().getDeliveryTime());
            ps.setInt(4,supp.getContract().getMinDiscountLimits());
            ps.setInt(5,supp.getContract().getMaxDiscountLimits());
            ps.setDouble(6,supp.getContract().getDiscount());
            ps.executeUpdate();
            for(Product product : supp.getContract().getProducts().keySet()){
                query = "INSERT INTO SuppliersProductsPrices (supplierID, productID, price) VALUES (?,?,?)";
                ps = dbConnection.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(supp.getId()));
                ps.setInt(2, Integer.parseInt(product.getId()));
                ps.setDouble(3, supp.getContract().getProducts().get(product));
                ps.executeUpdate();
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
        }
    }
    public void editContract(Supplier supp) {
        PreparedStatement ps=null;
        try{
            openConnection();
            String query="UPDATE Contracts SET deliveryMethod=?,deliveryTime=?,minAmount=?,maxAmount=?,discount=? WHERE ID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setInt(1,supp.getContract().getDeliveryMethod().ordinal());
            ps.setInt(2,supp.getContract().getDeliveryTime());
            ps.setInt(3,supp.getContract().getMinDiscountLimits());
            ps.setInt(4,supp.getContract().getMaxDiscountLimits());
            ps.setDouble(5,supp.getContract().getDiscount());
            ps.setInt(6,Integer.parseInt(supp.getId()));
            ps.executeUpdate();

            query = "DELETE FROM SuppliersProductsPrices WHERE supplierID=?";
            ps = dbConnection.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(supp.getId()));
            ps.executeUpdate();

            for(Product p : supp.getContract().getProducts().keySet()){
                query = "INSERT INTO SuppliersProductsPrices (supplierID, productID, price) VALUES (?,?,?)";
                ps = dbConnection.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(supp.getId()));
                ps.setInt(2, Integer.parseInt(p.getId()));
                ps.setDouble(3,supp.getContract().getProducts().get(p));
                ps.executeUpdate();
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
        }
    }

    /** Order Managementm **/
    public void createOrder(Order order) {
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
                if (ps != null) {
                    ps.close();
                }
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
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
        }
    }
    public List<Order> findOrdersByEmployee(String employeeID) {
        return FindOrder("employeeID",employeeID);
    }
    public List<Order> findOrdersBySupplier(String supplierID) {
        return FindOrder("supplierID",supplierID);
    }
    private List<Order> FindOrder(String findBy,String parameter){
        List<Order> ans=new ArrayList<>();
        String orderID;
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
            String query="SELECT * FROM Orders WHERE "+findBy+"=?";
            ps=dbConnection.prepareStatement(query);
            ps.setInt(1,Integer.valueOf(parameter));
            rs=ps.executeQuery();
            while(rs.next()){
                orderID=String.valueOf(rs.getInt("orderID"));
                emp=getEmployeeById(rs.getInt("employeeID"));
                supp= findSupplierByID(String.valueOf(rs.getString("supplierID"))).get(0);
                arrived=rs.getBoolean("arrivalStatus");
                totalPrice=rs.getDouble("totalPrice");
                products=getProductsInOrderByOrderID(orderID);
                date=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getString("date"));
                Order order=new Order(orderID,emp,supp,date,arrived,totalPrice,products);
                ans.add(order);
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return ans;
        }
    }
    public List<Order> findOrderByID(String id) {
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
                supp= findSupplierByID(String.valueOf(rs.getString("supplierID"))).get(0);
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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return ans;
        }
    }

    /** Employee management **/
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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
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
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return ans;
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
                if (dbResult != null) {
                    dbResult.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeConnection();
            return connectedUser;
        }
    }
}