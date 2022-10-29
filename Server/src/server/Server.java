package server;

import factories.DBConnectorFactory;
import factories.SessionFactoryBuilder;
import models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import view.MainScreen;
import view.SplashScreen;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Malik Heron
 */
public class Server {

    private static final Logger logger = LogManager.getLogger(Server.class);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
    private final SplashScreen splashScreen = new SplashScreen();
    private int requestAmount = 1;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream objOs;
    private ObjectInputStream objIs;
    private MainScreen mainScreen;

    public Server() {
        createConnection();
        DBConnectorFactory.getDatabaseConnection();
        waitForRequests();
    }

    private void createConnection() {
        try {
            serverSocket = new ServerSocket(8888);
            serverSocket.setReuseAddress(true);
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configureStreams() {
        try {
            objOs = new ObjectOutputStream(clientSocket.getOutputStream());
            objIs = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void closeConnections() {
        try {
            objOs.close();
            objIs.close();
            clientSocket.close();
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void waitForRequests() {
        mainScreen = new MainScreen(serverSocket);
        splashScreen.dispose();
        mainScreen.setVisible(true);
        logger.info("Sever is running");
        try {
            // running infinite loop for getting client request
            while (true) {
                // get current local time
                LocalDateTime localDateTime = LocalDateTime.now();

                // socket object to receive incoming clientSocket requests
                clientSocket = serverSocket.accept();

                String clientConnected = "Client connected: " + clientSocket.getInetAddress().getHostAddress() +
                        " @ " + localDateTime.format(dateTimeFormatter);

                // Displaying that new client is connected to server
                logger.info("\n" + clientConnected);

                // Update text area
                mainScreen.setTextArea(clientConnected);

                // create a new thread object
                ClientHandler clientHandler = new ClientHandler();

                // This thread will handle the client separately
                new Thread(clientHandler).start();
            }
        } catch (SocketException e) {
            logger.warn("SocketException: " + e.getMessage());
        } catch (IOException e) {
            logger.warn("IOException: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                logger.error("IOException: " + e.getMessage());
            }
        }
    }

    /**
     * Insert queries
     **/
    public void addCustomerData(Customer customer) throws IOException {
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(customer);
            transaction.commit();
            objOs.writeObject(true);
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            transaction.rollback();
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } finally {
            session.close();
        }
    }

    public void addEmployeeData(Employee employee) throws IOException {
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(employee);
            transaction.commit();
            objOs.writeObject(true);
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            transaction.rollback();
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } finally {
            session.close();
        }
    }

    public void addProductData(Product product) throws IOException {
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(product);
            transaction.commit();
            objOs.writeObject(true);
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            transaction.rollback();
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } finally {
            session.close();
        }
    }

    public void addInvoiceData(Invoice invoice) throws IOException {
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(invoice);
            transaction.commit();
            objOs.writeObject(true);
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            transaction.rollback();
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } finally {
            session.close();
        }
    }

    public void addInvoiceItemData(List<InvoiceItem> invoiceItemList) throws IOException {
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (InvoiceItem invoiceItem : invoiceItemList) {
                session.save(invoiceItem);
            }
            transaction.commit();
            objOs.writeObject(true);
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            transaction.rollback();
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } finally {
            session.close();
        }
    }

    /**
     * Update queries
     **/
    public void updateEmployeeData(Employee employee) throws IOException {
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(employee);
            transaction.commit();
            objOs.writeObject(true);
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            transaction.rollback();
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } finally {
            session.close();
        }
    }

    public void updateCustomerData(Customer customer) throws IOException {
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(customer);
            transaction.commit();
            objOs.writeObject(true);
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            transaction.rollback();
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } finally {
            session.close();
        }
    }

    public void updateProductData(Product product) throws IOException {
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(product);
            transaction.commit();
            objOs.writeObject(true);
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            transaction.rollback();
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } finally {
            session.close();
        }
    }

    public void updateInventoryData(List<Inventory> inventoryList) throws IOException {
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (Inventory inv : inventoryList) {
                //Get current item from inventory
                Inventory inv2 = getInventoryItem(inv.getId());
                //Update new details and add amount purchased before to amount purchased after
                if (inv2 != null) {
                    Inventory inventory = new Inventory(
                            inv.getId(),
                            inv.getStock(),
                            inv.getUnitPrice(),
                            inv.getAmountPurchased() + inv2.getAmountPurchased()
                    );
                    //Update record
                    session.update(inventory);
                } else {
                    //Save as new record
                    session.save(inv);
                }
            }
            transaction.commit();
            objOs.writeObject(true);
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            transaction.rollback();
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } finally {
            session.close();
        }
    }

    /**
     * Select queries
     **/
    private Employee getEmployeeData(String employeeId) {
        Employee employee = null;
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            employee = session.get(Employee.class, employeeId);
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employee;
    }

    private Customer getCustomerData(String customerId) {
        Customer customer = null;
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            customer = session.get(Customer.class, customerId);
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return customer;
    }

    private Product getProductData(String productCode) {
        Product product = null;
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            product = session.get(Product.class, productCode);
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return product;
    }

    private Invoice getInvoiceData(int invoiceNum) {
        Invoice invoice = null;
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            invoice = session.get(Invoice.class, invoiceNum);
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return invoice;
    }

    private Inventory getInventoryItem(InventoryId inventoryId) {
        Inventory inventory = null;
        Session session = SessionFactoryBuilder.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            inventory = session.get(Inventory.class, inventoryId);
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return inventory;
    }

    /**
     * Select queries for all
     **/
    private List<Employee> getEmployeeList() {
        List<Employee> employeeList = null;
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            employeeList = (List<Employee>) session.createQuery("FROM employee").getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeList;
    }

    private List<Customer> getCustomerList() {
        List<Customer> customerList = null;
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            customerList = (List<Customer>) session.createQuery("FROM customer").getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return customerList;
    }

    private List<Product> getProductList() {
        List<Product> productList = null;
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            productList = (List<Product>) session.createQuery("FROM product").getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return productList;
    }

    private List<Invoice> getInvoiceList() {
        List<Invoice> invoiceList = null;
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            invoiceList = (List<Invoice>) session.createQuery("FROM invoice").getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return invoiceList;
    }

    private List<InvoiceItem> getInvoiceItemList(int invoiceNum) {
        List<InvoiceItem> invoiceItemList = null;
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "FROM invoiceItem WHERE id.invoiceNumber = " + invoiceNum;
            invoiceItemList = (List<InvoiceItem>) session.createQuery(hql).getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return invoiceItemList;
    }

    /**
     * Delete queries
     **/
    public void removeEmployeeData(String employeeId) throws IOException {
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            Employee employee = session.get(Employee.class, employeeId);
            session.delete(employee);
            transaction.commit();
            objOs.writeObject(true);
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            transaction.rollback();
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } finally {
            session.close();
        }
    }

    public void removeCustomerData(String customerId) throws IOException {
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            Customer customer = session.get(Customer.class, customerId);
            session.delete(customer);
            transaction.commit();
            objOs.writeObject(true);
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            transaction.rollback();
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } finally {
            session.close();
        }
    }

    public void removeProductData(String productCode) throws IOException {
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            Product product = session.get(Product.class, productCode);
            session.delete(product);
            transaction.commit();
            objOs.writeObject(true);
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            transaction.rollback();
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } finally {
            session.close();
        }
    }

    public void removeInvoiceData(int invoiceNum) throws IOException {
        Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            Invoice invoice = session.get(Invoice.class, invoiceNum);
            session.delete(invoice);
            //Remove invoice items
            String hql = "FROM invoiceItem WHERE id.invoiceNumber = " + invoiceNum;
            List<InvoiceItem> invoiceItemList = (List<InvoiceItem>) session.createQuery(hql).getResultList();
            for (InvoiceItem invoiceItem : invoiceItemList) {
                session.delete(invoiceItem);
            }
            transaction.commit();
            objOs.writeObject(true);
        } catch (HibernateException e) {
            logger.error("HibernateException: " + e.getMessage());
            transaction.rollback();
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (IOException e) {
            transaction.rollback();
            logger.error("IOException: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            objOs.writeObject(false);
        } finally {
            session.close();
        }
    }

    // Class for handling client requests
    class ClientHandler implements Runnable {

        @Override
        public void run() {
            String action;
            Employee employee;
            Product product;
            Customer customer;
            Invoice invoice;
            Inventory inventory;
            configureStreams();
            try {
                action = (String) objIs.readObject();
                logger.info("Requested action: " + action);
                mainScreen.setRequestsText(requestAmount++);
                mainScreen.setTextArea("\nRequested action: " + action);
                mainScreen.setTextArea("\nHandled on: " + Thread.currentThread().getName() + "\n\n");
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
                if (action.equals("View Products")) {
                    List<Product> productList = getProductList();
                    for (Product prod : productList) {
                        objOs.writeObject(prod);
                    }
                }
                if (action.equals("Add Invoice")) {
                    invoice = (Invoice) objIs.readObject();
                    addInvoiceData(invoice);
                }
                if (action.equals("View Invoices")) {
                    List<Invoice> invoiceList = getInvoiceList();
                    for (Invoice inv : invoiceList) {
                        objOs.writeObject(inv);
                    }
                }
                if (action.equals("Find Invoice")) {
                    int invoiceNum = (int) objIs.readObject();
                    invoice = getInvoiceData(invoiceNum);
                    objOs.writeObject(invoice);
                }
                if (action.equals("Remove Invoice")) {
                    int invoiceNum = (int) objIs.readObject();
                    removeInvoiceData(invoiceNum);
                }
                if (action.equals("Add Invoice Item")) {
                    List<InvoiceItem> invoiceItemList = (List<InvoiceItem>) objIs.readObject();
                    addInvoiceItemData(invoiceItemList);
                }
                if (action.equals("View Invoice Item")) {
                    int invoiceNum = (int) objIs.readObject();
                    List<InvoiceItem> invoiceItemList = getInvoiceItemList(invoiceNum);
                    for (InvoiceItem itemList : invoiceItemList) {
                        objOs.writeObject(itemList);
                    }
                }
                if (action.equals("Update Inventory")) {
                    List<Inventory> inventoryList = (List<Inventory>) objIs.readObject();
                    updateInventoryData(inventoryList);
                }
                if (action.equals("View Inventory Item")) {
                    InventoryId inventoryId = (InventoryId) objIs.readObject();
                    inventory = getInventoryItem(inventoryId);
                    objOs.writeObject(inventory);
                }
            } catch (EOFException e) {
                logger.error("EOFException: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                logger.error("IOException: " + e.getMessage());
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                logger.error("ClassNotFoundException: " + e.getMessage());
                e.printStackTrace();
            } catch (ClassCastException e) {
                logger.error("ClassCastException: " + e.getMessage());
                e.printStackTrace();
            } finally {
                closeConnections();
            }
        }
    }
}