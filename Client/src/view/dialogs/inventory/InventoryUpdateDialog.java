package view.dialogs.inventory;

import client.Client;
import models.Product;
import view.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Malik Heron
 */
public class InventoryUpdateDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JLabel nameLabel, codeLabel, shortDescLabel, longDescLabel, inStockLabel, unitPriceLabel;
    private JTextField nameField, codeField, shortDescField, longDescField, inStockField, unitPriceField;
    private JButton cancelButton, confirmButton;

    public InventoryUpdateDialog() {
        initializeComponents();
        addComponentsToWindow();
        registerListeners();
        setWindowProperties();
    }

    private void initializeComponents() {
        //Label properties
        codeLabel = new JLabel("Product Code");
        codeLabel.setFont(new Font("arial", Font.BOLD, 14));
        codeLabel.setPreferredSize(new Dimension(140, 20));

        nameLabel = new JLabel("Product Name");
        nameLabel.setFont(new Font("arial", Font.BOLD, 14));
        nameLabel.setPreferredSize(new Dimension(140, 20));

        shortDescLabel = new JLabel("Short Description");
        shortDescLabel.setFont(new Font("arial", Font.BOLD, 14));
        shortDescLabel.setPreferredSize(new Dimension(140, 20));

        longDescLabel = new JLabel("Long Description");
        longDescLabel.setFont(new Font("arial", Font.BOLD, 14));
        longDescLabel.setPreferredSize(new Dimension(140, 20));

        inStockLabel = new JLabel("Items in Stock");
        inStockLabel.setFont(new Font("arial", Font.BOLD, 14));
        inStockLabel.setPreferredSize(new Dimension(140, 20));

        unitPriceLabel = new JLabel("Unit Price");
        unitPriceLabel.setFont(new Font("arial", Font.BOLD, 14));
        unitPriceLabel.setPreferredSize(new Dimension(140, 20));

        //Field properties
        codeField = new JTextField();
        codeField.setFont(new Font("times new roman", Font.PLAIN, 14));
        codeField.setBorder(new RoundedBorder(8));
        codeField.setPreferredSize(new Dimension(250, 30));

        nameField = new JTextField();
        nameField.setFont(new Font("times new roman", Font.PLAIN, 14));
        nameField.setBorder(new RoundedBorder(8));
        nameField.setPreferredSize(new Dimension(250, 30));

        shortDescField = new JTextField();
        shortDescField.setFont(new Font("times new roman", Font.PLAIN, 14));
        shortDescField.setBorder(new RoundedBorder(8));
        shortDescField.setPreferredSize(new Dimension(250, 30));

        longDescField = new JTextField();
        longDescField.setFont(new Font("times new roman", Font.PLAIN, 14));
        longDescField.setBorder(new RoundedBorder(8));
        longDescField.setPreferredSize(new Dimension(250, 30));

        inStockField = new JTextField();
        inStockField.setFont(new Font("times new roman", Font.PLAIN, 14));
        inStockField.setBorder(new RoundedBorder(8));
        inStockField.setPreferredSize(new Dimension(250, 30));

        unitPriceField = new JTextField();
        unitPriceField.setFont(new Font("times new roman", Font.PLAIN, 14));
        unitPriceField.setBorder(new RoundedBorder(8));
        unitPriceField.setPreferredSize(new Dimension(250, 30));

        //Button properties
        confirmButton = new JButton("UPDATE PRODUCT");
        confirmButton.setPreferredSize(new Dimension(200, 30));
        confirmButton.setForeground(Color.BLUE);
        confirmButton.setFont(new Font("arial", Font.BOLD, 14));

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setFont(new Font("arial", Font.BOLD, 14));

        //Additional properties
        confirmButton.setFocusPainted(false);
        cancelButton.setFocusPainted(false);
    }

    private void addComponentsToWindow() {
        add(codeLabel);
        add(codeField);
        add(nameLabel);
        add(nameField);
        add(shortDescLabel);
        add(shortDescField);
        add(longDescLabel);
        add(longDescField);
        add(inStockLabel);
        add(inStockField);
        add(unitPriceLabel);
        add(unitPriceField);
        add(confirmButton);
        add(cancelButton);
    }

    private void setWindowProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setTitle("Update Product");
        setSize(430, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setVisible(true);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(confirmButton)) {
            if (validateFields()) {
                Client client = new Client();
                client.sendAction("Update Product");
                Product product = new Product(
                        codeField.getText(),
                        nameField.getText(),
                        shortDescField.getText(),
                        longDescField.getText(),
                        Integer.parseInt(inStockField.getText()),
                        Float.parseFloat(unitPriceField.getText())
                );
                client.sendProduct(product);
                client.receiveResponse();
                client.closeConnections();
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "One or more fields empty",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }

        if (e.getSource().equals(cancelButton)) {
            dispose();
        }
    }

}
