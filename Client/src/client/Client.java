package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import models.Customer;
import models.Employee;
import models.Invoice;
import models.Product;

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
            JOptionPane.showMessageDialog(null,
                    "Failure to Establish Connection with Server",
                    "Connection Status",
                    JOptionPane.ERROR_MESSAGE
            );
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

    public void sendEmployeeId(String employeeId) {
        try {
            objOs.writeObject(employeeId);
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

    public void sendCustomerId(String customerId) {
        try {
            objOs.writeObject(customerId);
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

    public void sendProductCode(String productCode) {
        try {
            objOs.writeObject(productCode);
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
                    JOptionPane.showMessageDialog(
                            null,
                            "Record added successfully",
                            "Add Record Status",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to remove record",
                            "Remove Record Status",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            if (action.equalsIgnoreCase("Update Employee")) {
                Boolean flag = (Boolean) objIs.readObject();
                if (flag) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Employee updated successfully",
                            "Update Employee Status",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to update record",
                            "Update Employee Status",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            if (action.equalsIgnoreCase("Remove Employee")) {
                Boolean flag = (Boolean) objIs.readObject();
                if (flag) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Employee removed successfully",
                            "Remove Employee Status",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to remove record",
                            "Remove Employee Status",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            if (action.equalsIgnoreCase("Find Employee")) {
                Employee employee = (Employee) objIs.readObject();
                if (employee == null) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Record could not be found",
                            "Find Record Status",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            if (action.equalsIgnoreCase("Add Customer")) {
                Boolean flag = (Boolean) objIs.readObject();
                if (flag) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Record added successfully",
                            "Add Record Status",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to add record",
                            "Add Record Status",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            if (action.equalsIgnoreCase("Update Customer")) {
                Boolean flag = (Boolean) objIs.readObject();
                if (flag) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Customer updated successfully",
                            "Update Customer Status",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to update record",
                            "Update Customer Status",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            if (action.equalsIgnoreCase("Remove Customer")) {
                Boolean flag = (Boolean) objIs.readObject();
                if (flag) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Customer removed successfully",
                            "Remove Customer Status",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to remove record",
                            "Remove Customer Status",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            if (action.equalsIgnoreCase("Find Customer")) {
                Employee employee = (Employee) objIs.readObject();
                if (employee == null) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Record could not be found",
                            "Find Record Status",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            if (action.equalsIgnoreCase("Add Product")) {
                Boolean flag = (Boolean) objIs.readObject();
                if (flag) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Product added successfully",
                            "Add Product Status",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Product could not be added",
                            "Add Product Status",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            if (action.equalsIgnoreCase("Update Product")) {
                Boolean flag = (Boolean) objIs.readObject();
                if (flag) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Product updated successfully",
                            "Update Product Status",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to update record",
                            "Update Product Status",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            if (action.equalsIgnoreCase("Remove Product")) {
                Boolean flag = (Boolean) objIs.readObject();
                if (flag) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Product removed successfully",
                            "Remove Product Status",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to remove record",
                            "Remove Product Status",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            if (action.equalsIgnoreCase("Add Invoice")) {
                Boolean flag = (Boolean) objIs.readObject();
                if (flag) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Invoice added successfully",
                            "Add Invoice Status",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
            if (action.equalsIgnoreCase("Update Invoice")) {
                Boolean flag = (Boolean) objIs.readObject();
                if (flag) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Invoice updated successfully",
                            "Update Invoice Status",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to update record",
                            "Update Invoice Status",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            if (action.equalsIgnoreCase("Remove Invoice")) {
                Boolean flag = (Boolean) objIs.readObject();
                if (flag) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Invoice removed successfully",
                            "Remove Invoice Status",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to remove record",
                            "Remove Invoice Status",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            if (action.equalsIgnoreCase("Find Invoice")) {
                Invoice invoice = (Invoice) objIs.readObject();
                if (invoice == null) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Invoice could not be found",
                            "Find Invoice Status",
                            JOptionPane.ERROR_MESSAGE
                    );
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

    public Employee receiveFindEmployeeResponse() {
        Employee employee = new Employee();
        if (action.equalsIgnoreCase("Find Employee")) {
            try {
                employee = (Employee) objIs.readObject();
                if (employee == null) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Record not found",
                            "Search Result",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (IOException e) {
                System.err.println("IOException: " + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return employee;
    }

    public Customer receiveFindCustomerResponse() {
        Customer customer = new Customer();
        if (action.equalsIgnoreCase("Find Customer")) {
            try {
                customer = (Customer) objIs.readObject();
                if (customer == null) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Record not found",
                            "Search Result",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (IOException e) {
                System.err.println("IOException: " + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return customer;
    }

    public Product receiveFindProductResponse() {
        Product product = new Product();
        if (action.equalsIgnoreCase("Find Product")) {
            try {
                product = (Product) objIs.readObject();
                if (product == null) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Record not found",
                            "Search Result",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (IOException e) {
                System.err.println("IOException: " + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return product;
    }

    public Invoice receiveFindInvoiceResponse() {
        Invoice invoice = new Invoice();
        if (action.equalsIgnoreCase("Find Invoice")) {
            try {
                invoice = (Invoice) objIs.readObject();
            } catch (IOException e) {
                System.err.println("IOException: " + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return invoice;
    }

    public List<Employee> receiveViewEmployeeResponse() {
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
                System.err.println("IOException: " + e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return productList;
    }
}
