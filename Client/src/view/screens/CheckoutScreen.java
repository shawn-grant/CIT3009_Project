package view.screens;
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
import models.Customer;
import models.Employee;
import models.Product;
import utils.IDGenerator;
import view.components.RoundedBorder;
import view.dialogs.checkout.*;

public class CheckoutScreen extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private final String[] TableColumns = {"Product Code", "Product Name", "Quantity", "Unit Price", "Cost"};
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final List<Product> productList = new ArrayList<>();    //NTS: ADD data to this list when you get back
    private JButton addButton, deleteButton, clearButton, checkoutButton, searchButton, addCustomerButton;
    private JLabel titleLabel, productCodeLbl, itemNameLabel, quantityLabel, unitPrice;
    private JLabel customer, staff;
    private JTextField codeTxtValue, quantityTxtValue, itemNameTxtValue, unitPriceTxtValue;
    private JTextField customerTxtValue, staffTxtValue;
    private JPanel centerPanel, mainContent;
    private JTable table;
    private DefaultTableModel model;

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

        Dimension labelSize = new Dimension(140, 30);
        Dimension fieldSize = new Dimension(20, 35);
        Font labelFont = new Font("arial", Font.BOLD, 15);
        Font fieldFont = new Font("arial", Font.PLAIN, 14);
        Color btnColour = new Color(224, 224, 224);

        //Setting titleLabel properties
        titleLabel = new JLabel("Checkout");
        titleLabel.setFont(new Font("arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);

        //Setting Label Properties
        productCodeLbl = new JLabel("Product Code");
        productCodeLbl.setFont(labelFont);
        productCodeLbl.setPreferredSize(labelSize);

        itemNameLabel = new JLabel("Item");
        itemNameLabel.setFont(labelFont);
        itemNameLabel.setPreferredSize(labelSize);

        quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(labelFont);
        quantityLabel.setPreferredSize(labelSize);

        unitPrice = new JLabel("Unit Price");
        unitPrice.setFont(labelFont);
        unitPrice.setPreferredSize(labelSize);

        customer = new JLabel("Customer ID");
        customer.setFont(labelFont);
        customer.setPreferredSize(labelSize);

        staff = new JLabel("Staff ID");
        staff.setFont(labelFont);
        staff.setPreferredSize(labelSize);

        //Initializing Text areas
        codeTxtValue = new JTextField();
        quantityTxtValue = new JTextField();
        itemNameTxtValue = new JTextField();
        unitPriceTxtValue = new JTextField();
        itemNameTxtValue.setEditable(false);
        unitPriceTxtValue.setEditable(false);
        customerTxtValue = new JTextField("C0000");
        staffTxtValue = new JTextField();

        //Setting text field properties
        codeTxtValue.setFont(fieldFont);
        codeTxtValue.setBorder(new RoundedBorder(8));
        codeTxtValue.setPreferredSize(fieldSize);

        quantityTxtValue.setFont(fieldFont);
        quantityTxtValue.setBorder(new RoundedBorder(8));
        quantityTxtValue.setPreferredSize(fieldSize);

        itemNameTxtValue.setFont(fieldFont);
        itemNameTxtValue.setBorder(new RoundedBorder(8));
        itemNameTxtValue.setPreferredSize(fieldSize);

        unitPriceTxtValue.setFont(fieldFont);
        unitPriceTxtValue.setBorder(new RoundedBorder(8));
        unitPriceTxtValue.setPreferredSize(fieldSize);

        customerTxtValue.setFont(fieldFont);
        customerTxtValue.setBorder(new RoundedBorder(8));
        customerTxtValue.setPreferredSize(fieldSize);

        staffTxtValue.setFont(fieldFont);
        staffTxtValue.setBorder(new RoundedBorder(8));
        staffTxtValue.setPreferredSize(fieldSize);

        //initializing buttons
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        checkoutButton = new JButton("Checkout");
        checkoutButton.setEnabled(false);
        searchButton = new JButton("Search");
        addCustomerButton = new JButton("Add customer");

        //Setting Properties
        addButton.setPreferredSize(labelSize);
        deleteButton.setPreferredSize(labelSize);
        clearButton.setPreferredSize(labelSize);
        checkoutButton.setPreferredSize(labelSize);
        searchButton.setPreferredSize(labelSize);
        addCustomerButton.setPreferredSize(labelSize);


        addButton.setFont(labelFont);
        addButton.setOpaque(true);
        addButton.setBorderPainted(false);
        addButton.setBackground(btnColour);

        deleteButton.setFont(labelFont);
        deleteButton.setOpaque(true);
        deleteButton.setBorderPainted(false);
        deleteButton.setBackground(btnColour);

        clearButton.setFont(labelFont);
        clearButton.setOpaque(true);
        clearButton.setBorderPainted(false);
        clearButton.setBackground(btnColour);

        checkoutButton.setFont(labelFont);
        checkoutButton.setOpaque(true);
        checkoutButton.setBorderPainted(false);
        checkoutButton.setBackground(btnColour);

        searchButton.setFont(labelFont);
        searchButton.setOpaque(true);
        searchButton.setBorderPainted(false);
        searchButton.setBackground(btnColour);

        addCustomerButton.setFont(labelFont);
        addCustomerButton.setOpaque(true);
        addCustomerButton.setBorderPainted(false);
        addCustomerButton.setBackground(btnColour);

        //Set focus paint
        addButton.setFocusPainted(false);
        checkoutButton.setFocusPainted(false);
        deleteButton.setFocusPainted(false);
        searchButton.setFocusPainted(false);
        addCustomerButton.setFocusPainted(false);

        //Setting Panel Properties
        mainContent = new JPanel(new GridLayout(0, 1, 0, 70));
        centerPanel = new JPanel(new GridBagLayout());    //NTS: SetBounds?? 
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

    private void addComponentsToPanels() {//Adding Labels and fields to label panel       
        gbc.insets = new Insets(20, 10, 20, 10);//Sets Padding around values        
        gbc.gridwidth = 1;
        gbc.weightx = -0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //////////////////////////First Row////////////////////
        gbc.gridx = 0;//Setting position on x-axis
        gbc.gridy = 0;//Setting position on y-axis
        centerPanel.add(customer, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(customerTxtValue, gbc);


        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 0;
        centerPanel.add(addCustomerButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        centerPanel.add(staff, gbc);


        gbc.gridx = 4;
        gbc.gridy = 0;
        centerPanel.add(staffTxtValue, gbc);

        ////////////////Second Row//////////////////
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

        /////////////////////////Third Row/////////////////////
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(itemNameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        centerPanel.add(itemNameTxtValue, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        centerPanel.add(unitPrice, gbc);

        gbc.gridx = 4;
        gbc.gridy = 2;
        centerPanel.add(unitPriceTxtValue, gbc);

        ////////////////////Fourth Row////////////////////////
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
    private boolean validateFields() { //Method to check if all fields are empty
        return !(codeTxtValue.getText().isEmpty() || itemNameTxtValue.getText().isEmpty()
                || quantityTxtValue.getText().isEmpty() || unitPriceTxtValue.getText().isEmpty());
    }

    private boolean stockCheck() {//Method to check if items in stock is less than quantity being sold
        int stock;
        if (productList.isEmpty()) {
            Client client = new Client();
            client.sendAction("Find Product");
            client.sendProductCode(codeTxtValue.getText());
            Product product = client.receiveFindProductResponse();
            client.closeConnections();
            if (product != null) {
                stock = product.getItemInStock() - Integer.parseInt(quantityTxtValue.getText());
                if (stock >= 0) {
                    product.setItemInStock(stock);
                    productList.add(product);
                } else {
                    JOptionPane.showMessageDialog(null, "Items in stock are less than quantity entered.",
                            "Iventory Shortage", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }
        } else {
            for (Product product : productList) {
                if (product.getCode().equals(codeTxtValue.getText().trim())) {
                    stock = product.getItemInStock() - Integer.parseInt(quantityTxtValue.getText());
                    product.setItemInStock(stock);
                    if (stock < 0) {
                        JOptionPane.showMessageDialog(null, "Items in stock are less than quantity entered.",
                                "Inventory Shortage", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void setFields(Product product) {//Auto fill empty fields with necessary product data if found
        if (codeTxtValue.getText().trim().equalsIgnoreCase(product.getCode())) {
            if (quantityTxtValue.getText().equals("") || quantityTxtValue.getText().equals(" ")) {//NTS: Doesn't work with null
                quantityTxtValue.setText("1");
            }
            itemNameTxtValue.setText(product.getName());
            unitPriceTxtValue.setText(String.valueOf(product.getUnitPrice()));

        } else {
            quantityTxtValue.setText(null);
            itemNameTxtValue.setText(null);
            unitPriceTxtValue.setText(null);
        }
    }

    private void searchInventory() {// search for product using product code entered
        Client client = new Client();
        if (!(codeTxtValue.getText().isEmpty())) {
            client.sendAction("Find Product");
            client.sendProductCode(codeTxtValue.getText().trim());
            Product product = client.receiveFindProductResponse();
            setFields(product);
            client.closeConnections();
        }
    }

    public void updateList() {//update the product list which is used during checkout process
        productList.clear();
        for (int i = 0; i < model.getRowCount(); i++) {//for each row in the table do....
            int remaining = 0;
            Client client = new Client();
            client.sendAction("Find Product");
            client.sendProductCode(model.getValueAt(i, 0).toString());//searching for product with code corresponding to product code in table
            Product product = client.receiveFindProductResponse();
            remaining = product.getItemInStock() - Integer.parseInt(model.getValueAt(i, 2).toString());
            product.setItemInStock(remaining); //updating number of items remaining in stock for a product
            productList.add(product);
            client.closeConnections();
        }
    }

    //This method prevents an item from being added to the table twice, 
    //instead it just updates the quantity and total cost of the item in the table
    public boolean itemInTable() {
        if (model.getRowCount() > 0) {
            for (int i = 0; i < model.getRowCount(); i++) {//for each row in the check....
                String code = "", cost = "";
                int quantity = 0;
                double total = 0;
                code = model.getValueAt(i, 0).toString();//searching for product with code corresponding to product code in table
                if (codeTxtValue.getText().equals(code)) {
                    try {
                        quantity = Integer.parseInt(quantityTxtValue.getText().trim());
                        cost = String.valueOf(Math.floor(quantity) * Double.parseDouble(unitPriceTxtValue.getText().trim()));
                        quantity = quantity + Integer.parseInt(model.getValueAt(i, 2).toString());
                        total = Double.parseDouble(cost) + Double.parseDouble(model.getValueAt(i, 4).toString());
                        model.setValueAt(total, i, 4);
                        model.setValueAt(quantity, i, 2);
                        return true;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid Quantity value", "Invalid input",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        return false;
    }


    private void addItem() { //adding product information to table
        if (validateFields()) {//if all necessary fields are filled
            if (stockCheck()) {//If quantity being requested for purchase is available in inventory
                try {
                    if (!itemInTable()) {//If the item is not already added to the display table
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
                        clearInput();
                        checkoutButton.setEnabled(true);
                    }
                } catch (NumberFormatException e) {//If exceptions thrown, change value of quantity to 1
                    JOptionPane.showMessageDialog(null, "Invalid Quantity value", "Invalid input",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    //This method removes a selected item from a table
    private void removeItem() {
        if (table.getSelectedRow() != -1) { // checking for selected row
            int choice = JOptionPane.showConfirmDialog(
                    null,
                    " Are you sure you wish to delete " + itemNameTxtValue.getText() + "?", "Remove item",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (choice == JOptionPane.YES_OPTION) {
                model.removeRow(table.getSelectedRow());//remove selected row from the model
                JOptionPane.showMessageDialog(null, itemNameTxtValue.getText().trim() + " was deleted successfully.",
                        "Items List", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    private void clearAll() {//Emptying all text fields and table
        codeTxtValue.setText(null);
        quantityTxtValue.setText(null);
        itemNameTxtValue.setText(null);
        unitPriceTxtValue.setText(null);
        model.setRowCount(0);
        checkoutButton.setEnabled(false);
    }

    private void clearInput() { //Clear text fields
        codeTxtValue.setText(null);
        quantityTxtValue.setText(null);
        itemNameTxtValue.setText(null);
        unitPriceTxtValue.setText(null);
    }

    public void registerListeners() {//Registering Button Listeners
        searchButton.addActionListener(this);
        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);
        checkoutButton.addActionListener(this);
        addCustomerButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCustomerButton) {
            customerTxtValue.setText(new IDGenerator().getID("C"));//Generate An ID value
            new InsertDialog(customerTxtValue.getText().trim());//Set the id field in the dialog to new ID generated
            Client client = new Client();
            client.sendAction("Find Customer");
            client.sendCustomerId(customerTxtValue.getText());
            Customer customer = client.receiveFindCustomerResponse();
            client.closeConnections();
            if (customer == null) {//If the new ID they tried to add did not process, rest customerID to default value C000
                customerTxtValue.setText("C0000");
            }
        }
        if (e.getSource() == searchButton) {
            searchInventory();
        }
        if (e.getSource() == addButton) {
            addItem();
        }
        if (e.getSource() == deleteButton) {
            removeItem();
        }
        if (e.getSource() == clearButton) {
            clearAll();
        }
        if (e.getSource() == checkoutButton) {
            if (!(staffTxtValue.getText().isEmpty() || customerTxtValue.getText().isEmpty())) {
                Client client = new Client();
                client.sendAction("Find Employee");
                client.sendEmployeeId(staffTxtValue.getText());
                Employee employee = client.receiveFindEmployeeResponse();
                client.closeConnections();
                if (employee == null) {//if employeeID doesn't exist, show error message
                    JOptionPane.showMessageDialog(null, "Employee does not exist", "Missing Information",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    updateList();//updating list before it is passed t the checkout dialog
                    new CheckoutDialog(model, productList, customerTxtValue.getText().trim(),
                            staffTxtValue.getText().trim());
                    clearInput();
                    productList.clear(); //Empty product list after checkout dialog is closed
                }
            } else {
                JOptionPane.showMessageDialog(null, "Missing Customer or Employee ID", "Missing Information",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
