package models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Malik Heron
 */
@Entity(name = "invoiceItem")
@Table(name = "invoiceItem")
public class InvoiceItem implements Serializable {

    @EmbeddedId
    private InvoiceItemId id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "unit_price")
    private float unitPrice;

    public InvoiceItem() {
        id = new InvoiceItemId();
        quantity = 0;
        unitPrice = 0f;
    }

    //Primary Constructor
    public InvoiceItem(InvoiceItemId id, int quantity, float unitPrice) {
        setId(id);
        setQuantity(quantity);
        setUnitPrice(unitPrice);
    }

    //Copy Constructor
    public InvoiceItem(InvoiceItem invoiceItem) {
        this.id = invoiceItem.id;
        this.quantity = invoiceItem.quantity;
        this.unitPrice = invoiceItem.unitPrice;
    }

    //Getters and Setters
    public InvoiceItemId getId() {
        return id;
    }

    public void setId(InvoiceItemId id) {
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

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "id=" + getId() +
                ", quantity=" + getQuantity() +
                ", unitPrice=" + getUnitPrice() +
                '}';
    }
}
