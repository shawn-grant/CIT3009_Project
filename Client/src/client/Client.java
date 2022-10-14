package client;

import models.Customer;
import models.Employee;
import models.Invoice;
import models.Product;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private Socket connectionSocket;
    private ObjectOutputStream objOs;
    private ObjectInputStream objIs;
    private String action;

    public Client() {
        this.createConnection();
        this.configureStreams();
    }

    private void createConnection() {
        try {
            connectionSocket = new Socket("127.0.0.1", 8888);
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

    public void closeConnections() {
        try {
            objOs.close();
            objIs.close();
            connectionSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAction(String action) {
        this.action = action;
        try {
            objOs.writeObject(action);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEmployee(Employee employee) {
        try {
            objOs.writeObject(employee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCustomer(Customer customer) {
        try {
            objOs.writeObject(customer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendProduct(Product product) {
        try {
            objOs.writeObject(product);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendInvoice(Invoice invoice) {
        try {
            objOs.writeObject(invoice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendInvoiceNumber(String invoiceNum) {
        try {
            objOs.writeObject(invoiceNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveResponse() {
        try {
            if (action.equalsIgnoreCase("Add Employee")) {
                Boolean flag = (Boolean) objIs.readObject();
                if (flag) {
                    JOptionPane.showMessageDialog(null, "Record added successfully", "Add Record Status",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if (action.equalsIgnoreCase("Find Employee")) {
                Employee employee = (Employee) objIs.readObject();
                if (employee == null) {
                    JOptionPane.showMessageDialog(null, "Record could not be found", "Find Record Status",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if (action.equalsIgnoreCase("Add Product")) {
                Boolean flag = (Boolean) objIs.readObject();
                if (flag) {
                    JOptionPane.showMessageDialog(null, "Product added successfully", "Add Product Status",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if (action.equalsIgnoreCase("Add Invoice")) {
                Boolean flag = (Boolean) objIs.readObject();
                if (flag) {
                    JOptionPane.showMessageDialog(null, "Invoice added successfully", "Add Invoice Status",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if (action.equalsIgnoreCase("Find Invoice")) {
                Invoice invoice = (Invoice) objIs.readObject();
                if (invoice == null) {
                    JOptionPane.showMessageDialog(null, "Invoice could not be found", "Find Invoice Status",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> receiveViewEmployeesResponse() {
        List<Employee> employeeList = new ArrayList<>();
        Employee employee;
        if (action.equalsIgnoreCase("View Employees")) {
            try {
                while (true) {
                    employee = (Employee) objIs.readObject();
                    if (employee != null) {
                        employeeList.add(employee);
                    } else {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return employeeList;
    }

    public List<Customer> receiveViewCustomersResponse() {
        List<Customer> customerList = new ArrayList<>();
        Customer customer;
        if (action.equalsIgnoreCase("View Customers")) {
            try {
                while (true) {
                    customer = (Customer) objIs.readObject();
                    if (customer != null) {
                        customerList.add(customer);
                    } else {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return customerList;
    }

    public List<Product> receiveViewInventoryResponse() {
        List<Product> productList = new ArrayList<>();
        Product product;
        if (action.equalsIgnoreCase("View Inventory")) {
            try {
                while (true) {
                    product = (Product) objIs.readObject();
                    if (product != null) {
                        productList.add(product);
                    } else {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return productList;
    }
}
