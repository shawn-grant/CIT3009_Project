package factories;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Malik Heron
 */
public class DBConnectorFactory {

    private static final Logger logger = LogManager.getLogger(DBConnectorFactory.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASS = "";
    private static Connection dbConn;

    // method for getting database connection
    public static void getDatabaseConnection() {
        try {
            if (dbConn == null) {
                dbConn = DriverManager.getConnection(URL + "jwr", USER, PASS);
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            logger.info("DB Connection Established @ " + localDateTime.format(dateTimeFormatter));
            createEmployeeTable();
            createCustomerTable();
            createProductTable();
            createDepartmentTable();
            createInvoiceTable();
            createPurchaseTable();
            createInventoryTable();
        } catch (SQLException e) {
            logger.warn("SQLException: " + e.getMessage());
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
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Create queries
     */
    private static void createJWRDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()
        ) {
            String query = "CREATE DATABASE IF NOT EXISTS jwr";
            if (stmt.executeUpdate(query) == 0) {
                logger.info("JWR Database created");
            }
        } catch (SQLException e) {
            logger.warn("SQLException: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createEmployeeTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE employee(ID varchar(10) NOT NULL, firstName varchar(25)," +
                    "lastName varchar(25), dob date, address varchar(80), telephone varchar(25), " +
                    "email varchar(25), dept_code varchar(40), employeeType varchar(25), PRIMARY KEY(ID))";

            if (stmt.executeUpdate(query) == 0) {
                logger.info("Employee table created.");
            }
        } catch (SQLException e) {
            logger.warn("SQLException: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createCustomerTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE customer(ID varchar(10) NOT NULL, firstName varchar(25)," +
                    "lastName varchar(25), dob date, address varchar(80), telephone varchar(25)," +
                    " email varchar(25), membershipDate date, membershipExpiryDate date, " +
                    "PRIMARY KEY(ID))";

            if (stmt.executeUpdate(query) == 0) {
                logger.info("Customer table created.");
            }
        } catch (SQLException e) {
            logger.warn("SQLException: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createProductTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE product(product_code varchar(10) NOT NULL, productName varchar(25)," +
                    "shortDescription varchar(25), longDescription varchar(70), itemInStock int, unitPrice float, " +
                    "PRIMARY KEY(product_code))";

            if (stmt.executeUpdate(query) == 0) {
                logger.info("Product table created.");
            }
        } catch (SQLException e) {
            logger.warn("SQLException: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createDepartmentTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE department(dept_code varchar(10) NOT NULL, departmentName varchar(25)," +
                    "PRIMARY KEY(dept_code))";

            if (stmt.executeUpdate(query) == 0) {
                logger.info("Department table created.");
                String query1 = "INSERT INTO department(dept_code, departmentName) VALUES ('MAN', 'Management')";
                String query2 = "INSERT INTO department(dept_code, departmentName) VALUES ('INV', 'Inventory')";
                String query3 = "INSERT INTO department(dept_code, departmentName) VALUES ('ACS', 'Accounting & sales')";

                stmt.executeUpdate(query1);
                stmt.executeUpdate(query2);
                stmt.executeUpdate(query3);
            }
        } catch (SQLException e) {
            logger.warn("SQLException: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createInvoiceTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE invoice(invoice_number varchar(10) NOT NULL, billing_date date NOT NULL, " +
                    "total_cost float, amount_tendered float, employeeID varchar(10), customerID varchar(10), " +
                    "PRIMARY KEY(invoice_number))";

            if (stmt.executeUpdate(query) == 0) {
                logger.info("Invoice table created.");
            }
        } catch (SQLException e) {
            logger.warn("SQLException: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createPurchaseTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE purchase(invoice_number varchar(10) NOT NULL, item_name varchar(40) NOT NULL, " +
                    "quantity int, unit_price float, PRIMARY KEY(invoice_number, item_name))";

            if (stmt.executeUpdate(query) == 0) {
                logger.info("Purchase table created.");
            }
        } catch (SQLException e) {
            logger.warn("SQLException: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createInventoryTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE inventory(product_code varchar(10) NOT NULL, date_modified date NOT NULL," +
                    "stock int NOT NULL, unit_price float, amount_purchased int, PRIMARY KEY(product_code, date_modified))";

            if (stmt.executeUpdate(query) == 0) {
                logger.info("Inventory table created.");
            }
        } catch (SQLException e) {
            logger.warn("SQLException: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
