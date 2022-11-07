package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Model class for Product
 * @author Tyrien Gilpin
 * @author Malik Heron
 */
@Entity(name = "product")
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @Column(name = "product_code")
    private String code;
    @Column(name = "productName")
    private String name;
    @Column(name = "shortDescription")
    private String shortDescription;
    @Column(name = "longDescription")
    private String longDescription;
    @Column(name = "itemInStock")
    private int itemInStock;
    @Column(name = "unitPrice")
    private float unitPrice;

    //Default Constructor
    public Product() {
        code = "N/A";
        name = "N/A";
        shortDescription = "N/A";
        longDescription = "N/A";
        itemInStock = 0;
        unitPrice = 0f;
    }

    //Primary Constructor
    public Product(String code, String name, String shortDescription, String longDescription, int itemInStock,
                   float unitPrice) {
        setCode(code);
        setName(name);
        setShortDescription(shortDescription);
        setLongDescription(longDescription);
        setItemInStock(itemInStock);
        setUnitPrice(unitPrice);
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

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", itemInStock=" + itemInStock +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
