package factories;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DBConnectorFactory {

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
    private static void createJWRDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
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

    private static void createEmployeeTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE employee(ID varchar(10) NOT NULL, firstName varchar(25)," +
                    "lastName varchar(25), dob date, address varchar(80), telephone varchar(25), " +
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

    private static void createCustomerTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE customer(ID varchar(10) NOT NULL, firstName varchar(25)," +
                    "lastName varchar(25), dob date, address varchar(80), telephone varchar(25)," +
                    " email varchar(25), membershipDate date, membershipExpiryDate date, " +
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

    private static void createProductTable() {
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

    private static void createDepartmentTable() {
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

    private static void createInvoiceTable() {
        try (Statement stmt = dbConn.createStatement()) {
            String query = "CREATE TABLE invoice(invoice_number varchar(10) NOT NULL, billing_date date," +
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
}
