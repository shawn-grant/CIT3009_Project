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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Could not connect to database\n" + e, "Connection Failure",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            e.printStackTrace();
        } catch (SQLException e) {
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
                System.out.println(productList);
            }
            return productList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
}
