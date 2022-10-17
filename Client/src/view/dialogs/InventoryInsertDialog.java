package view.dialogs;

import client.Client;
import models.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryInsertDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JLabel nameLabel, codeLabel, shortDescLabel, longDescLabel, inStockLabel, unitPriceLabel;
    private JTextField nameField, codeField, shortDescField, longDescField, inStockField, unitPriceField;
    private JButton cancelButton, confirmButton;
    private JPanel panTop, panBottom;

    public InventoryInsertDialog() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        initializeComponents();
        addComponentsToPanels();
        addPanelsToWindow();
        setWindowProperties();
        registerListeners();
    }

    private void initializeComponents() {
        //Label properties
        codeLabel = new JLabel("Product Code");
        codeLabel.setFont(new Font("times new roman", Font.PLAIN, 16));
        nameLabel = new JLabel("Product Name");
        nameLabel.setFont(new Font("times new roman", Font.PLAIN, 16));
        shortDescLabel = new JLabel("Short Description");
        shortDescLabel.setFont(new Font("times new roman", Font.PLAIN, 16));
        longDescLabel = new JLabel("Long Description");
        longDescLabel.setFont(new Font("times new roman", Font.PLAIN, 16));
        inStockLabel = new JLabel("Items in Stock");
        inStockLabel.setFont(new Font("times new roman", Font.PLAIN, 16));
        unitPriceLabel = new JLabel("Unit Price");
        unitPriceLabel.setFont(new Font("times new roman", Font.PLAIN, 16));

        //Field properties
        codeField = new JTextField();
        codeField.setFont(new Font("times new roman", Font.PLAIN, 16));
        nameField = new JTextField();
        nameField.setFont(new Font("times new roman", Font.PLAIN, 16));
        shortDescField = new JTextField();
        shortDescField.setFont(new Font("times new roman", Font.PLAIN, 16));
        longDescField = new JTextField();
        longDescField.setFont(new Font("times new roman", Font.PLAIN, 16));
        inStockField = new JTextField();
        inStockField.setFont(new Font("times new roman", Font.PLAIN, 16));
        unitPriceField = new JTextField();
        unitPriceField.setFont(new Font("times new roman", Font.PLAIN, 16));
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("times new roman", Font.PLAIN, 16));
        confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font("times new roman", Font.PLAIN, 16));

        //Panel properties
        panTop = new JPanel();
        panBottom = new JPanel();

        //Additional properties
        codeField.setSize(50, 25);
        confirmButton.setFocusPainted(false);
        cancelButton.setFocusPainted(false);
    }

    private void addComponentsToPanels() {
        //Add components to Top panel
        panTop.setMaximumSize(new Dimension(400, 300));
        panTop.setAlignmentX(Component.CENTER_ALIGNMENT);
        panTop.setLayout(new BoxLayout(panTop, BoxLayout.Y_AXIS));
        panTop.add(codeLabel);
        panTop.add(codeField);
        panTop.add(nameLabel);
        panTop.add(nameField);
        panTop.add(shortDescLabel);
        panTop.add(shortDescField);
        panTop.add(longDescLabel);
        panTop.add(longDescField);
        panTop.add(inStockLabel);
        panTop.add(inStockField);
        panTop.add(unitPriceLabel);
        panTop.add(unitPriceField);

        //Add components to Bottom panel
        panBottom.setMaximumSize(new Dimension(400, 100));
        panBottom.setAlignmentX(Component.CENTER_ALIGNMENT);
        panBottom.setLayout(new BoxLayout(panBottom, BoxLayout.X_AXIS));
        panBottom.add(cancelButton);
        panBottom.add(confirmButton);
    }

    private void addPanelsToWindow() {
        add(panTop);
        add(panBottom);
    }

    private void setWindowProperties() {
        setTitle("Add New Product");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }

    private void registerListeners() {
        cancelButton.addActionListener(this);
        confirmButton.addActionListener(this);
    }

    private boolean validateFields() {
        return !(codeField.getText().isEmpty() || nameField.getText().isEmpty()
                || shortDescField.getText().isEmpty() || longDescField.getText().isEmpty()
                || inStockField.getText().isEmpty() || unitPriceField.getText().isEmpty());
    }

    private void resetFields() {
        codeField.setText("");
        nameField.setText("");
        shortDescField.setText("");
        longDescField.setText("");
        inStockField.setText("");
        unitPriceField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(confirmButton)) {
            if (validateFields()) {
                Client client = new Client();
                client.sendAction("Add Product");
                Product product = new Product(codeField.getText(), nameField.getText(), shortDescField.getText(),
                        longDescField.getText(), Integer.parseInt(inStockField.getText()),
                        Float.parseFloat(unitPriceField.getText()));
                client.sendProduct(product);
                client.receiveResponse();
                client.closeConnections();
                resetFields();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "One or more fields empty",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        }

        if (e.getSource().equals(cancelButton)) {
            dispose();
        }
    }
}
