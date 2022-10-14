package view;

import client.Client;
import models.Product;
import view.dialogs.InventoryInsertDialog;
import view.dialogs.InventoryUpdateDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InventoryScreen implements ActionListener {

    private final String[] TableHead = {"Product Code", "Product Name", "Short Description", "Long Description",
            "Items in Stock", "Unit Price"};
    private JButton refreshButton, addButton, updateButton, removeButton, backButton;
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
        addButton = new JButton("Add Product");
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("times new roman", Font.PLAIN, 18));

        updateButton = new JButton("Update Product");
        updateButton.setFocusPainted(false);
        updateButton.setFont(new Font("times new roman", Font.PLAIN, 18));

        removeButton = new JButton("Remove Product");
        removeButton.setFocusPainted(false);
        removeButton.setFont(new Font("times new roman", Font.PLAIN, 18));

        refreshButton = new JButton("Refresh");
        refreshButton.setFocusPainted(false);
        refreshButton.setFont(new Font("times new roman", Font.PLAIN, 18));

        backButton = new JButton("Back");
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("times new roman", Font.PLAIN, 18));

        //Table properties
        model = new DefaultTableModel(TableHead, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //Set to not editable
        table.setAutoCreateRowSorter(true); //Enable sorting by columns

        leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 140, 255));
        leftPanel.setBorder(BorderFactory.createEtchedBorder());
        leftPanel.setFocusable(false);
        leftPanel.setPreferredSize(new Dimension(100, 500));
        GroupLayout leftPanelLayout = new GroupLayout(leftPanel);
        leftPanelLayout.setHorizontalGroup(
                leftPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup()
                                .addContainerGap(100, Short.MAX_VALUE)
                                .addGroup(leftPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(addButton, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(updateButton, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(removeButton, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE))
                                .addGap(93, 93, 93)));
        leftPanelLayout.setVerticalGroup(
                leftPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(leftPanelLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(addButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(updateButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(removeButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(106, Short.MAX_VALUE))
        );
        leftPanel.setLayout(leftPanelLayout);
        leftPanel.setBounds(0, 0, 350, 500);

        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(1, 1, 5, 5));
        rightPanel.setSize(new Dimension(880, 500));
    }

    private void addComponentsToPanel() {
        rightPanel.add(new JScrollPane(table));
        leftPanel.add(addButton);
        leftPanel.add(updateButton);
        leftPanel.add(removeButton);
        leftPanel.add(refreshButton);
        leftPanel.add(backButton);
    }

    private void registerListeners() {
        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        removeButton.addActionListener(this);
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

        if (e.getSource().equals(addButton)) {
            InventoryInsertDialog insertDialog = new InventoryInsertDialog();
            insertDialog.setVisible(true);
        }
        if (e.getSource().equals(updateButton)) {
            InventoryUpdateDialog updateDialog = new InventoryUpdateDialog();
            updateDialog.setVisible(true);
        }
        if (e.getSource().equals(removeButton)) {
            //InventoryRemoveDialog removeDialog = new InventoryRemoveDialog();
            //removeDialog.setVisible(true);
        }
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
