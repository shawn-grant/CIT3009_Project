package view;
//============================================================================
//Name        : CheckoutScreen.java
//Author      : Tyrien Gilpin
//Version     : 1
//Description : GUI Screen class for cashing checkout 
//============================================================================
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import client.Client;
import models.Product;
import view.dialogs.checkout.*;
import view.dialogs.customer.CustomerInsertDialog;
 
public class CheckoutScreen extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton addButton, deleteButton, clearButton, checkoutButton, searchButton, addCustomerButton; 
    private JLabel titleLabel, productCodeLbl, itemNameLabel, quantityLabel, unitPrice;
    private JLabel customer, staff;
    private JTextField codeTxtValue, quantityTxtValue, itemNameTxtValue, unitPriceTxtValue;
    private JTextField customerTxtValue, staffTxtValue;
    private JPanel centerPanel, mainContent; 
	private final String[] TableColumns = {"Product Code", "Product Name", "Quantity", "Unit Price", "Cost"};
	private JTable table;
	private DefaultTableModel model;
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public CheckoutScreen() {
		setBackground(new Color(27, 73, 142));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setSize(800, 600);
	        
        initializeComponents();
        addComponentsToPanels();
        setContentView();
        addPanelsToWindow();
        registerListeners();
	   }	
	
	private void initializeComponents() {
		//Setting titleLabel properties
        titleLabel = new JLabel("Checkout");
        titleLabel.setFont(new Font("arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        titleLabel.setForeground(Color.WHITE);
       
        productCodeLbl = new JLabel("Product Code");
       // productCodeLbl.setFont(new Font("arial",Font.CENTER_BASELINE,20));
        itemNameLabel = new JLabel("Item");
        quantityLabel = new JLabel("Quantity");
        unitPrice = new JLabel("Unit Price");
        customer = new JLabel("Customer ID");
        staff = new JLabel("Staff ID");
       
        //Initializing Text areas
        codeTxtValue = new JTextField(20);
        quantityTxtValue = new JTextField(20);
        itemNameTxtValue = new JTextField(20);
        unitPriceTxtValue = new JTextField(20);
        itemNameTxtValue.setEditable(false);
        unitPriceTxtValue.setEditable(false);
       customerTxtValue = new JTextField(20);
        staffTxtValue = new JTextField(20);
       
        //initializing buttons
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        checkoutButton = new JButton("Checkout");
        checkoutButton.setEnabled(false);
        searchButton = new JButton("Search");
        addCustomerButton = new JButton("Add customer");
        
        //Setting button sizes
        addButton.setPreferredSize(new Dimension(100, 40));
        deleteButton.setPreferredSize(new Dimension(100, 40));
        clearButton.setPreferredSize(new Dimension(100, 40));
        checkoutButton.setPreferredSize(new Dimension(100, 40));
        searchButton.setPreferredSize(new Dimension(90,20));
        addCustomerButton.setPreferredSize(new Dimension(130,20));
        
        
        //Setting button font & Colour
        addButton.setFont(new Font("arial", Font.PLAIN, 15));
        addButton.setBackground(new Color(224, 224, 224));
        deleteButton.setFont(new Font("arial", Font.PLAIN, 15));
        deleteButton.setBackground(new Color(224, 224, 224));
        clearButton.setFont(new Font("arial", Font.PLAIN, 15));
        clearButton.setBackground(new Color(224, 224, 224));
        checkoutButton.setFont(new Font("arial", Font.PLAIN, 15));
        checkoutButton.setBackground(new Color(224, 224, 224));
        searchButton.setFont(new Font("arial", Font.PLAIN, 15));
        searchButton.setBackground(new Color(224, 224, 224));
        addCustomerButton.setFont(new Font("arial", Font.PLAIN, 15));
        addCustomerButton.setBackground(new Color(224, 224, 224));
        
        //Setting Panel Properties
        mainContent = new JPanel(new GridLayout(0, 1, 0, 70));
        centerPanel = new JPanel(new GridBagLayout());	//NTS: SetBounds??
        centerPanel.setBackground(new Color(255, 255, 255));
        mainContent.setBackground(new Color(255, 255, 255));
      
        //Setting Table properties
		model = new DefaultTableModel(TableColumns, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //Set to not editable
        table.setAutoCreateRowSorter(true); //Enable sorting by columns
        table.getTableHeader().setOpaque(false);//Removing background of table heading
        table.getTableHeader().setBackground(new Color(224, 224, 224));//Setting new background of table headings
        table.setBackground(Color.white);
        table.setForeground(Color.black);
	 }
	
		/*
	 private void addComponentsToPanels() {
		//Adding Labels and fields to label panel
		gbc.insets = new Insets(25, 10, 20, 10);//Sets Padding around values
		
		////////////////First Row//////////////////
		gbc.weightx = 1;
		gbc.weighty= 1;
		gbc.gridx = 0;//Setting position on x axis
		gbc.gridy = 0;//Setting position on Y axis
		gbc.gridwidth = 1; //Setting number of columns occupied by the grid object
		gbc.fill = GridBagConstraints.NONE;//Filling grid object into space specified grid width
		//gbc.anchor = GridBagConstraints.LINE_END;
		centerPanel.add(productCodeLbl, gbc);
		  
        gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//gbc.anchor = GridBagConstraints.LINE_START;
		centerPanel.add(codeTxtValue, gbc);
		
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//gbc.anchor = GridBagConstraints.CENTER;
		centerPanel.add(searchButton, gbc);
		
		gbc.gridx = 7;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		centerPanel.add(quantityLabel, gbc);
		
		gbc.gridx = 8;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(quantityTxtValue, gbc);
		
		/////////////////////////Second Row//////////////////////////
		gbc.weightx = 1;
		gbc.weighty= 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(itemNameLabel, gbc);
		 
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(itemNameTxtValue, gbc);
		
		gbc.gridx = 7;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		centerPanel.add(unitPrice, gbc);
		
		gbc.gridx = 8;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add( unitPriceTxtValue, gbc);
	
		////////////////////3rd Row////////////////////////
		gbc.weightx = 1;
		gbc.weighty= 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(addButton, gbc);
		
		gbc.gridx = 5;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(deleteButton, gbc);
	    
	    gbc.gridx = 7;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(clearButton, gbc);
       
		gbc.gridx = 8;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(checkoutButton, gbc);  
	 }
	*/
	
	private void addComponentsToPanels() {
		//Adding Labels and fields to label panel
		gbc.insets = new Insets(25, 10, 20, 10);//Sets Padding around values
		
		gbc.gridx = 0;//Setting position on x axis
		gbc.gridy = 0;//Setting position on Y axis
		centerPanel.add(customer, gbc);
		  
        gbc.gridx = 1;
		gbc.gridy = 0;
		centerPanel.add(customerTxtValue, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		centerPanel.add(addCustomerButton, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		centerPanel.add(staff, gbc);
		
		gbc.gridx = 4;
		gbc.gridy = 0;
		centerPanel.add(staffTxtValue, gbc);
		
		////////////////First Row//////////////////
		gbc.gridx = 0;//Setting position on x axis
		gbc.gridy = 1;//Setting position on Y axis
		centerPanel.add(productCodeLbl, gbc);
		  
        gbc.gridx = 1;
		gbc.gridy = 1;
		centerPanel.add(codeTxtValue, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		centerPanel.add(searchButton, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;
		centerPanel.add(quantityLabel, gbc);
		
		gbc.gridx = 4;
		gbc.gridy = 1;
		centerPanel.add(quantityTxtValue, gbc);
		
		/////////////////////////Second Row//////////////////////////
		gbc.gridx = 0;
		gbc.gridy= 2;
		centerPanel.add(itemNameLabel, gbc);
		 
		gbc.gridx = 1;
		gbc.gridy = 2;
		centerPanel.add(itemNameTxtValue, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 2;
		centerPanel.add(unitPrice, gbc);
		
		gbc.gridx = 4;
		gbc.gridy = 2;
		centerPanel.add( unitPriceTxtValue, gbc);
	
		////////////////////3rd Row////////////////////////
		gbc.gridx = 0;
		gbc.gridy = 3;
		centerPanel.add(addButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		centerPanel.add(deleteButton, gbc);
	    
	    gbc.gridx = 3;
	    gbc.gridy = 3;
		centerPanel.add(clearButton, gbc);
       
		gbc.gridx = 4;
		gbc.gridy = 3;
		centerPanel.add(checkoutButton, gbc);  
	 }
	 
	 
	 
	 private void addPanelsToWindow() {
	        add(Box.createRigidArea(new Dimension(0, 20)));// vertical spacing
	        add(titleLabel);
	        add(Box.createRigidArea(new Dimension(0, 20)));// vertical spacing
	        add(centerPanel);
	        add(mainContent);
	    }
	 	 
	 public void setMainContent(Component content) {
	        mainContent.add(content);
	    }
	 
	 // set main content view
	 private void setContentView() {
	    setMainContent(new JScrollPane(table));
	 }
		
/*****************************Defining Button Actions*****************************/
		//Method to check if all fields are empty
		 private boolean validateFields() {
		        return !(codeTxtValue.getText().isEmpty() || itemNameTxtValue.getText().isEmpty()
		        		|| quantityTxtValue.getText().isEmpty() || unitPriceTxtValue.getText().isEmpty());
		    }
		 		
		 private void setFields(Product product) {
			 if(codeTxtValue.getText().trim().equalsIgnoreCase(product.getCode())){	
				if(quantityTxtValue.getText().equals("") || quantityTxtValue.getText().equals(" ")) {//NTS: Doesn't work with null
				 quantityTxtValue.setText("1");
				}
	            itemNameTxtValue.setText(product.getName());
	            unitPriceTxtValue.setText(String.valueOf(product.getUnitPrice()));
		 
			 }else {
				 quantityTxtValue.setText(null);
		         itemNameTxtValue.setText(null);
		         unitPriceTxtValue.setText(null);
			 }
		 }
		 		 
		// search for product entered product information
	    private void searchInventory() {///NTS: Check
	    	Client client = new Client();
            if(!(codeTxtValue.getText().isEmpty())) {
            	client.sendAction("Find Product");
            	client.sendProductCode(codeTxtValue.getText().trim());
	            Product product = client.receiveFindProductResponse();
	            setFields(product);
	            client.closeConnections();
            }    	
	     }
	        
	    // adding product information to table
	    private void addItem() {
	    	
	    	if(validateFields() == true){
	    		try{
					int quantity = Integer.parseInt(quantityTxtValue.getText().trim());//getting value from quantity text field
					String cost = String.valueOf(Math.floor(quantity) * Double.parseDouble(unitPriceTxtValue.getText().trim())); //Calculating totals cost based on quantity
					
					List<String> data = new ArrayList<String>();//Storing entire row in list object
					data.add(codeTxtValue.getText().trim());
					data.add(itemNameTxtValue.getText().trim());
					data.add(quantityTxtValue.getText().trim());
					data.add(unitPriceTxtValue.getText().trim());
					data.add(cost);
					model.addRow(data.toArray());
					table.setModel(model);
					
					//Emptying text fields after adding values to table
					codeTxtValue.setText(null);
			    	quantityTxtValue.setText(null);
			        itemNameTxtValue.setText(null);
			        unitPriceTxtValue.setText(null);
			        checkoutButton.setEnabled(true);
	    		}catch (NumberFormatException e) {//If exceptions thrown, change value of quantity to 1
					JOptionPane.showMessageDialog(null, "Invalid Quantity value", "Invalid input",
							JOptionPane.ERROR_MESSAGE);		
				}
	    	}
	    }
	    
	    //This method removes a selected item from a table
	    private void removeItem() {
            if(table.getSelectedRow() != -1) { // checking for selected row 
            	int choice = JOptionPane.showConfirmDialog(
            			null,
            			" Are you sure you wish to delete " + itemNameTxtValue.getText()+ "?", "Remove item",
    	                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            	
            	if(choice == JOptionPane.YES_OPTION) {
	              //remove selected row from the model
	                model.removeRow(table.getSelectedRow());
	                JOptionPane.showMessageDialog(null, itemNameTxtValue.getText().trim()+ " was deleted successfully.", 
	                		"Items List", JOptionPane.INFORMATION_MESSAGE);
            	}
            }   
	    }
	    
	    //Emptying all text fields and table  
	    private void clearAll() {
	    	codeTxtValue.setText(null);
	    	quantityTxtValue.setText(null);
	        itemNameTxtValue.setText(null);
	        unitPriceTxtValue.setText(null);
	    	model.setRowCount(0);
	    	checkoutButton.setEnabled(false);
	    }
	   	     
	    //Registering Button Listeners
	    public void registerListeners() {
	    	searchButton.addActionListener(this);
	    	addButton.addActionListener(this);
	    	deleteButton.addActionListener(this);
	    	clearButton.addActionListener(this);
	    	checkoutButton.addActionListener(this);
		}
	 
	    @Override
		public void actionPerformed(ActionEvent e) {
	    	if (e.getSource() == addCustomerButton) {
	    		new CustomerInsertDialog();
	        }
	    	if (e.getSource() == searchButton) {
	    		searchInventory();
	        }
	        if (e.getSource() == addButton) {
	            addItem();
	        }
	        if (e.getSource() ==  deleteButton) {
	            removeItem();
	        }
	    	if (e.getSource() == clearButton) {
	            clearAll();
	        }if (e.getSource() == checkoutButton) {
	        	new checkoutDialog(model);
	        	clearAll();
	        }
	   
	    }
	   
}
