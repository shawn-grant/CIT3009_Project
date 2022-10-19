package server;

import factories.SessionFactoryBuilder;
import models.Date;
import models.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import view.MainScreen;
import view.SplashScreen;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static Connection dbConn;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream objOs;
    private ObjectInputStream objIs;
    private static LocalDateTime localDateTime;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
    private final SplashScreen splashScreen = new SplashScreen();
    private MainScreen mainScreen;
    int requestAmount = 1;

    public Server() {
        createConnection();
        getDatabaseConnection();
        waitForRequests();
    }

    private void createConnection() {
        try {
            serverSocket = new ServerSocket(8888);
            serverSocket.setReuseAddress(true);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configureStreams() {
        try {
            objOs = new ObjectOutputStream(clientSocket.getOutputStream());
            objIs = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void closeConnections() {
        try {
            objOs.close();
            objIs.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void waitForRequests() {
        mainScreen = new MainScreen(serverSocket);
        splashScreen.dispose();
        mainScreen.setVisible(true);
        System.out.println("Sever is running...");
        try {
            // running infinite loop for getting client request
            while (true) {
                // get current local time
                localDateTime = LocalDateTime.now();

                // socket object to receive incoming clientSocket requests
                clientSocket = serverSocket.accept();

                String clientConnected = "Client connected: " + clientSocket.getInetAddress().getHostAddress() +
                        " @ " + localDateTime.format(dateTimeFormatter);;

                // Displaying that new client is connected to server
                System.out.println("\n" + clientConnected);

                // Update text area
                mainScreen.setTextArea(clientConnected);

                // create a new thread object
                ClientHandler clientHandler = new ClientHandler();

                // This thread will handle the client separately
                new Thread(clientHandler).start();
            }
        } catch (SocketException e) {
            System.err.println("SocketException: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
            }
        }
    }

    public void getDatabaseConnection() {
        try {
            if (dbConn == null) {
                String url = "jdbc:mysql://localhost:3306/jwr";
                dbConn = DriverManager.getConnection(url, "root", "");
            }
            localDateTime = LocalDateTime.now();
            System.out.println("DB Connection Established @ " + localDateTime.format(dateTimeFormatter));
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
                    "Could not connect to database jwr." +
                            "\n" + e.getMessage() +
                            "\nRetry?",
                    "Connection Failure",
                    JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE
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

    /**
     * Create queries
     */
    public static void createJWRDatabase() {
        final String DB_URL = "jdbc:mysql://localhost:3306/";
        final String USER = "root";
        final String PASS = "";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()
        ) {
            String query = "CREATE DATABASE IF NOT EXISTS jwr";
            if ((stmt.executeUpdate(query)) == 0) {
                System.out.println("JWR Database created.");
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    public static void createEmployeeTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE employee(ID varchar(10) NOT NULL, firstName varchar(25)," +
                    "lastName varchar(25), dob varchar(25), address varchar(80), telephone varchar(25), " +
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
            String query = "CREATE TABLE customer(ID varchar(10) NOT NULL, firstName varchar(25)," +
                    "lastName varchar(25), dob varchar(25), address varchar(80), telephone varchar(25)," +
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
                System.out.println("Product table created.");
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
                    "item_name varchar(40), quantity int, employeeID varchar(10), customerID varchar(10), " +
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

    /**
     * Insert queries
     **/
    public void addCustomerData(Customer customer) throws IOException {
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                session.save(customer);
                transaction.commit();
                objOs.writeObject(true);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    public void addEmployeeData(Employee employee) throws IOException {
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                session.save(employee);
                transaction.commit();
                objOs.writeObject(true);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    public void addProductData(Product product) throws IOException {
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                session.save(product);
                transaction.commit();
                objOs.writeObject(true);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    public void addInvoiceData(Invoice invoice) throws IOException {
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                session.save(invoice);
                transaction.commit();
                objOs.writeObject(true);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    /**
     * Update queries
     **/
    public void updateEmployeeData(Employee employee) throws IOException {
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                session.update(employee);
                transaction.commit();
                objOs.writeObject(true);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    public void updateCustomerData(Customer customer) throws IOException {
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                session.update(customer);
                transaction.commit();
                objOs.writeObject(true);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    public void updateInvoiceData(Invoice invoice) throws IOException {
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                session.update(invoice);
                transaction.commit();
                objOs.writeObject(true);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    public void updateProductData(Product product) throws IOException {
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                session.update(product);
                transaction.commit();
                objOs.writeObject(true);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    /**
     * Select queries
     **/
    private Employee getEmployeeData(String employeeId) {
        Employee employee = null;
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                employee = session.get(Employee.class, employeeId);
                transaction.commit();
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    private Customer getCustomerData(String customerId) {
        Customer customer = null;
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                customer = session.get(Customer.class, customerId);
                transaction.commit();
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    private Invoice getInvoiceData(String invoiceNum) {
        Invoice invoice = null;
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                invoice = session.get(Invoice.class, invoiceNum);
                transaction.commit();
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoice;
    }

    private Product getProductData(String productCode) {
        Product product = null;
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                product = session.get(Product.class, productCode);
                transaction.commit();
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    /**
     * Select queries for all
     **/
    private List<Employee> getEmployeeList() {
        List<Employee> employeeList = null;
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                employeeList = (List<Employee>) session.createQuery("FROM employee").getResultList();
                transaction.commit();
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    private List<Customer> getCustomerList() {
        List<Customer> customerList = null;
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                customerList = (List<Customer>) session.createQuery("FROM customer").getResultList();
                transaction.commit();
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }

    private List<Product> getInventoryList() {
        List<Product> productList = null;
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                productList = (List<Product>) session.createQuery("FROM product").getResultList();
                transaction.commit();
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    /**
     * Delete queries
     **/
    public void removeEmployeeData(String employeeId) throws IOException {
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                Employee employee = session.get(Employee.class, employeeId);
                session.delete(employee);
                transaction.commit();
                objOs.writeObject(true);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    public void removeCustomerData(String customerId) throws IOException {
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                Customer customer = session.get(Customer.class, customerId);
                session.delete(customer);
                transaction.commit();
                objOs.writeObject(true);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    public void removeProductData(String productCode) throws IOException {
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                Product product = session.get(Product.class, productCode);
                session.delete(product);
                transaction.commit();
                objOs.writeObject(true);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    public void removeInvoiceData(String invoiceNum) throws IOException {
        try (Session session = SessionFactoryBuilder.getSession()) {
            Transaction transaction;
            if (session != null) {
                transaction = session.beginTransaction();
                Invoice invoice = session.get(Invoice.class, invoiceNum);
                session.delete(invoice);
                transaction.commit();
                objOs.writeObject(true);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            e.printStackTrace();
            objOs.writeObject(false);
        }
    }

    class ClientHandler implements Runnable {

        @Override
        public void run() {
            String action;
            Employee employee;
            Product product;
            Customer customer;
            Invoice invoice;
            configureStreams();
            try {
                action = (String) objIs.readObject();
                System.out.println("Requested action: " + action);
                mainScreen.setRequestsText(requestAmount++);
                mainScreen.setTextArea("\nRequested action: " + action + "\n\n");
                
                if (action.equals("Add Employee")) {
                    employee = (Employee) objIs.readObject();
                    addEmployeeData(employee);
                }
                if (action.equals("Update Employee")) {
                    employee = (Employee) objIs.readObject();
                    updateEmployeeData(employee);
                }
                if (action.equals("View Employees")) {
                    List<Employee> employeeList = getEmployeeList();
                    for (Employee emp : employeeList) {
                        objOs.writeObject(emp);
                    }
                }
                if (action.equals("Find Employee")) {
                    String employeeId = (String) objIs.readObject();
                    employee = getEmployeeData(employeeId);
                    objOs.writeObject(employee);
                }
                if (action.equals("Remove Employee")) {
                    String employeeId = (String) objIs.readObject();
                    removeEmployeeData(employeeId);
                }
                if (action.equals("Add Customer")) {
                    customer = (Customer) objIs.readObject();
                    addCustomerData(customer);
                }
                if (action.equals("Update Customer")) {
                    customer = (Customer) objIs.readObject();
                    updateCustomerData(customer);
                }
                if (action.equals("View Customers")) {
                    List<Customer> customerList = getCustomerList();
                    for (Customer cus : customerList) {
                        objOs.writeObject(cus);
                    }
                }
                if (action.equals("Find Customer")) {
                    String customerId = (String) objIs.readObject();
                    customer = getCustomerData(customerId);
                    objOs.writeObject(customer);
                }
                if (action.equals("Remove Customer")) {
                    String customerId = (String) objIs.readObject();
                    removeCustomerData(customerId);
                }
                if (action.equals("Add Product")) {
                    product = (Product) objIs.readObject();
                    addProductData(product);
                }
                if (action.equals("Update Product")) {
                    product = (Product) objIs.readObject();
                    updateProductData(product);
                }
                if (action.equals("Find Product")) {
                    String productCode = (String) objIs.readObject();
                    product = getProductData(productCode);
                    objOs.writeObject(product);
                }
                if (action.equals("Remove Product")) {
                    String productCode = (String) objIs.readObject();
                    removeProductData(productCode);
                }
                if (action.equals("View Inventory")) {
                    List<Product> productList = getInventoryList();
                    for (Product prod : productList) {
                        objOs.writeObject(prod);
                    }
                }
                if (action.equals("Add Invoice")) {
                    invoice = (Invoice) objIs.readObject();
                    addInvoiceData(invoice);
                }
                if (action.equals("Update Invoice")) {
                    invoice = (Invoice) objIs.readObject();
                    updateInvoiceData(invoice);
                }
                if (action.equals("Find Invoice")) {
                    String invoiceNum = (String) objIs.readObject();
                    invoice = getInvoiceData(invoiceNum);
                    objOs.writeObject(invoice);
                }
                if (action.equals("Remove Invoice")) {
                    String invoiceNum = (String) objIs.readObject();
                    removeInvoiceData(invoiceNum);
                }
            } catch (EOFException e) {
                System.err.println("EOFException: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.err.println("ClassNotFoundException: " + e.getMessage());
            } catch (ClassCastException e) {
                System.err.println("ClassCastException: " + e.getMessage());
            } finally {
                closeConnections();
            }
        }
    }
}