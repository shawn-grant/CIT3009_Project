package models;
//============================================================================
//Name        : Inventory.java
//Author      : Tyrien Gilpin
//Version     : 1
//Copyright   : Your copyright notice
//Description : Inventory Model Class
//============================================================================

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "inventory")
@Table(name = "inventory")
public class Inventory implements Serializable {

    @EmbeddedId
    private InventoryId id;
    @Column(name = "stock")
    private int stock;
    @Column(name = "unit_price")
    private Float unitPrice;
    @Column(name = "amount_purchased")
    private int amountPurchased;

    //Default Constructor
    public Inventory() {
        id = new InventoryId();
        stock = 0;
        unitPrice = 0f;
        amountPurchased = 0;
    }

    //Primary Constructor
    public Inventory(InventoryId id, Date date_modified, int stock, Float unit_price, int amount_purchased) {
        setId(id);
        setStock(stock);
        setUnitPrice(unit_price);
        setAmountPurchased(amount_purchased);
    }

    //Copy Constructor
    public Inventory(Inventory inventory) {
        this.id = inventory.id;
        this.stock = inventory.stock;
        this.unitPrice = inventory.unitPrice;
        this.amountPurchased = inventory.amountPurchased;
    }

    //Getters and Setters
    public InventoryId getId() {
        return id;
    }

    public void setId(InventoryId id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getAmountPurchased() {
        return amountPurchased;
    }

    public void setAmountPurchased(int amountPurchased) {
        this.amountPurchased = amountPurchased;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "code='" + id.getCode() + '\'' +
                ", date_modified=" + id.getDateModified() +
                ", stock=" + getStock() +
                ", unit_price=" + getUnitPrice() +
                ", amount_purchased=" + getAmountPurchased() +
                '}';
    }
}

