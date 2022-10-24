package view.dialogs.checkout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import client.Client;
import models.Invoice;
import models.Product;
import utils.CostValidator;
import utils.GenerateID;
import utils.IntegerValidator;
import view.components.RoundedBorder;

public class checkoutDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JLabel totalItemsLbl, totalCostlbl, staffIDLabel, customerIDLabel, tenderedLabel;
    private JTextField totalItemsTxtValue, totalCostTxtValue, staffTxtValue, customerTxtValue;
    private JTextField tenderedTxtValue;
    private JButton cashoutButton, cancelButton;
	private DefaultTableModel model;
	private List<Product> productList = new ArrayList<>();
	private String products;
    
    public checkoutDialog(DefaultTableModel model, List<Product> productList, String customer, String staff) {
    	this.model = model;
    	this.productList = productList;
        initializeComponents(customer, staff);
        addPanelsToWindow();
        registerListeners();
        setWindowProperties();
    } 

    private void initializeComponents(String customer, String staff) {
    	staffIDLabel = new JLabel("Staff ID");
        staffIDLabel.setFont(new Font("arial", Font.BOLD, 14));
        staffIDLabel.setPreferredSize(new Dimension(130, 20));

        staffTxtValue = new JTextField();
        staffTxtValue.setBorder(new RoundedBorder(8));
        staffTxtValue.setPreferredSize(new Dimension(250, 30));
        staffTxtValue.setText(staff);
        staffTxtValue.setEditable(false);
        
        customerIDLabel = new JLabel("Customer ID");
        customerIDLabel.setFont(new Font("arial", Font.BOLD, 14));
        customerIDLabel.setPreferredSize(new Dimension(130, 20));

        customerTxtValue = new JTextField("N/A");
        customerTxtValue.setBorder(new RoundedBorder(8));
        customerTxtValue.setPreferredSize(new Dimension(250, 30));
        customerTxtValue.setText(customer);
        customerTxtValue.setEditable(false);
        
        totalItemsLbl = new JLabel("Total Items");
        totalItemsLbl.setFont(new Font("arial", Font.BOLD, 14));
        totalItemsLbl.setPreferredSize(new Dimension(130, 20));

        totalItemsTxtValue = new JTextField();
        totalItemsTxtValue.setBorder(new RoundedBorder(8));
        totalItemsTxtValue.setPreferredSize(new Dimension(250, 30));
        totalItemsTxtValue.setEditable(false);
        totalItemsTxtValue.setText(Integer.toString(getTotalQuantity()));
        
        totalCostlbl = new JLabel("Total Cost");
        totalCostlbl.setFont(new Font("arial", Font.BOLD, 14));
        totalCostlbl.setPreferredSize(new Dimension(130, 20));

        totalCostTxtValue = new JTextField();
        totalCostTxtValue.setBorder(new RoundedBorder(8));
        totalCostTxtValue.setPreferredSize(new Dimension(250, 30));
        totalCostTxtValue.setEditable(false);
        totalCostTxtValue.setText(Float.toString(getTotalCost()));
        
        tenderedLabel = new JLabel("Tendered");
        tenderedLabel.setFont(new Font("arial", Font.BOLD, 14));
        tenderedLabel.setPreferredSize(new Dimension(130, 20));
        
        tenderedTxtValue = new JTextField();
        tenderedTxtValue.setBorder(new RoundedBorder(8));
        tenderedTxtValue.setPreferredSize(new Dimension(250, 30));
        
        cashoutButton = new JButton("Cash Out");
        cashoutButton.setPreferredSize(new Dimension(150, 30));
        cashoutButton.setForeground(Color.BLUE);
        cashoutButton.setFont(new Font("arial", Font.BOLD, 12));
        
        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setForeground(Color.RED);
    }

    private void addPanelsToWindow() {//Adding Labels and textfields to window
    	add(staffIDLabel);
    	add(staffTxtValue);
    	add(customerIDLabel);
    	add(customerTxtValue);
    	add(totalItemsLbl);
    	add(totalItemsTxtValue);
    	add(totalCostlbl);
    	add(totalCostTxtValue);
    	add(tenderedLabel);
    	add(tenderedTxtValue);
    	add(cashoutButton);
    	add(cancelButton);
    }

    private void setWindowProperties() {//Setting properties of dialog window
    	setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setTitle("Cashout Items");
        setSize(420, 280);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setVisible(true);   
    }
    
    private boolean validateFields() {//method to check if ant fields is empty
        if(staffTxtValue.getText().isEmpty() || customerTxtValue.getText().isEmpty()
        		|| tenderedTxtValue.getText().isEmpty()) {
        	JOptionPane.showMessageDialog(
                    this,
                    "One or more fields empty",
                    "Warning", JOptionPane.WARNING_MESSAGE
            );
        	return false;
        }else { 
        	return CostValidator.isValid(tenderedTxtValue.getText(), this);
        }
        
    }
    
    private int generateInvoiceNum() {//randomizing the generation of invoice numbers
    	int value = 0;
    	value = (int) ((Math.random() * (70000 - 100)) + 100);
    	return value;
    }
   
    public float calculateChange() {//calculating how much change customer will receive
    	float tendered = Float.parseFloat(tenderedTxtValue.getText());
    	float cost = getTotalCost();
    	return tendered - cost;
    }
    
    public void registerListeners() {
        cashoutButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }
    
    public int getTotalQuantity() {//Retrieving total number of items being purchased by customer
    	int sum = 0;
    	for(int i = 0; i< model.getRowCount(); i++) {
    		sum = sum + Integer.parseInt(model.getValueAt(i, 2).toString());
    	}
    	return sum;
    }
    
    public float getTotalCost(){//calculating the totall billing fro the invoice using data in table
    	float sum = 0;
    	for(int i = 0; i< model.getRowCount(); i++) {
    		sum = sum + Float.parseFloat(model.getValueAt(i, 4).toString());
    	}
    	return sum;
    }
    
    public void updateInventory() {//Updates the inventory items after customer is cashed out
    	 for (Product product : productList) {//For each product being checked out
             Client client = new Client();
             client.sendAction("Update Product"); //update all fields to update the product quantity in inventory
             Product prod = new Product(
                     product.getCode(),
                     product.getName(),
                     product.getShortDescription(),
                     product.getLongDescription(),
                     product.getItemInStock(),
                     product.getUnitPrice()
             );
             products = product.getCode() + "\n";//Add product code to string
             client.sendProduct(prod);
             client.receiveResponse();
             client.closeConnections();
             System.out.println(prod);
         } 	
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cashoutButton) {
        	if(validateFields()){
	        	float change = calculateChange();
	            if(change < 0.0) {
	            	JOptionPane.showMessageDialog(null,  "Insufficient amount tendered", 
	                		"Customer Change", JOptionPane.INFORMATION_MESSAGE);
	            }else {
	            	updateInventory();
	            	
	            	String date = String.valueOf(java.time.LocalDate.now());
		        	LocalDate currentDate = LocalDate.parse(date);
		        	int invoiceNumber = generateInvoiceNum();
		        	Client client = new Client();
		        	client.sendAction("Add Invoice");
		        			Invoice invoice = new Invoice(
			        			invoiceNumber,
			            		new Date(currentDate.getDayOfMonth(), 
			            				 currentDate.getMonthValue(), 
			            				 currentDate.getYear()),
			            		products,					
			            		Integer.parseInt(totalItemsTxtValue.getText()),
			            		Float.parseFloat(totalCostTxtValue.getText().trim()),	//NTS: update invoice model to add this field
			            		staffTxtValue.getText().trim(),
			                    customerTxtValue.getText().trim()
			            );
			        	client.sendInvoice(invoice);
			            client.receiveResponse();
			            client.closeConnections();
			            
			            JOptionPane.showMessageDialog(null,  "Customer should receive $"+ change +" in change.",
                		"Customer Change", JOptionPane.INFORMATION_MESSAGE); 
	            	dispose();
	            }
        	}else {
        		JOptionPane.showMessageDialog(null,  "One or more fields empty", 
                		"Missing information", JOptionPane.WARNING_MESSAGE); 
        	}
        	
        }
        if (e.getSource() == cancelButton) {
        	dispose();
        }
        
    }

}