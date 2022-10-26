package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Malik Heron
 */
@Embeddable
public class InvoiceItemId implements Serializable {

    @Column(name = "invoice_number")
    private int invoiceNumber;
    @Column(name = "item_name")
    private String itemName;

    public InvoiceItemId() {
    }

    public InvoiceItemId(int invoiceNumber, String itemName) {
        setInvoiceNumber(invoiceNumber);
        setItemName(itemName);
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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
