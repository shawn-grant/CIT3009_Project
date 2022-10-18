package view;
//============================================================================
//Name        : CheckoutScreen.java
//Author      : Tyrien Gilpin
//Version     : 1
//Copyright   : Your copyright notice
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
import view.dialogs.inventory.InventoryInsertDialog;
import view.dialogs.inventory.InventoryUpdateDialog;

public class CheckoutScreen extends JPanel implements ActionListener{
	private JButton addButton; // add a new item
    private JButton deleteButton; // delete an item
    private JButton clearButton; // clear all items in table
    private JButton checkoutButton; //display invoice
    private JButton searchButton;
    private JLabel titleLabel;
    private JLabel productCodeLbl;
    private JLabel itemNameLabel;
    private JLabel quantityLabel;
    private JLabel unitPrice;
    private JTextField codeTxtValue;
    private JTextField quantityTxtValue;
    private JTextField itemNameTxtValue;
    private JTextField unitPriceTxtValue;
    private JPanel centerPanel;
    private JPanel mainContent; // w
	private final String[] TableColumns = {"Product Code", "Product Name", "Quantity", "Unit Price", "Cost"};
	private JTable table;
	private DefaultTableModel model;
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public CheckoutScreen() {
		setBackground(new Color(0, 100, 205));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setSize(800, 600);
	        
        initializeComponents();
        addComponentsToPanels();
        setContentView();
        addPanelsToWindow();
	    }
	
	
	/*Working Version
	private void initializeComponents() {
		//Setting titleLabel properties
        titleLabel = new JLabel("Checkout");
        titleLabel.setFont(new Font("arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
       
        productCodeLbl = new JLabel("Product Code");
        itemNameLabel = new JLabel("Item");
        quantityLabel = new JLabel("Quantity");;
        unitPrice = new JLabel("Unit Price");;
        
        //Initializing Text areas
        codeTxtValue = new JTextField(20);
        quantityTxtValue = new JTextField(20);
        itemNameTxtValue = new JTextField(20);
        unitPriceTxtValue = new JTextField(20);
        
        
        //initializing buttons
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        checkoutButton = new JButton("Checkout");
        searchButton = new JButton("Search");
        
        //Setting button sizes
    
        addButton.setPreferredSize(new Dimension(100, 40));
        deleteButton.setPreferredSize(new Dimension(100, 40));
        clearButton.setPreferredSize(new Dimension(100, 40));
        checkoutButton.setPreferredSize(new Dimension(100, 40));
        searchButton.setPreferredSize(new Dimension(100,20));
        
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
       
        
        mainContent = new JPanel(new GridLayout(0, 1, 0, 70));
        
        labelPanel = new JPanel(new GridBagLayout());
        labelPanel.setBackground(new Color(255, 255, 255));
        mainContent.setBackground(new Color(255, 255, 255));
       // labelPanel.setBounds(30, 30, 300, 200);////
        
		model = new DefaultTableModel(TableColumns, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //Set to not editable
        table.setAutoCreateRowSorter(true); //Enable sorting by columns
	 }
	
	
	
	 private void addComponentsToPanels() {
		//Adding Labels and fields to label panel
		gbc.gridx = 0;
		gbc.gridy = 0;
        labelPanel.add(productCodeLbl, gbc);
		 
        gbc.gridx = 4;
		gbc.gridy = 0;
		labelPanel.add(codeTxtValue, gbc);
		 
		gbc.gridx = 8;
		gbc.gridy = 0;
		labelPanel.add(searchButton, gbc);
		
		gbc.gridx = 11;
		gbc.gridy = 0;
		labelPanel.add(quantityLabel, gbc);
		
		gbc.gridx = 13;
		gbc.gridy = 0;
		labelPanel.add(quantityTxtValue, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		labelPanel.add(itemNameLabel, gbc);
		 
		gbc.gridx = 4;
		gbc.gridy = 4;
		labelPanel.add(itemNameTxtValue, gbc);
		
		gbc.gridx = 11;
		gbc.gridy = 4;
		labelPanel.add(unitPrice, gbc);
		
		gbc.gridx = 13;
		gbc.gridy = 4;
		labelPanel.add( unitPriceTxtValue, gbc);
		  
		//adding buttons to button panel
		gbc.gridx = 0;
		gbc.gridy = 8;
		labelPanel.add(addButton, gbc);
		
		gbc.gridx = 4;
		gbc.gridy = 8;
		labelPanel.add(deleteButton, gbc);
	    
	    gbc.gridx = 9;
		gbc.gridy = 8;
		labelPanel.add(clearButton, gbc);
       
		gbc.gridx = 13;
		gbc.gridy = 8;
		labelPanel.add(checkoutButton, gbc);  
	 }
	*/
	
	private void initializeComponents() {
		//Setting titleLabel properties
        titleLabel = new JLabel("Checkout");
        titleLabel.setFont(new Font("arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
       
        productCodeLbl = new JLabel("Product Code");
        itemNameLabel = new JLabel("Item");
        quantityLabel = new JLabel("Quantity");;
        unitPrice = new JLabel("Unit Price");;
        
        //Initializing Text areas
        codeTxtValue = new JTextField(20);
        quantityTxtValue = new JTextField(20);
        itemNameTxtValue = new JTextField(20);
        unitPriceTxtValue = new JTextField(20);
        
        codeTxtValue.setColumns(20);
        quantityTxtValue.setColumns(20);
        itemNameTxtValue.setColumns(20);
        unitPriceTxtValue.setColumns(20);
        
        //initializing buttons
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        checkoutButton = new JButton("Checkout");
        searchButton = new JButton("Search");
        
        //Setting button sizes
        addButton.setPreferredSize(new Dimension(100, 40));
        deleteButton.setPreferredSize(new Dimension(100, 40));
        clearButton.setPreferredSize(new Dimension(100, 40));
        checkoutButton.setPreferredSize(new Dimension(100, 40));
        searchButton.setPreferredSize(new Dimension(100,20));
        
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
       
        
        mainContent = new JPanel(new GridLayout(0, 1, 0, 70));
        centerPanel = new JPanel(new GridBagLayout());	//NTS: SetBounds??
        centerPanel.setBackground(new Color(255, 255, 255));
        mainContent.setBackground(new Color(255, 255, 255));
      
		model = new DefaultTableModel(TableColumns, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //Set to not editable
        table.setAutoCreateRowSorter(true); //Enable sorting by columns
        table.getTableHeader().setOpaque(false);//Removing background of table heading
        table.getTableHeader().setBackground(new Color(224, 224, 224));//Setting new background of table headings
        table.setBackground(Color.white);
        table.setForeground(Color.black);
	 }
	
	
	 private void addComponentsToPanels() {
		//Adding Labels and fields to label panel
		gbc.insets = new Insets(10, 10, 20, 10);//Sets Padding around values
		 
		gbc.gridx = 0;//Setting position on x axis
		gbc.gridy = 0;//Setting position on Y axis
		gbc.gridwidth = 1; //Setting number of columns occupied by the grid object
		gbc.fill = GridBagConstraints.HORIZONTAL;//Filling grid object into space specified grid width 
		centerPanel.add(productCodeLbl, gbc);
		 
        gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(codeTxtValue, gbc);
		
		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(searchButton, gbc);
		
		gbc.gridx = 7;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(quantityLabel, gbc);
		
		gbc.gridx = 9;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(quantityTxtValue, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(itemNameLabel, gbc);
		 
		gbc.gridx = 2;
		gbc.gridy = 7;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(itemNameTxtValue, gbc);
		
		gbc.gridx = 7;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(unitPrice, gbc);
		
		gbc.gridx = 9;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add( unitPriceTxtValue, gbc);
	
		//adding buttons to button panel
		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(addButton, gbc);
		
		gbc.gridx = 4;
		gbc.gridy = 10;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(deleteButton, gbc);
	    
	    gbc.gridx = 9;
		gbc.gridy = 10;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(clearButton, gbc);
       
		gbc.gridx = 13;
		gbc.gridy = 10;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
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


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
		
		
/*****************************Defining Button Actions*****************************/
		//Method to check if all fields are empty
		 private boolean validateFields() {
		        return !(codeTxtValue.getText().isEmpty() || itemNameTxtValue.getText().isEmpty()
		        		|| quantityTxtValue.getText().isEmpty() || unitPriceTxtValue.getText().isEmpty());
		    }
		 
		// search for product entered product information
	    private void searchInventory() {///NTS: Check
	        Client client = new Client();
	        client.sendAction("View Inventory");
	        List<Product> productList = client.receiveViewInventoryResponse();
	        client.closeConnections();
	        
	        for (Product product : productList) {
	           
	        	if(codeTxtValue.getText().trim() == product.getCode()) {
	        		//System.out.println(product);
	        		quantityTxtValue.setText("1");
	                itemNameTxtValue.setText(product.getName());
	                unitPriceTxtValue.setText(String.valueOf(product.getUnitPrice()));
	        	}	
	        }
	    }
	    
	    // adding product information to table
	    private void addItem() {
	    	if(validateFields()){
	    		
	    		try{
					int quantity = Integer.parseInt(quantityTxtValue.getText().trim());//getting value from quantity text field
					String cost = String.valueOf(Math.floor(quantity) * Double.parseDouble(unitPriceTxtValue.getText().trim())); //Calculating totals cost based on quantity
					model.insertRow(model.getRowCount()+1, new Object[]{codeTxtValue.getText().trim(), itemNameTxtValue.getText().trim(),
							quantityTxtValue.getText().trim(), unitPriceTxtValue.getText().trim(), cost});
				}catch (NumberFormatException e) {//If exceptions thrown, change value of quantity to 1
					JOptionPane.showMessageDialog(null, "Invalid Quantity value", "Invalid input",
							JOptionPane.ERROR_MESSAGE);		
				}
	    	}
	    }
	    
	    private void deleteItem() {
	    	
	    }

	    private void clearAll() {
	    	codeTxtValue.setText(null);
	    	quantityTxtValue.setText(null);
	        itemNameTxtValue.setText(null);
	        unitPriceTxtValue.setText(null);
	    	model.setRowCount(0);
	    }
	    
	    
	  /*  
	    @Override
	    public void actionPerformed(ActionEvent e) {

			 /*if (e.getSource().equals(searchButton)) {
	            searchInventory();
	        }*
	        
	        if (e.getSource().equals(addButton)) {
	            addItem();
	        }
	       
	        if (e.getSource().equals(deleteButton)) {
	            //CheckoutDeleteDialog removeDialog = new CheckoutDeleteDialog();
	            //removeDialog.setVisible(true);
	        }
	        if (e.getSource().equals(clearButton)) {
	            clearAll()
	        }
	        
	        if (e.getSource().equals(checkoutButton)) {
	            checkoutItem();
	        }
	   */
	    
	   
}
