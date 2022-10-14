package server;

import models.Customer;
import models.Employee;
import models.Invoice;
import models.Product;

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
    private ServerSocket serverSocket;
    private Socket connectionSocket;
    private ObjectOutputStream objOs;
    private ObjectInputStream objIs;
    private static Connection dbConn;
    private Statement stmt;
    private ResultSet result;

    public Server() {
        this.createConnection();
        this.waitForRequests();
    }

    private void createConnection() {
        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureStreams() {
        try {
            objOs = new ObjectOutputStream(connectionSocket.getOutputStream());
            objIs = new ObjectInputStream(connectionSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getDatabaseConnection() {
        try {
            if (dbConn == null) {
                String url = "jdbc:mysql://localhost:3306/jwr";
                dbConn = DriverManager.getConnection(url, "root", "");
            }
            JOptionPane.showMessageDialog(null, "DB Connection Established", "Connection Status",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Could not connect to database\n" + e, "Connection Failure",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeConnections() {
        try {
            objOs.close();
            objIs.close();
            connectionSocket.close();
        } catch (IOException e) {
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

                    if (action.equals("Add Employee")) {
                        employee = (Employee) objIs.readObject();
                        addEmployeeToFile(employee);
                        objOs.writeObject(true);
                    } else if (action.equals("View Employees")) {
                        List<Employee> employeeList;
                        employeeList = getEmployeeList();
                        objOs.writeObject(employeeList);
                    }

                    if (action.equals("Add Customer")) {
                        customer = (Customer) objIs.readObject();
                        addCustomerToFile(customer);
                        objOs.writeObject(true);
                    } else if (action.equals("View Customers")) {
                        List<Customer> customerList;
                        customerList = getCustomerList();
                        objOs.writeObject(customerList);
                    }

                    if (action.equals("Add Product")) {
                        product = (Product) objIs.readObject();
                        addProductToFile(product);
                        objOs.writeObject(true);
                    } else if (action.equals("View Inventory")) {
                        List<Product> productList;
                        productList = getInventoryList();
                        objOs.writeObject(productList);
                    }

                    if (action.equals("Add Invoice")) {
                        invoice = (Invoice) objIs.readObject();
                        addInvoiceToFile(invoice);
                        objOs.writeObject(true);
                    } else if (action.equals("Find Invoice")) {
                        String invoiceNum = (String) objIs.readObject();
                        invoice = findInvoiceByNumber(invoiceNum);
                        objOs.writeObject(invoice);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
                this.closeConnections();
            }
        } catch (EOFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCustomerToFile(Customer customer) {
        String query = "INSERT INTO jwr.customers() " +
                "VALUES ('" + customer + "', '" + customer + "', '" + customer +
                "', '" + customer + "', '" + customer +
                "', '" + customer + "')";
        try {
            stmt = dbConn.createStatement();
            if ((stmt.executeUpdate(query) == 1)) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEmployeeToFile(Employee employee) {
        String query = "INSERT INTO jwr.employees() " +
                "VALUES ('" + employee + "', '" + employee + "', '" + employee +
                "', '" + employee + "', '" + employee +
                "', '" + employee + "')";
        try {
            stmt = dbConn.createStatement();
            if ((stmt.executeUpdate(query) == 1)) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
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
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addInvoiceToFile(Invoice invoice) {
        String query = "INSERT INTO jwr.invoices() " +
                "VALUES ('" + invoice + "', '" + invoice + "', '" + invoice +
                "', '" + invoice + "', '" + invoice +
                "', '" + invoice + "')";
        try {
            stmt = dbConn.createStatement();
            if ((stmt.executeUpdate(query) == 1)) {
                objOs.writeObject(true);
            } else {
                objOs.writeObject(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Invoice findInvoiceByNumber(String invoiceNum) {
        Invoice invoice = null;
        String query = "SELECT * FROM jwr.invoices WHERE invoiceNum = " + invoiceNum;
        try {
            stmt = dbConn.createStatement();
            result = stmt.executeQuery(query);

            if (result.next()) {
                //add values to invoice object
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoice;
    }

    private List<Employee> getEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();
        String query = "SELECT * FROM jwr.employees";
        try (Statement stmt = dbConn.createStatement(); ResultSet result = stmt.executeQuery(query)) {
            while (result.next()) {
                employeeList.add(new Employee(result.getString(""), result.getString(""),
                        result.getString(""), result.getString(""),
                        result.getInt(""), result.getFloat("")));
            }
            return employeeList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    private List<Customer> getCustomerList() {
        List<Customer> customerList = new ArrayList<>();
        String query = "SELECT * FROM jwr.customers";
        try (Statement stmt = dbConn.createStatement(); ResultSet result = stmt.executeQuery(query)) {
            while (result.next()) {
                customerList.add(new Customer(result.getString(""), result.getString(""),
                        result.getString(""), result.getString(""),
                        result.getInt(""), result.getFloat("")));
            }
            return customerList;
        } catch (SQLException e) {
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
            e.printStackTrace();
        }
        return productList;
    }
}
