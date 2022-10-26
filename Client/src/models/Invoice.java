package models;
//============================================================================
//Name        : Date.java
//Author      : Tyrien Gilpin
//Description : Invoice Model Class
//============================================================================

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Malik Heron
 */
@Entity(name = "invoice")
@Table(name = "invoice")
public class Invoice implements Serializable {

    @Id
    private int invoice_number;
    @Column(name = "billing_date")
    @Temporal(TemporalType.DATE)
    private Date billingDate;
    @Column(name = "total_cost")
    private float totalCost;
    @Column(name = "amount_tendered")
    private float amountTendered;
    @Column(name = "employeeId")
    private String employeeId;
    @Column(name = "customerId")
    private String customerId;

    public Invoice() {
        invoice_number = 0;
        billingDate = new Date();
        totalCost = 0f;
        amountTendered = 0f;
        employeeId = "N/A";
        customerId = "N/A";
    }

    //Primary Constructor
    public Invoice(int invoice_number, Date billingDate, float totalCost, float amountTendered, String employeeId, String customerId) {
        setInvoice_number(invoice_number);
        setBillingDate(billingDate);
        setTotalCost(totalCost);
        setAmountTendered(amountTendered);
        setEmployeeId(employeeId);
        setCustomerId(customerId);
    }

    //Copy Constructor
    public Invoice(Invoice invoice) {
        this.invoice_number = invoice.invoice_number;
        this.billingDate = invoice.billingDate;
        this.totalCost = invoice.totalCost;
        this.amountTendered = invoice.amountTendered;
        this.employeeId = invoice.employeeId;
        this.customerId = invoice.customerId;
    }

    //Getters and Setters
    public int getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(int invoice_number) {
        this.invoice_number = invoice_number;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public float getAmountTendered() {
        return amountTendered;
    }

    public void setAmountTendered(float amountTendered) {
        this.amountTendered = amountTendered;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoice_number=" + invoice_number +
                ", billingDate=" + billingDate +
                ", totalCost=" + totalCost +
                ", amountTendered=" + amountTendered +
                ", employeeId='" + employeeId + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
