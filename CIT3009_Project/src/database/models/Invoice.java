package database.models;

public class Invoice {

	private int invoiceNumber;
	private Date billingDate;
	private Product item;
	private String staff;
	private String customer;
	
	//Default Constructor
	public Invoice() {
		this.invoiceNumber = 0000000;
		this.billingDate = new Date();
		this.item = new Product();
		this.staff = "N/A";
		this.customer = "N/A";
	}
	
	//Primary Constructor
	public Invoice(int invoiceNumber, Date billingDate, Product item, String staff, String customer) {
		this.invoiceNumber = invoiceNumber;
		this.billingDate = billingDate;
		this.item = item;
		this.staff = staff;
		this.customer = customer;
	}
	
	//Copy Constructor
	public Invoice(Invoice invoice) {
		this.invoiceNumber = invoice.invoiceNumber;
		this.billingDate = invoice.billingDate;
		this.item = invoice.item;
		this.staff = invoice.staff;
		this.customer = invoice.customer;
	}

	
	//Getters and setters
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

	public Product getItem() {
		return item;
	}

	public void setItem(Product item) {
		this.item = item;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	

	
	
	
}
