package models;
//============================================================================
//Name        : Date.java
//Author      : Tyrien Gilpin
//Version     : 1
//Copyright   : Your copyright notice
//Description : Invoice Model Class
//============================================================================

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "invoice")
@Table(name = "invoice")
public class Invoice implements Serializable {

    @Id
    @Column(name = "invoice_number")
    private int invoiceNumber;
    @Temporal(TemporalType.DATE)
    @Column(name = "billing_date")
    private Date billingDate;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "total_cost")
    private float totalCost;
    @Column(name = "employeeId")
    private String employee;
    @Column(name = "customerId")
    private String customer;

    public Invoice() {
        invoiceNumber = 0;
        billingDate = new Date();
        itemName = "N/A";
        quantity = 0;
        totalCost = (float) 0.00;
        employee = "N/A";
        customer = "N/A";
    }

    //Primary Constructor
    public Invoice(int invoiceNumber, Date billingDate, String itemName,
                   int quantity, float totalCost, String employee, String customer) {
        setInvoiceNumber(invoiceNumber);
        setBillingDate(billingDate);
        setItemName(itemName);
        setQuantity(quantity);
        setTotalCost(totalCost);
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

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee.getFirstName() + " (" + employee.getId() + ")";
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer.getFirstName() + " (" + customer.getId() + ")";
    }

    public void displayInvoice() {
        System.out.println("Invoice Number: " + getInvoiceNumber() + "\nDate" + getBillingDate()
                + "Items" + getItemName() + "Quantity: " + getQuantity() + "Total Billing: " + getTotalCost() + "Employee: "
                + getEmployee() + "Customer: " + getCustomer());
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceNumber=" + invoiceNumber +
                ", billingDate=" + billingDate +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", totalCost=" + totalCost +
                ", employee='" + employee + '\'' +
                ", customer='" + customer + '\'' +
                '}';
    }
}
