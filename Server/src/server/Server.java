package server;

import models.Date;
import models.*;

import javax.swing.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static Connection dbConn;
    private ServerSocket serverSocket;
    private Socket connectionSocket;
    private ObjectOutputStream objOs;
    private ObjectInputStream objIs;
    private Statement stmt;
    private ResultSet result;

    public Server() {
        this.createConnection();
        this.waitForRequests();
    }

    public static void getDatabaseConnection() {
        try {
            if (dbConn == null) {
                String url = "jdbc:mysql://localhost:3306/jwr";
                dbConn = DriverManager.getConnection(url, "root", "Bo$$2001");
            }
            /*JOptionPane.showMessageDialog(null, "DB Connection Established", "Connection Status",
                    JOptionPane.INFORMATION_MESSAGE);*/
            System.out.println("DB Connection Established");
            createEmployeeTable();
            createCustomerTable();
            createInventoryTable();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            boolean isYes;
            int selection = JOptionPane.showConfirmDialog(null, "Could not connect to database\nRetry?" + e, "Connection Failure",
                    JOptionPane.YES_NO_OPTION);
            isYes = (selection == JOptionPane.YES_OPTION);
            if (isYes) {
                createJWRDatabase();
                getDatabaseConnection();
            } else {
                System.exit(0);
            }
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void createJWRDatabase() {
        final String JBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost:3306/";
        final String USER = "root";
        final String PASS = "Bo$$2001";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()
        ) {
            Class.forName(JBC_DRIVER);
            String query = "CREATE DATABASE IF NOT EXISTS jwr";
            if ((stmt.executeUpdate(query)) == 0) {
                System.out.println("JWR Database created.");
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void createEmployeeTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE employees(empId int NOT NULL AUTO_INCREMENT, first_name varchar(25)," +
                    "last_name varchar(25), dob varchar(25), address varchar(40), telephone varchar(25), " +
                    "email varchar(25), type varchar(25), department varchar(40), PRIMARY KEY(empId))";

            if ((stmt.executeUpdate(query)) == 0) {
                System.out.println("Employee table created.");
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void createCustomerTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE customers(cusId int NOT NULL AUTO_INCREMENT, first_name varchar(25)," +
                    "last_name varchar(25), dob varchar(25), address varchar(40), telephone varchar(25), " +
                    "email varchar(25), membershipDate varchar(25), membershipExpDate varchar(40), PRIMARY KEY(cusId))";

            if ((stmt.executeUpdate(query)) == 0) {
                System.out.println("Customer table created.");
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void createInventoryTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE inventory(product_code varchar(10) NOT NULL, product_name varchar(25)," +
                    "short_desc varchar(25), long_desc varchar(70), stock int, unit_price float, PRIMARY KEY(product_code))";

            if ((stmt.executeUpdate(query)) == 0) {
                System.out.println("Inventory table created.");
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createConnection() {
        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configureStreams() {
        try {
            objOs = new ObjectOutputStream(connectionSocket.getOutputStream());
            objIs = new ObjectInputStream(connectionSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void closeConnections() {
        try {
            objOs.close();
            objIs.close();
            connectionSocket.close();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void waitForRequests() {
        String action;
        getDatabaseConnection();
        Employee employee;
        Product product;
        Customer customer;
        Invoice invoice;
        try {
            while (true) {
                connectionSocket = serverSocket.accept();
                this.configureStreams();
                try {
                    action = (String) objIs.readObject();
                    System.out.println("Requested action: " + action);

                    if (action.equals("Add Employee")) {
                        employee = (Employee) objIs.readObject();
                        addEmployeeToFile(employee);
                        objOs.writeObject(true);
                    }
                    if (action.equals("View Employees")) {
                        List<Employee> employeeList = getEmployeeList();
                        for (Employee emp : employeeList) {
                            objOs.writeObject(emp);
                        }
                    }
                    if (action.equals("Add Customer")) {
                        customer = (Customer) objIs.readObject();
                        addCustomerToFile(customer);
                        objOs.writeObject(true);
                    }
                    if (action.equals("View Customers")) {
                        List<Customer> customerList = getCustomerList();
                        for (Customer cus : customerList) {
                            objOs.writeObject(cus);
                        }
                    }
                    if (action.equals("Add Product")) {
                        product = (Product) objIs.readObject();
                        addProductToFile(product);
                        objOs.writeObject(true);
                    }
                    if (action.equals("Update Product")) {
                        product = (Product) objIs.readObject();
                        updateProductData(product);
                        objOs.writeObject(true);
                    }
                    if (action.equals("View Inventory")) {
                        List<Product> productList = getInventoryList();
                        for (Product prod : productList) {
                            objOs.writeObject(prod);
                        }
                    }
                    if (action.equals("Add Invoice")) {
                        invoice = (Invoice) objIs.readObject();
                        addInvoiceToFile(invoice);
                        objOs.writeObject(true);
                    }
                    if (action.equals("Find Invoice")) {
                        String invoiceNum = (String) objIs.readObject();
                        invoice = findInvoiceByNumber(invoiceNum);
                        objOs.writeObject(invoice);
                    }
                } catch (ClassNotFoundException e) {
                    System.err.println("ClassNotFoundException: " + e.getMessage());
                } catch (ClassCastException e) {
                    System.err.println("ClassCastException: " + e.getMessage());
                }
                this.closeConnections();
            }
        } catch (EOFException e) {
            System.err.println("EOFException: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addCustomerToFile(Customer customer) {
        String query = "INSERT INTO jwr.customers(cusId, first_name, last_name, dob, address, " +
                "telephone, membershipDate, membershipExpDate) " + "VALUES ('" + customer.getId() +
                "', '" + customer.getFirstName() + "', '" + customer.getLastName() + "', '" + customer.getDOB() +
                "', '" + customer.getAddress() + "', '" + customer.getTelephone() + "', '" + customer.getEmail() +
                "', '" + customer.getMembershipDate() + "', '" + customer.getMembershipExpiryDate() + "')";
        try {
            stmt = dbConn.createStatement();
            if ((stmt.executeUpdate(query) == 1)) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addEmployeeToFile(Employee employee) {
        String query = "INSERT INTO jwr.employees(empId, first_name, last_name, dob, address, telephone, " +
                "type, department) " + "VALUES ('" + employee.getId() + "', '" + employee.getFirstName() +
                "', '" + employee.getLastName() + "', '" + employee.getDOB() + "', '" + employee.getAddress() +
                "', '" + employee.getTelephone() + "', '" + employee.getType() +
                "', '" + employee.getDepartment() + "')";
        try {
            stmt = dbConn.createStatement();
            if ((stmt.executeUpdate(query) == 1)) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addProductToFile(Product product) {
        String query = "INSERT INTO jwr.inventory(product_code, product_name, short_desc, long_desc, stock, unit_price) " +
                "VALUES ('" + product.getCode() + "', '" + product.getName() + "', '" + product.getShortDescription() +
                "', '" + product.getLongDescription() + "', '" + product.getItemInStock() +
                "', '" + product.getUnitPrice() + "')";
        try {
            stmt = dbConn.createStatement();
            if ((stmt.executeUpdate(query) == 1)) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addInvoiceToFile(Invoice invoice) {
        String query = "INSERT INTO jwr.invoices(invoiceNum, billing_date, item_name, quantity, employee, customer) " +
                "VALUES ('" + invoice.getInvoiceNumber() + "', '" + invoice.getBillingDate()
                + "', '" + invoice.getItemName() + "', '" + invoice.getQuantity() + "', '" + invoice.getEmployee() +
                "', '" + invoice.getCustomer() + "')";
        try {
            stmt = dbConn.createStatement();
            if ((stmt.executeUpdate(query) == 1)) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* Update queries */
    public void updateEmployeeData(Employee employee) {
        String query = "UPDATE jwr.employees SET first_name = '" + employee.getFirstName() + "', " +
                "last_name = '" + employee.getLastName() + "', " +
                "address = '" + employee.getAddress() + "', " +
                "type = '" + employee.getType() + "', " +
                "department = '" + employee.getDepartment() +
                "telephone = '" + employee.getTelephone() +
                "dob = '" + employee.getDOB() +
                "email = '" + employee.getEmail() +
                "'WHERE empId = '" + employee.getId() + "'";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            if ((stmt.executeUpdate(query) == 1)) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateCustomerData(Customer customer) {
        String query = "UPDATE jwr.customers SET first_name = '" + customer.getFirstName() + "', " +
                "last_name = '" + customer.getLastName() + "', " +
                "address = '" + customer.getAddress() + "', " +
                "telephone = '" + customer.getTelephone() +
                "dob = '" + customer.getDOB() +
                "email = '" + customer.getEmail() +
                "membershipDate = '" + customer.getMembershipDate() +
                "membershipExpDate = '" + customer.getMembershipExpiryDate() +
                "'WHERE cusId = '" + customer.getId() + "'";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            if ((stmt.executeUpdate(query) == 1)) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateInvoiceData(Invoice invoice) {
        String query = "UPDATE jwr.invoices SET billing_date = '" + invoice.getBillingDate() + "', " +
                "employee = '" + invoice.getEmployee() + "', " +
                "customer = '" + invoice.getCustomer() + "', " +
                "item_name = '" + invoice.getItemName() + "', " +
                "quantity = '" + invoice.getQuantity() +
                "'WHERE invoiceNum = '" + invoice.getInvoiceNumber() + "'";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            if ((stmt.executeUpdate(query) == 1)) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateProductData(Product product) {
        String query = "UPDATE jwr.inventory SET product_name = '" + product.getName() + "', " +
                "short_desc = '" + product.getShortDescription() + "', " +
                "long_desc = '" + product.getLongDescription() + "', " +
                "stock = '" + product.getItemInStock() + "', " +
                "unit_price = '" + product.getUnitPrice() +
                "'WHERE product_code = '" + product.getCode() + "'";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            if ((stmt.executeUpdate(query) == 1)) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* Select queries */
    private Invoice findInvoiceByNumber(String invoiceNum) {
        Invoice invoice = new Invoice();
        String query = "SELECT * FROM jwr.invoices WHERE invoiceNum = " + invoiceNum;
        try {
            stmt = dbConn.createStatement();
            result = stmt.executeQuery(query);

            if (result.next()) {
                invoice.setInvoiceNumber(result.getInt("invoiceNum"));
                Date billingDate = null;
                invoice.setItemName(result.getString("itemName"));
                //invoice.setCustomer(result.getString("customer"));
                //invoice.setEmployee(result.getString("employee"));
                invoice.setQuantity(result.getInt("quantity"));
                invoice.setBillingDate(billingDate);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return invoice;
    }

    private List<Employee> getEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();
        String query = "SELECT * FROM jwr.employees";
        try (Statement stmt = dbConn.createStatement(); ResultSet result = stmt.executeQuery(query)) {
            while (result.next()) {
                String id = result.getString("empId");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                Date DOB = null;
                String address = result.getString("address");
                String telephone = result.getString("telephone");
                String email = result.getString("email");
                String type = result.getString("type");
                String department = result.getString("department");
                employeeList.add(new Employee(id, firstName, lastName, DOB, address, telephone, email,
                        type, department));
            }
            return employeeList;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return employeeList;
    }

    private List<Customer> getCustomerList() {
        List<Customer> customerList = new ArrayList<>();
        String query = "SELECT * FROM jwr.customers";
        try (Statement stmt = dbConn.createStatement(); ResultSet result = stmt.executeQuery(query)) {
            while (result.next()) {
                String id = result.getString("cusId");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                Date DOB = null;
                String address = result.getString("address");
                String telephone = result.getString("telephone");
                String email = result.getString("email");
                Date membershipDate = null;
                Date membershipExpiryDate = null;
                customerList.add(new Customer(id, firstName, lastName, DOB, address, telephone, email, membershipDate
                        , membershipExpiryDate));
            }
            return customerList;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return customerList;
    }

    private List<Product> getInventoryList() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM jwr.inventory";
        try (Statement stmt = dbConn.createStatement(); ResultSet result = stmt.executeQuery(query)) {
            while (result.next()) {
                productList.add(new Product(result.getString("product_code"), result.getString("product_name"),
                        result.getString("short_desc"), result.getString("long_desc"),
                        result.getInt("stock"), result.getFloat("unit_price")));
            }
            return productList;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return productList;
    }
}
