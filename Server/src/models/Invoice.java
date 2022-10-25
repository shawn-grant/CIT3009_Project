package models;
//============================================================================
//Name        : Date.java
//Author      : Tyrien Gilpin
//Description : Invoice Model Class
//============================================================================

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "invoice")
@Table(name = "invoice")
public class Invoice implements Serializable {

    @EmbeddedId
    private InvoiceId id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "unit_price")
    private float unitPrice;
    @Column(name = "total_cost")
    private float totalCost;
    @Column(name = "amount_tendered")
    private float amountTendered;
    @Column(name = "employeeId")
    private String employeeId;
    @Column(name = "customerId")
    private String customerId;

    public Invoice() {
        id = new InvoiceId();
        quantity = 0;
        unitPrice = 0f;
        totalCost = 0f;
        amountTendered = 0f;
        employeeId = "N/A";
        customerId = "N/A";
    }

    //Primary Constructor
    public Invoice(InvoiceId id, int quantity, float unitPrice, float totalCost,
                   float amountTendered, String employeeId, String customerId) {
        setId(id);
        setQuantity(quantity);
        setUnitPrice(unitPrice);
        setTotalCost(totalCost);
        setAmountTendered(amountTendered);
        setEmployeeId(employeeId);
        setCustomerId(customerId);
    }

    //Copy Constructor
    public Invoice(Invoice invoice) {
        this.id = invoice.id;
        this.quantity = invoice.quantity;
        this.totalCost = invoice.totalCost;
        this.amountTendered = invoice.amountTendered;
        this.unitPrice = invoice.unitPrice;
        this.employeeId = invoice.employeeId;
        this.customerId = invoice.customerId;
    }

    //Getters and Setters
    public InvoiceId getId() {
        return id;
    }

    public void setId(InvoiceId id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
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
                "id=" + getId() +
                ", quantity=" + getQuantity() +
                ", unitPrice=" + getUnitPrice() +
                ", totalCost=" + getTotalCost() +
                ", amountTendered=" + getAmountTendered() +
                ", employeeId='" + getEmployeeId() + '\'' +
                ", customerId='" + getCustomerId() + '\'' +
                '}';
    }
}
