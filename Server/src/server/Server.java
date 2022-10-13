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
                String url = "jdbc:mysql://localhost:3306/JWR";
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
        String action = "";
        getDatabaseConnection();
        Employee employee = null;
        Product product = null;
        Customer customer = null;
        Invoice invoice = null;
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
                    } else if (action.equals("Find Employee")) {
                        String empId = (String) objIs.readObject();
                        employee = findEmployeeById(empId);
                        objOs.writeObject(employee);
                    }

                    if (action.equals("Add Customer")) {
                        customer = (Customer) objIs.readObject();
                        addCustomerToFile(customer);
                        objOs.writeObject(true);
                    } else if (action.equals("Find Customer")) {
                        String cusId = (String) objIs.readObject();
                        customer = findCustomerById(cusId);
                        objOs.writeObject(customer);
                    }

                    if (action.equals("Add Product")) {
                        product = (Product) objIs.readObject();
                        addProductToFile(product);
                        objOs.writeObject(true);
                    } else if (action.equals("Find Product")) {
                        String prodId = (String) objIs.readObject();
                        product = findProductById(empId);
                        objOs.writeObject(product);
                    }

                    if (action.equals("Add Invoice")) {
                        invoice = (Invoice) objIs.readObject();
                        addInvoiceToFile(invoice);
                        objOs.writeObject(true);
                    } else if (action.equals("Find Invoice")) {
                        String invoiceId = (String) objIs.readObject();
                        invoice = findInvoiceById(invoiceId);
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
}
