/**
 * ReportScreen.java
 * Report Screen to display reports
 * Author (s): Shawn Grant and Malik Heron
 */
package view.screens;

import javax.swing.*;

import client.Client;
import models.Inventory;
import models.InventoryId;
import models.Product;
import view.components.DatePicker;
import view.components.RoundedBorder;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportScreen extends BaseScreen implements ActionListener {

    private final Locale locale = new Locale("en", "EN");
    private final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
    private JPanel container, leftPanel, rightPanel;
    private JLabel productLabel;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JTextArea reportTextArea;
    private JComboBox<String> productSelect;
    private DatePicker startDateField;
    private DatePicker endDateField;
    private JButton generateButton, printButton;
    private List<Product> productList;

    public ReportScreen() {
        super("Generate Reports");

        buttonPanel.setVisible(false);
        initializeComponents();
        addComponentsToPanels();
        setupListeners();
        setContentView();
    }

    private void initializeComponents() {
        Dimension labelSize = new Dimension(250, 20);
        Dimension fieldSize = new Dimension(250, 35);
        Font labelFont = new Font("arial", Font.BOLD, 14);
        Font fieldFont = new Font("arial", Font.PLAIN, 14);

        container = new JPanel(new FlowLayout(FlowLayout.CENTER));
        leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setPreferredSize(new Dimension(300, 450));
        leftPanel.setBorder(new RoundedBorder(8));
        leftPanel.setBackground(Color.WHITE);

        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(500, 450));
        rightPanel.setBorder(new RoundedBorder(8));
        rightPanel.setBackground(Color.WHITE);

        productLabel = new JLabel("Product");
        productLabel.setFont(labelFont);
        productLabel.setPreferredSize(labelSize);

        startDateLabel = new JLabel("Start Date");
        startDateLabel.setFont(labelFont);
        startDateLabel.setPreferredSize(labelSize);

        endDateLabel = new JLabel("End Date");
        endDateLabel.setFont(labelFont);
        endDateLabel.setPreferredSize(labelSize);

        reportTextArea = new JTextArea("Your Report will show here...");
        reportTextArea.setEditable(false);
        reportTextArea.setPreferredSize(new Dimension(490, 400));
        reportTextArea.setLineWrap(true);
        reportTextArea.setFont(new Font("arial", Font.PLAIN, 16));

        productSelect = new JComboBox<>(getProducts());
        productSelect.setBorder(new RoundedBorder(8));
        productSelect.setPreferredSize(fieldSize);
        productSelect.setFont(fieldFont);
        productSelect.setFocusable(false);

        startDateField = new DatePicker();
        endDateField = new DatePicker();

        generateButton = new JButton("GENERATE REPORT");
        generateButton.setPreferredSize(new Dimension(250, 40));
        generateButton.setFont(labelFont);
        generateButton.setFocusPainted(false);

        printButton = new JButton("Print");
        printButton.setPreferredSize(new Dimension(250, 40));
        printButton.setFont(labelFont);
//        printButton.setEnabled(false);
    }

    private void addComponentsToPanels() {
        leftPanel.add(productLabel);
        leftPanel.add(productSelect);

        leftPanel.add(startDateLabel);
        leftPanel.add(startDateField);

        leftPanel.add(endDateLabel);
        leftPanel.add(endDateField);
        leftPanel.add(Box.createRigidArea(new Dimension(250, 50))); // vertical spacing

        leftPanel.add(generateButton);
        leftPanel.add(printButton);

        rightPanel.add(reportTextArea);

        container.add(leftPanel);
        container.add(rightPanel);
    }

    // setup actions for buttons
    private void setupListeners() {
        generateButton.addActionListener(this);
        printButton.addActionListener(this);
    }

    // set main content view
    private void setContentView() {
        setMainContent(container);
    }

    //Get list of all products
    private String[] getProducts() {
        Client client = new Client();
        client.sendAction("View Products");
        productList = client.receiveViewProductsResponse();
        client.closeConnections();

        String[] products = new String[productList.size()];

        for (int i = 0; i < productList.size(); i++) {
            //Format item name using product code plus product name
            products[i] = productList.get(i).getCode() + " - " + productList.get(i).getName();
        }
        return products;
    }

    //Get item information using start date
    private Inventory getInventoryItemByStartDate() {
        String productCode = productList.get(productSelect.getSelectedIndex()).getCode();
        Date startDate = startDateField.getSelectedDate();
        Client client = new Client();
        client.sendAction("View Inventory Item");
        client.sendInventoryInfo(new InventoryId(productCode, startDate));
        Inventory inventory = client.receiveViewInventoryItemResponse();
        System.out.println(inventory);
        client.closeConnections();
        return inventory;
    }

    //Get item information using end date
    private Inventory getInventoryItemByEndDate() {
        String productCode = productList.get(productSelect.getSelectedIndex()).getCode();
        Date endDate = endDateField.getSelectedDate();
        Client client = new Client();
        client.sendAction("View Inventory Item");
        client.sendInventoryInfo(new InventoryId(productCode, endDate));
        Inventory inventory = client.receiveViewInventoryItemResponse();
        client.closeConnections();
        return inventory;
    }

    //Get product information from server
    private Product getProductInfo(Inventory inventory) {
        Client client = new Client();
        client.sendAction("Find Product");
        client.sendProductCode(inventory.getId().getCode());
        Product product = client.receiveFindProductResponse();
        client.closeConnections();
        return product;
    }

    //Generate report using start and end dates
    private void generateReport() {
        Product product;
        Inventory inventoryStart = getInventoryItemByStartDate();
        Inventory inventoryEnd = getInventoryItemByEndDate();

        if (inventoryStart != null && inventoryEnd != null) {
            // set printButton to enabled once a report has been generated
            printButton.setEnabled(true);
            // Get product details
            product = getProductInfo(inventoryStart);
            // Add information to text area
            reportTextArea.setText("");
            reportTextArea.append("Product name: " + product.getName());

            //Start and end date formatted (DD, MMM,  YYYY)
            String startDate = dateFormat.format(inventoryStart.getId().getDateModified());
            String endDate = dateFormat.format(inventoryEnd.getId().getDateModified());

            //Check if start and end dates are the same
            if (startDateField.getSelectedDate().compareTo(endDateField.getSelectedDate()) == 0) {
                reportTextArea.append("\n\nStock as at " + startDate + ": " + inventoryStart.getStock());
                reportTextArea.append("\n\nUnit Cost as at " + startDate + ": $" + inventoryStart.getUnitPrice());
                reportTextArea.append("\n\nAmount Purchased: " + inventoryStart.getAmountPurchased());
            } else {
                reportTextArea.append("\n\nStock as at " + startDate + ": " + inventoryStart.getStock());
                reportTextArea.append("\n\nUnit Cost as at " + startDate + ": $" + inventoryStart.getUnitPrice());
                reportTextArea.append("\n\nStock as at " + endDate + ": " + inventoryEnd.getStock());
                reportTextArea.append("\n\nUnit Cost as at " + endDate + ": $" + inventoryEnd.getUnitPrice());
                reportTextArea.append("\n\nAmount Purchased: " +
                        (inventoryStart.getAmountPurchased() + inventoryEnd.getAmountPurchased()));
            }
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Unable to generate report",
                    "Report Status",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void printReport() {
        try {
            // add spacing between content and header
            reportTextArea.insert("\n\n", 0);
            // trigger system print dialog
            reportTextArea.print(new MessageFormat("REPORT | JANS WHOLESALE & RETAIL"), null);
        } catch (PrinterException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(generateButton)) {
            generateReport();
        }
        if (e.getSource().equals(printButton)) {
            printReport();
        }
    }
}
