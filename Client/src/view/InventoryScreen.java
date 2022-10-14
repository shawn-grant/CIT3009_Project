package view;

import client.Client;
import models.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InventoryScreen implements ActionListener {

    private final String[] TableHead = {"Product Code", "Product Name", "Short Description", "Long Description",
            "Items in Stock", "Unit Price"};
    private JButton refreshButton, productButton, backButton;
    private JPanel leftPanel, rightPanel;
    private JTable table;
    private DefaultTableModel model;

    public InventoryScreen() {
        initializeComponents();
        addComponentsToPanel();
        registerListeners();
        getInventory();
    }

    private void initializeComponents() {
        //Button properties
        productButton = new JButton("New Product");
        productButton.setBounds(100, 90, 150, 50);
        productButton.setFocusPainted(false);
        productButton.setFont(new Font("times new roman", Font.PLAIN, 18));

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(100, 170, 150, 50);
        refreshButton.setFocusPainted(false);
        refreshButton.setFont(new Font("times new roman", Font.PLAIN, 18));

        backButton = new JButton("Back");
        backButton.setBounds(100, 250, 150, 50);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("times new roman", Font.PLAIN, 18));

        //Table properties
        model = new DefaultTableModel(TableHead, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //Set to not editable
        table.setAutoCreateRowSorter(true); //Enable sorting by columns

        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setSize(new Dimension(350, 500));
        leftPanel.setBackground(new Color(0, 140, 255));

        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(1, 1, 5, 5));
        rightPanel.setSize(new Dimension(880, 500));
    }

    private void addComponentsToPanel() {
        rightPanel.add(new JScrollPane(table));
        leftPanel.add(refreshButton);
        leftPanel.add(productButton);
        leftPanel.add(backButton);
    }

    private void registerListeners() {
        refreshButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    private void getInventory() {
        Client client = new Client();
        client.sendAction("View Inventory");
        List<Product> productList = client.receiveViewInventoryResponse();
        client.closeConnections();

        int count = 0;
        int rowCount = model.getRowCount();
        int counter = 0;

        while (counter < rowCount) {
            model.removeRow(count);
            counter++;
        }

        for (Product product : productList) {
            System.out.println(product);

            model.insertRow(count, new Object[]{product.getCode(), product.getName(),
                    product.getShortDescription(), product.getLongDescription(),
                    product.getItemInStock(), product.getUnitPrice()});
            count++;
        }
    }

    public JComponent[] getComponent() {
        return new JPanel[]{leftPanel, rightPanel};
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(refreshButton)) {
            getInventory();
        }
        if (e.getSource().equals(backButton)) {
            MainScreen.leftPanel.removeAll();
            MainScreen.rightPanel.removeAll();
            MainScreen.addComponentsToPanels();
            MainScreen.leftPanel.repaint();
            MainScreen.rightPanel.repaint();
            MainScreen.leftPanel.revalidate();
            MainScreen.rightPanel.revalidate();
        }
    }
}
