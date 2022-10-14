package models;

import models.Employee;
import models.Customer;

public class Invoice {
	private int invoiceNumber;
	private Date billingDate;
	private String itemName;
	private int quantity;
	private String employee;
	private String customer;
	
	public Invoice() {
		this.invoiceNumber = -1;
		this.billingDate = new Date();
		this.itemName = itemName;
		this.quantity = quantity;
		this.employee = employee;
		this.customer = customer;
	}
	
	//Primary Constructor
	public Invoice(int invoiceNumber, int day, int month, int year, String itemName, 
			int quantity, String employee,String customer) {
		this.invoiceNumber = invoiceNumber;
		this.billingDate = new Date(day, month, year);
		this.itemName = itemName;
		this.quantity = quantity;
		this.employee = employee;
		this.customer = customer;
	}
	
	//Copy Consturctor
	public Invoice(Invoice invoice) {
		this.invoiceNumber = invoice.invoiceNumber;
		this.billingDate = invoice.billingDate;
		this.itemName = invoice.itemName;
		this.quantity = invoice.quantity;
		this.employee = invoice.employee;
		this.customer = invoice.customer;
	}

	
	
	//Getters and Setters
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee.getFirstName() + " " +employee.getLastName();
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer.getFirstName()+" "+customer.getLastName();
	}

	
	public void displayInvoice() {
		System.out.println("Invoice Number: " + getInvoiceNumber() + "\nDate" + getBillingDate()
				+ "Items" + getItemName() + "Quantity: " + getQuantity() + "Employee: "
				+ getEmployee() + "Customer: " + getCustomer());
	}

	
	
	
	
	
	
	
}
