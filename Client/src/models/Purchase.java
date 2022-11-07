package models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Model class for Purchase
 * @author Malik Heron
 */
@Entity(name = "purchase")
@Table(name = "purchase")
public class Purchase implements Serializable {

    @EmbeddedId
    private PurchaseId id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "unit_price")
    private float unitPrice;

    public Purchase() {
        id = new PurchaseId();
        quantity = 0;
        unitPrice = 0f;
    }

    //Primary Constructor
    public Purchase(PurchaseId id, int quantity, float unitPrice) {
        setId(id);
        setQuantity(quantity);
        setUnitPrice(unitPrice);
    }

    //Copy Constructor
    public Purchase(Purchase purchase) {
        this.id = purchase.id;
        this.quantity = purchase.quantity;
        this.unitPrice = purchase.unitPrice;
    }

    //Getters and Setters
    public PurchaseId getId() {
        return id;
    }

    public void setId(PurchaseId id) {
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
