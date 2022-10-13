package database.models;

public class Product {

	private String productCode;
	private String name;
	private String shortDescription;
	private String longDescription;
	private int itemsInStock;
	private float unitPrice;
	
	
	//Default Constructor
	public Product() {
		this.productCode = "N/A";
		this.name = "N/A";
		this.shortDescription = "N/A";
		this.longDescription = "N/A";
		this.itemsInStock = -1;
		this.unitPrice = (float) 0000.00;
	}
	
	
	//Primary Constructor
	public Product(String productCode, String name, String shortDescription, String longDescription, 
			int itemsInStock,float unitPrice) {
		this.productCode = productCode;
		this.name = name;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.itemsInStock = itemsInStock;
		this.unitPrice = unitPrice;
	}
	
	
	//Copy Constructor
	public Product(Product product) {
		this.productCode = product.productCode;
		this.name = product.name;
		this.shortDescription = product.shortDescription;
		this.longDescription = product.longDescription;
		this.itemsInStock = product.itemsInStock;
		this.unitPrice = product.unitPrice;
	}


	public String getProductCode() {
		return productCode;
	}


	public void setProductCode(String productCode) {
		this.productCode = productCode;
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


	public int getItemsInStock() {
		return itemsInStock;
	}


	public void setItemsInStock(int itemsInStock) {
		this.itemsInStock = itemsInStock;
	}


	public float getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}


	@Override
	public String toString() {
		return "Product: " + getProductCode() + "\nName: " + getName() + "\nShort Description: "
				+ getShortDescription() + "\nLong Description: " + getLongDescription() + "\nItems in Stock:"
				+ getItemsInStock() + "\nUnit Price: " + getUnitPrice();
	}
	
	
}
