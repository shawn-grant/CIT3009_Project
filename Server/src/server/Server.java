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
                dbConn = DriverManager.getConnection(url, "root", "");
            }
            /*JOptionPane.showMessageDialog(null, "DB Connection Established", "Connection Status",
                    JOptionPane.INFORMATION_MESSAGE);*/
            System.out.println("DB Connection Established");
            createEmployeeTable();
            createCustomerTable();
            createProductTable();
            createDepartmentTable();
            createInvoiceTable();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            boolean isYes;
            int selection = JOptionPane.showConfirmDialog(
                    null,
                    "Could not connect to " + "database\nRetry?" + e,
                    "Connection Failure",
                    JOptionPane.YES_NO_OPTION
            );
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
        final String PASS = "";
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
            String query = "CREATE TABLE employee(ID int NOT NULL AUTO_INCREMENT, firstName varchar(25)," +
                    "lastName varchar(25), dob varchar(25),address varchar(80), telephone varchar(25), " +
                    "email varchar(25), dept_code varchar(40), employeeType varchar(25), PRIMARY KEY(ID))";

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
            String query = "CREATE TABLE customer(ID int NOT NULL AUTO_INCREMENT, firstName varchar(25)," +
                    "lastName varchar(25), dob varchar(25), parish varchar(80), telephone varchar(25)," +
                    " email varchar(25), membershipDate varchar(25), membershipExpiryDate varchar(40), " +
                    "PRIMARY KEY(ID))";

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

    public static void createProductTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE product(product_code varchar(10) NOT NULL, productName varchar(25)," +
                    "shortDescription varchar(25), longDescription varchar(70), itemInStock int, unitPrice float, " +
                    "PRIMARY KEY(product_code))";

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

    public static void createDepartmentTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE department(dept_code varchar(10) NOT NULL, departmentName varchar(25)," +
                    "PRIMARY KEY(dept_code))";

            if ((stmt.executeUpdate(query)) == 0) {
                System.out.println("Department table created.");
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void createInvoiceTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE invoice(invoice_number varchar(10) NOT NULL, billing_date varchar(25)," +
                    "item_name varchar(40), quantity int, employeeID int, customerID int, " +
                    "PRIMARY KEY(invoice_number))";

            if ((stmt.executeUpdate(query)) == 0) {
                System.out.println("Invoice table created.");
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
                configureStreams();
                try {
                    action = (String) objIs.readObject();
                    System.out.println("Requested action: " + action);

                    if (action.equals("Add Employee")) {
                        employee = (Employee) objIs.readObject();
                        addEmployeeToFile(employee);
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
                    }
                    if (action.equals("Update Product")) {
                        product = (Product) objIs.readObject();
                        updateProductData(product);
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
                closeConnections();
            }
        } catch (EOFException e) {
            System.err.println("EOFException: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addCustomerToFile(Customer customer) throws IOException {
        String query = "INSERT INTO jwr.customer(ID, firstName, lastName, dob, address, " +
                "telephone, email, membershipDate, membershipExpiryDate) " + "VALUES ('" + customer.getId() +
                "', '" + customer.getFirstName() + "', '" + customer.getLastName() + "', '" + customer.getDOB() +
                "', '" + customer.getAddress() + "', '" + customer.getTelephone() + "', '" + customer.getEmail() +
                "', '" + customer.getMembershipDate() + "', '" + customer.getMembershipExpiryDate() + "')";
        try {
            stmt = dbConn.createStatement();
            if (stmt.executeUpdate(query) == 1) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    public void addEmployeeToFile(Employee employee) throws IOException {
        String query = "INSERT INTO jwr.employee(ID, firstName, lastName, dob, address, telephone, email" +
                "dept_code, employeeType) " + "VALUES ('" + employee.getId() + "', '" + employee.getFirstName() +
                "', '" + employee.getLastName() + "', '" + employee.getDOB() + "', '" + employee.getAddress() +
                "', '" + employee.getTelephone() + "', '" + employee.getEmail() + "', '" + employee.getDepartment() +
                "', '" + employee.getType() + "')";
        try {
            stmt = dbConn.createStatement();
            if (stmt.executeUpdate(query) == 1) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    public void addProductToFile(Product product) throws IOException {
        String query = "INSERT INTO jwr.product(product_code, product_name, shortDescription, longDescription, itemInStock, unit_price) " +
                "VALUES ('" + product.getCode() + "', '" + product.getName() + "', '" + product.getShortDescription() +
                "', '" + product.getLongDescription() + "', '" + product.getItemInStock() +
                "', '" + product.getUnitPrice() + "')";
        try {
            stmt = dbConn.createStatement();
            if (stmt.executeUpdate(query) == 1) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    public void addInvoiceToFile(Invoice invoice) throws IOException {
        String query = "INSERT INTO jwr.invoice(invoice_number, billing_date, item_name, quantity, employeeID, customerID) " +
                "VALUES ('" + invoice.getInvoiceNumber() + "', '" + invoice.getBillingDate()
                + "', '" + invoice.getItemName() + "', '" + invoice.getQuantity() + "', '" + invoice.getEmployee() +
                "', '" + invoice.getCustomer() + "')";
        try {
            stmt = dbConn.createStatement();
            if (stmt.executeUpdate(query) == 1) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    /* Update queries */
    public void updateEmployeeData(Employee employee) throws IOException {
        String query = "UPDATE jwr.employee SET firstName = '" + employee.getFirstName() + "', " +
                "lastName = '" + employee.getLastName() + "', " +
                "address = '" + employee.getAddress() + "', " +
                "employeeType = '" + employee.getType() + "', " +
                "dept_code = '" + employee.getDepartment() +
                "telephone = '" + employee.getTelephone() +
                "dob = '" + employee.getDOB() +
                "email = '" + employee.getEmail() +
                "'WHERE ID = '" + employee.getId() + "'";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            if (stmt.executeUpdate(query) == 1) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    public void updateCustomerData(Customer customer) throws IOException {
        String query = "UPDATE jwr.customer SET firstName = '" + customer.getFirstName() + "', " +
                "lastName = '" + customer.getLastName() + "', " +
                "address = '" + customer.getAddress() + "', " +
                "telephone = '" + customer.getTelephone() +
                "dob = '" + customer.getDOB() +
                "email = '" + customer.getEmail() +
                "membershipDate = '" + customer.getMembershipDate() +
                "membershipExpiryDate = '" + customer.getMembershipExpiryDate() +
                "'WHERE ID = '" + customer.getId() + "'";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            if (stmt.executeUpdate(query) == 1) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    public void updateInvoiceData(Invoice invoice) throws IOException {
        String query = "UPDATE jwr.invoice SET billing_date = '" + invoice.getBillingDate() + "', " +
                "employeeID = '" + invoice.getEmployee() + "', " +
                "customerID = '" + invoice.getCustomer() + "', " +
                "item_name = '" + invoice.getItemName() + "', " +
                "quantity = '" + invoice.getQuantity() +
                "'WHERE invoice_number = '" + invoice.getInvoiceNumber() + "'";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            if (stmt.executeUpdate(query) == 1) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    public void updateProductData(Product product) throws IOException {
        String query = "UPDATE jwr.product SET productName = '" + product.getName() + "', " +
                "shortDescription = '" + product.getShortDescription() + "', " +
                "longDescription = '" + product.getLongDescription() + "', " +
                "itemInStock = '" + product.getItemInStock() + "', " +
                "unitPrice = '" + product.getUnitPrice() +
                "'WHERE product_code = '" + product.getCode() + "'";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            if (stmt.executeUpdate(query) == 1) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    /* Select queries */
    private Invoice findInvoiceByNumber(String invoiceNum) {
        Invoice invoice = new Invoice();
        String query = "SELECT * FROM jwr.invoice WHERE invoice_number = " + invoiceNum;
        try {
            stmt = dbConn.createStatement();
            result = stmt.executeQuery(query);

            if (result.next()) {
                invoice.setInvoiceNumber(result.getInt("invoice_number"));
                Date billingDate = null;
                invoice.setItemName(result.getString("item_name"));
                //invoice.setCustomer(result.getString("customerID"));
                //invoice.setEmployee(result.getString("employeeID"));
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
        String query = "SELECT * FROM jwr.employee";
        try (Statement stmt = dbConn.createStatement(); ResultSet result = stmt.executeQuery(query)) {
            while (result.next()) {
                String id = result.getString("ID");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                Date DOB = null;
                String address = result.getString("address");
                String telephone = result.getString("telephone");
                String email = result.getString("email");
                String type = result.getString("employeeType");
                String department = result.getString("dept_code");
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
        String query = "SELECT * FROM jwr.customer";
        try (Statement stmt = dbConn.createStatement(); ResultSet result = stmt.executeQuery(query)) {
            while (result.next()) {
                String id = result.getString("ID");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
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
        String query = "SELECT * FROM jwr.product";
        try (Statement stmt = dbConn.createStatement(); ResultSet result = stmt.executeQuery(query)) {
            while (result.next()) {
                productList.add(new Product(result.getString("product_code"), result.getString("productName"),
                        result.getString("shortDescription"), result.getString("longDescription"),
                        result.getInt("itemInStock"), result.getFloat("unitPrice")));
            }
            return productList;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return productList;
    }
}
