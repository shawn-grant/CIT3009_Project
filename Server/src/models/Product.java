package models;

import java.io.Serializable;

public class Product implements Serializable {
    private String code;
    private String name;
    private String shortDescription;
    private String longDescription;
    private int itemInStock;
    private float unitPrice;

    //Default Constructor
    public Product() {
        this.code = "N/A";
        this.name = "N/A";
        this.shortDescription = "N/A";
        this.longDescription = "N/A";
        this.itemInStock = 0;
        this.unitPrice = 0f;
    }

    //Primary Constructor
    public Product(String code, String name, String shortDescription, String longDescription, int itemInStock,
                   float unitPrice) {
        this.code = code;
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.itemInStock = itemInStock;
        this.unitPrice = unitPrice;
    }

    //Copy Constructor
    public Product(Product Product) {
        this.code = Product.code;
        this.name = Product.name;
        this.shortDescription = Product.shortDescription;
        this.longDescription = Product.longDescription;
        this.itemInStock = Product.itemInStock;
        this.unitPrice = Product.unitPrice;
    }

    //Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public int getItemInStock() {
        return itemInStock;
    }

    public void setItemInStock(int itemInStock) {
        this.itemInStock = itemInStock;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    //Method to display product attributes
    public void Display() {
        System.out.println("ProductCode: " + getCode() + "\nName: " + getName() + "\nShort Description: "
                + getShortDescription() + "\nLong Description: " + getLongDescription() + "\nItem in Stock: "
                + getItemInStock() + "\nUnit Price: " + getUnitPrice());
    }
}
