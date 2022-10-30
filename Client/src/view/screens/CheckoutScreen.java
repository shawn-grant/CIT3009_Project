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

import javax.swing.JButton;
import javax.swing.JComboBox;
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
import utils.GenerateCodeList;
import utils.IDGenerator;
import view.components.RoundedBorder;
import view.dialogs.checkout.*;
import view.dialogs.customer.InsertDialog;

public class CheckoutScreen extends BaseScreen implements ActionListener {

    private final String[] tableHeaders = {"Product Code", "Product Name", "Quantity", "Unit Price", "Cost"};
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final List<Product> productList = new ArrayList<>();
    private final List<Integer> quantityList = new ArrayList<>();
    private JButton addButton, deleteButton, clearButton, checkoutButton, searchButton, addCustomerButton;
    private JLabel productCodeLabel, itemNameLabel, quantityLabel, unitPriceLabel;
    private JLabel customer, staff;
    private JTextField quantityField, itemNameField, unitPriceField;
    private String productCode = "";
    private JTextField customerIdField, employeeIdField;
    private JComboBox<String> codeList;
    private JPanel centerPanel, mainContent;
    private JTable table;
    private DefaultTableModel model;

    public CheckoutScreen() {
        super("Check Out");

        buttonPanel.setVisible(false);
        initializeComponents();
        addComponentsToPanels();
        setContentView();
        addPanelsToWindow();
        setupListeners();
    }

    private void initializeComponents() {

        Dimension labelSize = new Dimension(140, 30);
        Dimension fieldSize = new Dimension(20, 35);
        Font labelFont = new Font("arial", Font.BOLD, 15);
        Font fieldFont = new Font("arial", Font.PLAIN, 14);
        Color buttonColour = new Color(224, 224, 224);

        //Setting Label Properties
        productCodeLabel = new JLabel("Product Code");
        productCodeLabel.setFont(labelFont);
        productCodeLabel.setPreferredSize(labelSize);

        itemNameLabel = new JLabel("Item");
        itemNameLabel.setFont(labelFont);
        itemNameLabel.setPreferredSize(labelSize);

        quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(labelFont);
        quantityLabel.setPreferredSize(labelSize);

        unitPriceLabel = new JLabel("Unit Price");
        unitPriceLabel.setFont(labelFont);
        unitPriceLabel.setPreferredSize(labelSize);

        customer = new JLabel("Customer ID");
        customer.setFont(labelFont);
        customer.setPreferredSize(labelSize);

        staff = new JLabel("Staff ID");
        staff.setFont(labelFont);
        staff.setPreferredSize(labelSize);

        //Initializing Text areas
        quantityField = new JTextField();
        itemNameField = new JTextField();
        unitPriceField = new JTextField();
        itemNameField.setEditable(false);
        unitPriceField.setEditable(false);
        customerIdField = new JTextField("C0000");
        employeeIdField = new JTextField();

        codeList = new JComboBox<>(new GenerateCodeList().getCodes());
        codeList.setFont(fieldFont);
        codeList.setBorder(new RoundedBorder(8));
        codeList.setPreferredSize(new Dimension(20, 40));
        codeList.setOpaque(false);
        codeList.setFocusable(false);

        quantityField.setFont(fieldFont);
        quantityField.setBorder(new RoundedBorder(8));
        quantityField.setPreferredSize(fieldSize);

        itemNameField.setFont(fieldFont);
        itemNameField.setBorder(new RoundedBorder(8));
        itemNameField.setPreferredSize(fieldSize);

        unitPriceField.setFont(fieldFont);
        unitPriceField.setBorder(new RoundedBorder(8));
        unitPriceField.setPreferredSize(fieldSize);

        customerIdField.setFont(fieldFont);
        customerIdField.setBorder(new RoundedBorder(8));
        customerIdField.setPreferredSize(fieldSize);

        employeeIdField.setFont(fieldFont);
        employeeIdField.setBorder(new RoundedBorder(8));
        employeeIdField.setPreferredSize(fieldSize);

        //initializing buttons
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        checkoutButton = new JButton("Checkout");
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
        addButton.setBackground(buttonColour);

        deleteButton.setFont(labelFont);
        deleteButton.setOpaque(true);
        deleteButton.setBorderPainted(false);
        deleteButton.setBackground(buttonColour);

        clearButton.setFont(labelFont);
        clearButton.setOpaque(true);
        clearButton.setBorderPainted(false);
        clearButton.setBackground(buttonColour);

        checkoutButton.setFont(labelFont);
        checkoutButton.setOpaque(true);
        checkoutButton.setBorderPainted(false);
        checkoutButton.setBackground(buttonColour);
        checkoutButton.setEnabled(false);

        searchButton.setFont(labelFont);
        searchButton.setOpaque(true);
        searchButton.setBorderPainted(false);
        searchButton.setBackground(buttonColour);

        addCustomerButton.setFont(labelFont);
        addCustomerButton.setOpaque(true);
        addCustomerButton.setBorderPainted(false);
        addCustomerButton.setBackground(buttonColour);

        //Set focus paint
        addButton.setFocusPainted(false);
        checkoutButton.setFocusPainted(false);
        deleteButton.setFocusPainted(false);
        searchButton.setFocusPainted(false);
        addCustomerButton.setFocusPainted(false);

        //Setting Panel Properties
        mainContent = new JPanel(new GridLayout(0, 1, 0, 70));
        centerPanel = new JPanel(new GridBagLayout());//NTS: SetBounds??
        centerPanel.setBackground(new Color(255, 255, 255));
        mainContent.setBackground(new Color(255, 255, 255));

        //Setting Table properties
        model = new DefaultTableModel(tableHeaders, 0);
        table = new JTable(model);
        //Set to not editable
        table.setDefaultEditor(Object.class, null);
        //Enable sorting by columns
        table.setAutoCreateRowSorter(true);
        //Removing background of table heading
        table.getTableHeader().setOpaque(false);
        //Setting new background of table headings
        table.getTableHeader().setBackground(new Color(224, 224, 224));
        table.setBackground(Color.white);
        table.setForeground(Color.black);
    }

    //Adding Labels and fields to label panel
    private void addComponentsToPanels() {
        //Sets Padding around values
        gbc.insets = new Insets(20, 10, 20, 10);
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
        centerPanel.add(customerIdField, gbc);

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
        centerPanel.add(employeeIdField, gbc);

        ////////////////Second Row//////////////////
        gbc.gridx = 0;//Setting position on x-axis
        gbc.gridy = 1;//Setting position on y-axis
        centerPanel.add(productCodeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(codeList, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        centerPanel.add(searchButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        centerPanel.add(quantityLabel, gbc);

        gbc.gridx = 4;
        gbc.gridy = 1;
        centerPanel.add(quantityField, gbc);

        /////////////////////////Third Row/////////////////////
        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(itemNameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        centerPanel.add(itemNameField, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        centerPanel.add(unitPriceLabel, gbc);

        gbc.gridx = 4;
        gbc.gridy = 2;
        centerPanel.add(unitPriceField, gbc);

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

    //Method to check if all fields are empty
    private boolean validateFields() {
        return !(productCode.equals("") || itemNameField.getText().isEmpty()
                || quantityField.getText().isEmpty() || unitPriceField.getText().isEmpty());
    }

    //Method to check if items in stock is less than quantity being sold
    private boolean stockCheck() {
        int stock;
        if (productList.isEmpty()) {
            Client client = new Client();
            client.sendAction("Find Product");
            client.sendProductCode(productCode);
            Product product = client.receiveFindProductResponse();
            client.closeConnections();
            if (product != null) {
                stock = product.getItemInStock() - Integer.parseInt(quantityField.getText());
                if (stock >= 0) {
                    product.setItemInStock(stock);
                    productList.add(product);
                } else {
                    JOptionPane.showMessageDialog(null, "Items in stock are less than quantity entered.",
                            "Inventory Shortage", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }
        } else {
            for (Product product : productList) {
                if (product.getCode().equals(productCode)) {
                    stock = product.getItemInStock() - Integer.parseInt(quantityField.getText());
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

    //Autofill empty fields with necessary product data if found
    private void setFields(Product product) {
        if (productCode.equalsIgnoreCase(product.getCode())) {
            if (quantityField.getText().equals("") || quantityField.getText().equals(" ")) {//NTS: Doesn't work with null
                quantityField.setText("1");
            }
            itemNameField.setText(product.getName());
            unitPriceField.setText(String.valueOf(product.getUnitPrice()));
        } else {
            quantityField.setText(null);
            itemNameField.setText(null);
            unitPriceField.setText(null);
        }
    }

    // search for product using product code entered
    private void searchProducts() {
        Client client = new Client();
        if (!(productCode.equals(""))) {
            client.sendAction("Find Product");
            client.sendProductCode(productCode);
            Product product = client.receiveFindProductResponse();
            setFields(product);
            client.closeConnections();
        }
    }

    //update the product list which is used during checkout process
    private void updateList() {
        productList.clear();
        //for each row in the table do....
        for (int i = 0; i < model.getRowCount(); i++) {
            int remaining;
            Client client = new Client();
            client.sendAction("Find Product");
            //searching for product with code corresponding to product code in table
            client.sendProductCode(model.getValueAt(i, 0).toString());
            Product product = client.receiveFindProductResponse();
            remaining = product.getItemInStock() - Integer.parseInt(model.getValueAt(i, 2).toString());
            //updating number of items remaining in stock for a product
            product.setItemInStock(remaining);
            productList.add(product);
            quantityList.add(Integer.valueOf(model.getValueAt(i, 2).toString()));
            client.closeConnections();
        }
    }

    //This method prevents an item from being added to the table twice, 
    //instead it just updates the quantity and total cost of the item in the table
    private boolean itemInTable() {
        if (model.getRowCount() > 0) {
            //for each row in the check....
            for (int i = 0; i < model.getRowCount(); i++) {
                String code, cost;
                int quantity;
                double total;
                //searching for product with code corresponding to product code in table
                code = model.getValueAt(i, 0).toString();
                if (productCode.equals(code)) {
                    try {
                        quantity = Integer.parseInt(quantityField.getText().trim());
                        cost = String.valueOf(Math.floor(quantity) * Double.parseDouble(unitPriceField.getText().trim()));
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

    //adding product information to table
    private void addItem() {
        //if all necessary fields are filled
        if (validateFields()) {
            //If quantity being requested for purchase is available in inventory
            if (stockCheck()) {
                try {
                    //If the item is not already added to the display table
                    if (!itemInTable()) {
                        //getting value from quantity text field
                        int quantity = Integer.parseInt(quantityField.getText().trim());
                        //Calculating totals cost based on quantity
                        String cost = String.valueOf(Math.floor(quantity) * Double.parseDouble(unitPriceField.getText().trim()));
                        //Storing entire row in list object
                        List<String> data = new ArrayList<>();
                        data.add(productCode);
                        data.add(itemNameField.getText().trim());
                        data.add(quantityField.getText().trim());
                        data.add(unitPriceField.getText().trim());
                        data.add(cost);
                        model.addRow(data.toArray());
                        table.setModel(model);

                        //Emptying text fields after adding values to table
                        clearInput();
                        checkoutButton.setEnabled(true);
                    }
                } catch (NumberFormatException e) {
                    //If exceptions thrown, change value of quantity to 1
                    JOptionPane.showMessageDialog(null, "Invalid Quantity value", "Invalid input",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    //This method removes a selected item from a table
    private void removeItem() {
        // checking for selected row
        if (table.getSelectedRow() != -1) {
            String itemName = model.getValueAt(table.getSelectedRow(), 1).toString();
            int choice = JOptionPane.showConfirmDialog(
                    null,
                    " Are you sure you want to remove " + itemName + "?", "Remove item",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
            );
            if (choice == JOptionPane.YES_OPTION) {
                //remove selected row from the model
                model.removeRow(table.getSelectedRow());
                JOptionPane.showMessageDialog(
                        null,
                        itemName + " was deleted successfully.",
                        "Items List", JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }

    //Emptying all text fields and table
    private void clearAll() {
        productCode = "";
        codeList.setSelectedIndex(0);
        quantityField.setText(null);
        itemNameField.setText(null);
        unitPriceField.setText(null);
        model.setRowCount(0);
        checkoutButton.setEnabled(false);
    }

    //Clear text fields
    private void clearInput() {
        productCode = "";
        codeList.setSelectedIndex(0);
        quantityField.setText(null);
        itemNameField.setText(null);
        unitPriceField.setText(null);
    }

    //Registering Button Listeners
    public void setupListeners() {
        searchButton.addActionListener(this);
        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);
        checkoutButton.addActionListener(this);
        addCustomerButton.addActionListener(this);
        codeList.addActionListener(this);
    }

    //Defining Button Actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(addCustomerButton)) {
            //Generate An ID value
            customerIdField.setText(new IDGenerator().getID("C"));
            //Set the id field in the dialog to new ID generated
            new InsertDialog(customerIdField.getText());
            Client client = new Client();
            client.sendAction("Find Customer");
            client.sendCustomerId(customerIdField.getText().trim());
            Customer customer = client.receiveFindCustomerResponse();
            client.closeConnections();
            //If the new ID they tried to add did not process, reset customerID to default value C000
            if (customer == null) {
                customerIdField.setText("C0000");
            }
        }
        if (e.getSource().equals(codeList)) {
            productCode = (String) codeList.getSelectedItem();
        }
        if (e.getSource().equals(searchButton)) {
            searchProducts();
        }
        if (e.getSource().equals(addButton)) {
            addItem();
        }
        if (e.getSource().equals(deleteButton)) {
            removeItem();
        }
        if (e.getSource().equals(clearButton)) {
            clearAll();
        }
        if (e.getSource().equals(checkoutButton)) {
            if (!(employeeIdField.getText().isEmpty() || customerIdField.getText().isEmpty())) {
                Client client = new Client();
                client.sendAction("Find Employee");
                client.sendEmployeeId(employeeIdField.getText().trim());
                Employee employee = client.receiveFindEmployeeResponse();
                client.closeConnections();

                //if employeeID doesn't exist, show error message
                if (employee != null) {
                    boolean found = true;
                    //Check if customer other than default is entered
                     if (!customerIdField.getText().equals("C0000")) {
                         client = new Client();
                         client.sendAction("Find Customer");
                         client.sendCustomerId(customerIdField.getText().trim());
                         Customer customer = client.receiveFindCustomerResponse();
                         client.closeConnections();

                         //if customerID doesn't exist, show error message
                         if (customer == null) {
                             found = false;
                         }
                     }
                     //Continue if customer exists
                     if (found) {
                         //updating list before it is passed the checkout dialog
                         updateList();
                         new CheckoutDialog(
                                 model,
                                 productList,
                                 quantityList,
                                 customerIdField.getText().trim(),
                                 employeeIdField.getText().trim()
                         );
                         clearInput();
                         //Empty product list after checkout dialog is closed
                         productList.clear();
                     }
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Missing Customer or Employee ID",
                        "Missing Information",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
