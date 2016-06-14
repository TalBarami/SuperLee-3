package Suppliers.Application.Database;

import Employees_Transports.Backend.Employee;
import Employees_Transports.DL.EmployeeHandler;
import Inventory.dbHandlers.ProductHandler;
import Inventory.dbHandlers.ProductStockHandler;
import Inventory.entities.ProductCatalog;
import Inventory.entities.ProductStock;
import Store.SQLiteConnector;
import Suppliers.Entities.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.sql.*;
import java.util.*;

public class DatabaseImplementation implements Database {
    private Connection dbConnection;
    private ProductHandler productHandler;
    private ProductStockHandler productStockHandler;

    public DatabaseImplementation(){
        dbConnection= SQLiteConnector.getInstance().getConnection();
        productHandler = new ProductHandler();
        productStockHandler = new ProductStockHandler();
    }

    /** Suppliers managamemnt **/
    public void addSupplier(Supplier supplier){
        PreparedStatement ps=null;
        try {
            String query = "INSERT INTO Suppliers (ID, name, paymentMethod, bankAccount, address) VALUES (?,?,?,?,?)";
            ps = dbConnection.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(supplier.getId()));
            ps.setString(2, supplier.getName());
            ps.setInt(3, supplier.getPaymentMethod().ordinal());
            ps.setString(4, supplier.getBankAccount());
            ps.setString(5, supplier.getAddress());
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

        }
    }
    public void editSupplier(Supplier supplier) {
        PreparedStatement ps=null;
        try{
            System.out.println("Entered!");
            String query="UPDATE Suppliers set name=?, paymentMethod=?, bankAccount=?, address=? WHERE ID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1, supplier.getName());
            ps.setInt(2, supplier.getPaymentMethod().ordinal());
            ps.setString(3, supplier.getBankAccount());
            ps.setInt(5, Integer.parseInt(supplier.getId()));
            ps.setString(4, supplier.getAddress());
            System.out.println("Right before execute!");
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

        }
    }
    public void removeSupplier(Supplier supplier){
        PreparedStatement ps=null;
        try {

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

        }
    }
    private List<Supplier> FindSupplier(String idOrName,String parameter){
        List<Supplier> suppliers=new ArrayList<>();
        String name,bankAccount, address;
        PaymentMethod pm;
        Map<String,String> contacts;
        Contract contract;
        boolean active;
        int id;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{

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
                address=rs.getString("address");
                pm=getPaymentMethodByID(rs.getInt("paymentMethod"));
                contract=getContractBySupplierID(String.valueOf(id));
                contacts=getContactsBySupplierID(String.valueOf(id));
                active=rs.getBoolean("active");
                Supplier supplier=new Supplier(String.valueOf(id),name,bankAccount,pm,address,contacts,active);
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

            return suppliers;
        }
    }
    public List<Supplier> findSuppliersByProductID(int id){
        List<Supplier> ans=new ArrayList<>();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{

            String queryID="SELECT supplierID FROM SuppliersProductAgreements WHERE productID=?";
            ps=dbConnection.prepareStatement(queryID);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                int supplierID=rs.getInt("supplierID");
                ans.add(findSupplierByID(String.valueOf(supplierID)).get(0));
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

            return ans;
        }
    }
    /*public Product getProductByID(String id){
        Product ans=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{

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

            return ans;
        }
    }*/
    private Contract getContractBySupplierID(String id){
        DeliveryMethod dm;
        int deliveryTime;
        Map<ProductCatalog,ProductAgreement> productsAgreements;
        Contract contract=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{

            String query="SELECT *  FROM Contracts WHERE supplierID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                productsAgreements=getProductsWithAgreementsBySupplierID(id);
                dm=getDeliveryBySupplierID(Integer.parseInt(id));
                deliveryTime=rs.getInt("deliveryTime");
                contract=new Contract(dm,deliveryTime,productsAgreements);
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

            return contract;
        }

    }
    private Map<ProductCatalog,ProductAgreement> getProductsWithAgreementsBySupplierID(String id){
        Map<ProductCatalog,ProductAgreement> ans=new HashMap<>();
        ResultSet rs=null;
        PreparedStatement ps=null;
        int minAmount;
        double baseDiscount,maxDiscount,price;
        try{

            String query="SELECT * FROM SuppliersProductAgreements WHERE supplierID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                minAmount=rs.getInt("minAmount");
                baseDiscount=rs.getDouble("baseDiscount");
                maxDiscount=rs.getDouble("maxDiscount");
                price=rs.getDouble("price");
                ans.put(productHandler.createProductCatalogByID(rs.getInt("productID")),new ProductAgreement(price,minAmount,baseDiscount,maxDiscount));
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

            return ans;
        }
    }
    /*
    private String getManufacturerByID(int id){
        String ans="";
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{

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

            return ans;
        }
    }*/
    private DeliveryMethod getDeliveryBySupplierID(int id){
        DeliveryMethod dm=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{

            String query="SELECT method FROM Contracts JOIN DeliveryMethods ON Contracts.deliveryMethod = DeliveryMethods.ID WHERE supplierID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                switch (rs.getString("method")){
                    case "Weekly":
                        dm=DeliveryMethod.Weekly;
                        break;
                    case "onDemand":
                        dm=DeliveryMethod.onDemand;
                        break;
                    case "Self":
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

            return dm;
        }
    }
    private PaymentMethod getPaymentMethodByID(int id){
        PaymentMethod method=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{

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

            return method;
        }
    }
    private Map<String,String> getContactsBySupplierID(String id){
        Map<String,String> contacts=new HashMap<>();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{

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

            return contacts;
        }
    }

    /** Contract Management **/
    public void addContract(Supplier supp) {
        PreparedStatement ps=null;
        try {

            String query = "INSERT INTO Contracts (supplierID, deliveryMethod, deliveryTime) VALUES (?,?,?)";
            ps = dbConnection.prepareStatement(query);
            ps.setInt(1,Integer.parseInt(supp.getId()));
            ps.setInt(2,supp.getContract().getDeliveryMethod().ordinal());
            ps.setInt(3,supp.getContract().getDeliveryTime());
            ps.executeUpdate();
            for(ProductCatalog product : supp.getContract().getProducts().keySet()){
                query = "INSERT INTO SuppliersProductAgreements (supplierID, productID, price,minAmount,baseDiscount,maxDiscount) VALUES (?,?,?,?,?,?)";
                ps = dbConnection.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(supp.getId()));
                ps.setInt(2, product.get_id());
                ps.setDouble(3, supp.getContract().getProducts().get(product).getPrice());
                ps.setInt(4, supp.getContract().getProducts().get(product).getMinAmount());
                ps.setDouble(5, supp.getContract().getProducts().get(product).getBaseDiscount());
                ps.setDouble(6, supp.getContract().getProducts().get(product).getMaxDiscount());
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

        }
    }
    public void editContract(Supplier supp) {
        PreparedStatement ps=null;
        try{

            String query="UPDATE Contracts SET deliveryMethod=?,deliveryTime=? WHERE supplierID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setInt(1,supp.getContract().getDeliveryMethod().ordinal());
            ps.setInt(2,supp.getContract().getDeliveryTime());
            ps.setInt(3,Integer.parseInt(supp.getId()));
            ps.executeUpdate();

            query = "DELETE FROM SuppliersProductAgreements WHERE supplierID=?";
            ps = dbConnection.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(supp.getId()));
            ps.executeUpdate();

            for(ProductCatalog product : supp.getContract().getProducts().keySet()){
                query = "INSERT INTO SuppliersProductAgreements (supplierID, productID, price,minAmount,baseDiscount,maxDiscount) VALUES (?,?,?,?,?,?)";
                ps = dbConnection.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(supp.getId()));
                ps.setInt(2, product.get_id());
                ps.setDouble(3, supp.getContract().getProducts().get(product).getPrice());
                ps.setInt(4, supp.getContract().getProducts().get(product).getMinAmount());
                ps.setDouble(5, supp.getContract().getProducts().get(product).getBaseDiscount());
                ps.setDouble(6, supp.getContract().getProducts().get(product).getMaxDiscount());
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

        }
    }

    /** Order Managementm **/
    public void createOrder(Order order) {
        PreparedStatement ps=null;
        try {

            String query = "INSERT INTO Orders (totalPrice, employeeID, supplierID) VALUES (?,?,?)";
            ps = dbConnection.prepareStatement(query);
            ps.setDouble(1, order.getTotalPrice());
            ps.setInt(2, Integer.parseInt(order.getEmployee().getID()));
            ps.setInt(3, Integer.parseInt(order.getSupplier().getId()));
            ps.executeUpdate();
            int lastOrderID=getLastOrderID();
            for(ProductCatalog product : order.getItems().keySet()){
                query = "INSERT INTO ProductsInOrders (productID, orderID, amount) VALUES (?,?,?)";
                ps = dbConnection.prepareStatement(query);
                ps.setInt(1,product.get_id());
                ps.setInt(2, lastOrderID);
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

        }
    }
    public int getLastOrderID(){
        int result=0;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            String query="SELECT MAX(ID) as max FROM Orders";
            ps=dbConnection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                result=rs.getInt("max");
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
            return result;
        }
    }
    public void confirmOrder(Order order) {
        PreparedStatement ps=null;
        try{

            String query="UPDATE Orders set arrivalStatus=1 WHERE ID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setInt(1,Integer.parseInt(order.getId()));
            ps.executeUpdate();

            for(ProductCatalog productCatalog : order.getItems().keySet()){
                int productID = productCatalog.get_id();
                int amount = order.getItems().get(productCatalog);

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, 1);
                String currDate = dateFormat.format(cal.getTime());

                ProductStock product = new ProductStock(productID, amount, currDate, true);
                productStockHandler.addProductStock(product);
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
        String orderID,sourceAddress;
        Employee emp;
        Supplier supp;
        Date date;
        boolean arrived;
        double totalPrice;
        Map<ProductCatalog,Integer> products;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{

            String query="SELECT * FROM Orders WHERE "+findBy+"=?";
            ps=dbConnection.prepareStatement(query);
            //ps.setInt(1,Integer.valueOf(parameter));
            ps.setString(1, parameter);
            rs=ps.executeQuery();
            while(rs.next()){
                orderID=String.valueOf(rs.getInt("ID"));
                emp= EmployeeHandler.getInstance().getEmployeeByID(String.valueOf(rs.getInt("employeeID"))); // TODO - CHECK!
                supp= findSupplierByID(String.valueOf(rs.getString("supplierID"))).get(0);
                arrived=rs.getBoolean("arrivalStatus");
                totalPrice=rs.getDouble("totalPrice");
                products=getProductsInOrderByOrderID(orderID);
                date=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getString("date"));
                sourceAddress=rs.getString("sourceAddress");
                Order order=new Order(orderID,emp,supp,date,arrived,totalPrice,products,sourceAddress);
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

            return ans;
        }
    }
    public void updateWeeklyOrder(Order order) {
        PreparedStatement ps=null;
        try{

            String query = "DELETE from ProductsInOrders where orderID=?";
            ps = dbConnection.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(order.getId()));
            ps.executeUpdate();

            for(ProductCatalog product : order.getItems().keySet()){
                query = "INSERT INTO ProductsInOrders (productID, orderID, amount) VALUES (?,?,?)";
                ps = dbConnection.prepareStatement(query);
                ps.setInt(1, product.get_id());
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

        }
    }
    public void cancelWeeklyOrder(Order order) {
        PreparedStatement ps=null;
        try{

            String query = "DELETE from Orders where ID=?";
            ps = dbConnection.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(order.getId()));
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

        }
    }
    public List<Order> findOrderByID(String id) {
        List<Order> ans=new ArrayList<>();
        Employee emp;
        Supplier supp;
        Date date;
        boolean arrived;
        double totalPrice;
        Map<ProductCatalog,Integer> products;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sourceAddress;
        try{

            String query="SELECT * FROM Orders WHERE  ID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                emp=EmployeeHandler.getInstance().getEmployeeByID(String.valueOf(rs.getInt("employeeID"))); // TODO - CHECK!
                supp= findSupplierByID(String.valueOf(rs.getString("supplierID"))).get(0);
                arrived=rs.getBoolean("arrivalStatus");
                totalPrice=rs.getDouble("totalPrice");
                products=getProductsInOrderByOrderID(id);
                date=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getString("date"));
                sourceAddress=rs.getString("sourceAddress");
                Order order=new Order(id,emp,supp,date,arrived,totalPrice,products,sourceAddress);
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

            return ans;
        }
    }
    private Map<ProductCatalog,Integer> getProductsInOrderByOrderID(String id){
        Map<ProductCatalog,Integer> ans=new HashMap<>();
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{

            String query="SELECT productID,amount FROM ProductsInOrders WHERE orderID=?";
            ps=dbConnection.prepareStatement(query);
            ps.setString(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                ans.put(productHandler.createProductCatalogByID(rs.getInt("productID")),rs.getInt("amount"));
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

            return ans;
        }
    }

    /** Employee management **/
    /*private Employee getEmployeeById(int id){
        Employee ans=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{

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

            return ans;
        }
    }
    private Employee getEmployeeByUserName(String userName){
        Employee ans=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{

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

            return ans;
        }
    }
    public Employee checkCredentials(String username, String password) {
        Employee connectedUser=null;
        PreparedStatement ps=null;
        ResultSet dbResult=null;
        try{

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

            return connectedUser;
        }
    }*/

    public ProductCatalog getProductByID(int id){
        return productHandler.createProductCatalogByID(id);
    }

    public Map<ProductCatalog,Integer> getMissingProducts(){
        return productStockHandler.GetAllAmountMissingOfProducts();
    }
}