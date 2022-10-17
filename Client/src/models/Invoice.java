package models;
//============================================================================
//Name        : Date.java
//Author      : Tyrien Gilpin
//Version     : 1
//Copyright   : Your copyright notice
//Description : Invoice Model Class
//============================================================================
import java.io.Serializable;

public class Invoice implements Serializable {
    private int invoiceNumber;
    private Date billingDate;
    private String itemName;
    private int quantity;
    private String employee;
    private String customer;

    public Invoice() {
        invoiceNumber = 0;
        billingDate = new Date();
        itemName = "N/A";
        quantity = 0;
        employee = "N/A";
        customer = "N/A";
    }

    //Primary Constructor
    public Invoice(int invoiceNumber, Date billingDate, String itemName,
                   int quantity, String employee, String customer) {
        this.invoiceNumber = invoiceNumber;
        this.billingDate = billingDate;
        this.itemName = itemName;
        this.quantity = quantity;
        this.employee = employee;
        this.customer = customer;
    }

    //Copy Constructor
    public Invoice(Invoice invoice) {
        this.invoiceNumber = invoice.invoiceNumber;
        this.billingDate = invoice.billingDate;
        this.itemName = invoice.itemName;
        this.quantity = invoice.quantity;
        this.employee = invoice.employee;
        this.customer = invoice.customer;
    }

    //Getters and Setters
    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee.getFirstName() + " " + employee.getLastName();
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer.getFirstName() + " " + customer.getLastName();
    }

    public void displayInvoice() {
        System.out.println("Invoice Number: " + getInvoiceNumber() + "\nDate" + getBillingDate()
                + "Items" + getItemName() + "Quantity: " + getQuantity() + "Employee: "
                + getEmployee() + "Customer: " + getCustomer());
    }
}
