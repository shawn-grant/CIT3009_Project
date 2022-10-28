package view.dialogs.checkout;

import client.Client;
import models.*;
import utils.CostValidator;
import view.components.RoundedBorder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckoutDialog extends JDialog implements ActionListener {

    private final DefaultTableModel model;
    private final List<Product> productList;
    private final List<Integer> quantityList;
    private JLabel totalItemsLabel, totalCostLabel, staffIDLabel, customerIDLabel, tenderedLabel;
    private JTextField totalItemsField, totalCostField, employeeIdField, customerIdField;
    private JTextField tenderedField;
    private JButton cashOutButton, cancelButton;

    public CheckoutDialog(DefaultTableModel model, List<Product> productList,
                          List<Integer> quantityList, String customer, String staff) {
        this.model = model;
        this.productList = productList;
        this.quantityList = quantityList;
        initializeComponents(customer, staff);
        addPanelsToWindow();
        registerListeners();
        setWindowProperties();
    }

    private void initializeComponents(String customer, String staff) {
        Dimension labelSize = new Dimension(140, 20);
        Dimension fieldSize = new Dimension(250, 35);
        Font labelFont = new Font("arial", Font.BOLD, 14);
        Font fieldFont = new Font("arial", Font.PLAIN, 14);

        //Label properties
        staffIDLabel = new JLabel("Staff ID");
        staffIDLabel.setFont(labelFont);
        staffIDLabel.setPreferredSize(labelSize);

        customerIDLabel = new JLabel("Customer ID");
        customerIDLabel.setFont(labelFont);
        customerIDLabel.setPreferredSize(labelSize);

        totalItemsLabel = new JLabel("Total Items");
        totalItemsLabel.setFont(labelFont);
        totalItemsLabel.setPreferredSize(labelSize);

        totalCostLabel = new JLabel("Total Cost");
        totalCostLabel.setFont(labelFont);
        totalCostLabel.setPreferredSize(labelSize);

        tenderedLabel = new JLabel("Tendered");
        tenderedLabel.setFont(labelFont);
        tenderedLabel.setPreferredSize(labelSize);

        //Field properties
        employeeIdField = new JTextField();
        employeeIdField.setBorder(new RoundedBorder(8));
        employeeIdField.setFont(fieldFont);
        employeeIdField.setPreferredSize(fieldSize);
        employeeIdField.setText(staff);
        employeeIdField.setEditable(false);

        customerIdField = new JTextField("N/A");
        customerIdField.setBorder(new RoundedBorder(8));
        customerIdField.setPreferredSize(fieldSize);
        customerIdField.setFont(fieldFont);
        customerIdField.setText(customer);
        customerIdField.setEditable(false);

        totalItemsField = new JTextField();
        totalItemsField.setBorder(new RoundedBorder(8));
        totalItemsField.setPreferredSize(fieldSize);
        totalItemsField.setEditable(false);
        totalItemsField.setText(Integer.toString(getTotalQuantity()));
        totalItemsField.setFont(fieldFont);

        totalCostField = new JTextField();
        totalCostField.setBorder(new RoundedBorder(8));
        totalCostField.setPreferredSize(fieldSize);
        totalCostField.setEditable(false);
        totalCostField.setText(String.valueOf(calculateCost()));
        totalCostField.setFont(fieldFont);

        tenderedField = new JTextField();
        tenderedField.setBorder(new RoundedBorder(8));
        tenderedField.setPreferredSize(fieldSize);
        tenderedField.setFont(fieldFont);

        cashOutButton = new JButton("CASH OUT");
        cashOutButton.setPreferredSize(new Dimension(200, 30));
        cashOutButton.setForeground(Color.BLUE);
        cashOutButton.setFont(labelFont);

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setForeground(Color.RED);
        cancelButton.setFont(labelFont);
    }

    //Adding Labels and text-fields to window
    private void addPanelsToWindow() {
        add(staffIDLabel);
        add(employeeIdField);
        add(customerIDLabel);
        add(customerIdField);
        add(totalItemsLabel);
        add(totalItemsField);
        add(totalCostLabel);
        add(totalCostField);
        add(tenderedLabel);
        add(tenderedField);
        add(cashOutButton);
        add(cancelButton);
    }

    //Setting properties of dialog window
    private void setWindowProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setTitle("Cash Out Items");
        setSize(420, 280);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setVisible(true);
    }

    //method to check if ant fields is empty
    private boolean validateFields() {
        if (tenderedField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Cash tendered cannot be empty",
                    "Warning", JOptionPane.WARNING_MESSAGE
            );
            return false;
        } else return CostValidator.isValid(tenderedField.getText(), this);
    }

    //randomizing the generation of invoice numbers
    private int generateInvoiceNum() {
        int value;
        value = (int) ((Math.random() * (70000 - 100)) + 100);
        return value;
    }

    //calculating how much change customer will receive
    public float calculateCost() {
        float cost = getTotalCost();
        if (!customerIdField.getText().equals("C0000")) {
            //Request to find a customer
            Client client = new Client();
            client.sendAction("Find Customer");
            client.sendCustomerId(customerIdField.getText());
            Customer customer = client.receiveFindCustomerResponse();
            client.closeConnections();

            //Get current local time
            LocalDate localDateTime = LocalDate.now();
            //Convert to Date
            Date currentDate = Date.from(localDateTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
            //Get customer membership expiry date
            Date expiryDate = customer.getMembershipExpiryDate();

            if (currentDate.compareTo(expiryDate) <= 0) {
                //Apply 10% discount
                cost = (float) (cost - (cost * 0.10));
            }
        }
        //Apply 15% GCT
        cost = (float) (cost + (cost * 0.15));
        totalCostField.setText(String.valueOf(cost));
        return cost;
    }

    public void registerListeners() {
        cashOutButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    //Retrieving total number of items being purchased by customer
    public int getTotalQuantity() {
        int sum = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            sum = sum + Integer.parseInt(model.getValueAt(i, 2).toString());
        }
        return sum;
    }

    //calculating the total billing from the invoice using data in table
    public float getTotalCost() {
        float sum = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            sum = sum + Float.parseFloat(model.getValueAt(i, 4).toString());
        }
        return sum;
    }

    //Updates the inventory items after customer is cashed out
    public void updateInventory() {
        //For each product being checked out
        for (Product product : productList) {
            //update all fields to update the product quantity in inventory
            Client client = new Client();
            client.sendAction("Update Product");
            Product prod = new Product(
                    product.getCode(),
                    product.getName(),
                    product.getShortDescription(),
                    product.getLongDescription(),
                    product.getItemInStock(),
                    product.getUnitPrice()
            );
            client.sendProduct(prod);
            client.receiveResponse();
            client.closeConnections();
            System.out.println(prod);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cashOutButton)) {
            if (validateFields()) {
                float tendered = Float.parseFloat(tenderedField.getText());
                float change = tendered - calculateCost();
                if (change < 0.0) {
                    JOptionPane.showMessageDialog(null, "Insufficient amount tendered",
                            "Customer Change", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    updateInventory();
                    int invoiceNumber = generateInvoiceNum();
                    //Request to add an invoice
                    Client client = new Client();
                    client.sendAction("Add Invoice");
                    Invoice invoice = new Invoice(
                            invoiceNumber,
                            new Date(),
                            Float.parseFloat(totalCostField.getText().trim()),
                            Float.parseFloat(tenderedField.getText().trim()),
                            employeeIdField.getText().trim(),
                            customerIdField.getText().trim()
                    );
                    client.sendInvoice(invoice);
                    client.receiveResponse();
                    client.closeConnections();

                    List<InvoiceItem> invoiceItemList = new ArrayList<>();
                    List<Inventory> inventoryList = new ArrayList<>();
                    int index = 0;
                    for (Product product : productList) {
                        invoiceItemList.add(new InvoiceItem(
                                new InvoiceItemId(invoiceNumber, product.getName()),
                                quantityList.get(index),
                                product.getUnitPrice())
                        );
                        inventoryList.add(new Inventory(
                                new InventoryId(product.getCode(), new Date()),
                                product.getItemInStock(),
                                product.getUnitPrice(),
                                quantityList.get(index++))
                        );
                    }

                    //Request to add invoice items
                    client = new Client();
                    client.sendAction("Add Invoice Item");
                    client.sendInvoiceItem(invoiceItemList);
                    client.receiveResponse();
                    client.closeConnections();

                    //Request to update inventory
                    client = new Client();
                    client.sendAction("Update Inventory");
                    client.sendInventory(inventoryList);
                    client.receiveResponse();
                    client.closeConnections();

                    JOptionPane.showMessageDialog(null, "Customer should receive $" + change + " in change.",
                            "Customer Change", JOptionPane.INFORMATION_MESSAGE);
                    model.setRowCount(0);
                    dispose();
                }
            }
        }
        if (e.getSource().equals(cancelButton)) {
            dispose();
        }
    }
}