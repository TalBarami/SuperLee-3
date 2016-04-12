package Application;

import Entities.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


public class DatabaseImplementation implements Database {
    private Connection dbConnection;
    private Statement dbStatement;
    private ResultSet dbResult;

     public DatabaseImplementation(){
         dbConnection=null;
         dbStatement=null;
         dbResult=null;
     }

     private void openConnection(){
         try{
             Class.forName("org.sqlite.JDBC");
             dbConnection = DriverManager.getConnection("jdbc:sqlite:SuperLeeDB.db");
             dbConnection.setAutoCommit(false);
             System.out.println("Opened database successfully");
             dbStatement= dbConnection.createStatement();
         }
         catch ( Exception e ) {
             System.err.println( e.getClass().getName() + ": " + e.getMessage() );
             System.exit(0);
         }
     }

    private void closeConnection(){
        try{
            dbResult.close();
            dbStatement.close();
            dbResult.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    @Override
    public void AddSupplier() {

    }

    @Override
    public void EditSupplier() {

    }

    @Override
    public void RemoveSupplier() {

    }

    @Override
    public List<Supplier> FindSupplierByID(String id) {
        List<Supplier> suppliers=new ArrayList<>();
        openConnection();
        String name,bankAccount;
        PaymentMethod pm;
        HashMap<String,String> contacts;
        Contract contract;
        try{
            String query="SELECT * FROM Employees WHERE ID=\""+id+"\";";
            ResultSet rs=dbStatement.executeQuery(query);
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
        return suppliers;
    }

    private Contract getContractBySupplierID(String id){
        return null;
    }

    private Product getProductByID(String id){
return null;
    }

    private DeliveryMethod getDeliveryBySupplierID(int id){
        DeliveryMethod dm=null;
        try{
            String query="SELECT method FROM Employees WHERE ID=\""+id+"\";";
            ResultSet rs=dbStatement.executeQuery(query);
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
        closeConnection();
        return dm;

    }


    private PaymentMethod getPaymentMethodByID(int id){
        PaymentMethod method=null;
        try{
            String query="SELECT * FROM PaymentMethods WHERE ID=\""+id+"\";";
            ResultSet rs=dbStatement.executeQuery(query);
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
        closeConnection();
        return method;
    }

    private HashMap<String,String> getContactsBySupplierID(String id){
        HashMap<String,String> contacts=new HashMap<>();
        PaymentMethod method=null;
        try{
            String query="SELECT (name,phone) FROM SuupliersContacts WHERE supplierID=\""+id+"\";";
            ResultSet rs=dbStatement.executeQuery(query);
            while(rs.next()){
                contacts.put(rs.getString("name"),rs.getString("phone"));
            }
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        closeConnection();
        return contacts;
    }

    @Override
    public Collection<Supplier> FindSuppliersByName(String name) {
        return null;
    }

    @Override
    public void AddContract() {

    }

    @Override
    public void CreateOrder() {

    }

    @Override
    public Collection<Order> FindOrderByID(String id) {
        return null;
    }

    @Override
    public Collection<Order> FindOrdersByEmployee(String employeeID) {
        return null;
    }

    @Override
    public Collection<Order> FindOrdersBySupplier(String supplierID) {
        return null;
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        openConnection();
        boolean answer=false;
        try{
            String query="SELECT COUNT(*) as result FROM Employees WHERE userName=\""+username+"\" and password=\""+password+"\";";
            ResultSet rs=dbStatement.executeQuery(query);
            if(rs.getInt("result")==1)
                answer=true;
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        closeConnection();
        return answer;
    }
}