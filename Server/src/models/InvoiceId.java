package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class InvoiceId implements Serializable {

    @Column(name = "invoice_number")
    private int invoiceNumber;
    @Temporal(TemporalType.DATE)
    @Column(name = "billing_date")
    private Date billingDate;
    @Column(name = "item_name")
    private String itemName;

    public InvoiceId() {
    }

    public InvoiceId(int invoiceNumber, Date billingDate, String itemName) {
        setInvoiceNumber(invoiceNumber);
        setBillingDate(billingDate);
        setItemName(itemName);
    }

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

    @Override
    public String toString() {
        return "InvoiceId{" +
                "invoiceNumber=" + getInvoiceNumber() +
                ", billingDate=" + getBillingDate() +
                ", itemName='" + getItemName() + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
