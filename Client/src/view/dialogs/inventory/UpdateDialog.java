package view.dialogs.inventory;

import client.Client;
import models.Customer;
import models.Product;
import view.RoundedBorder;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Malik Heron
 */
public class UpdateDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JLabel nameLabel, codeLabel, shortDescLabel, longDescLabel, inStockLabel, unitPriceLabel;
    private JTextField nameField, codeField, shortDescField, longDescField, inStockField, unitPriceField;
    private JButton cancelButton, confirmButton;
    private Product product;

    public UpdateDialog() {
        initializeComponents();
        addComponentsToWindow();
        registerListeners();
        setWindowProperties();
    }

    // for selected customer
    public UpdateDialog(Product product) {
        this.product = product;

        initializeComponents();
        setComponentValues();
        addComponentsToWindow();
        registerListeners();
        setWindowProperties();
    }

    private void initializeComponents() {
        Dimension labelSize = new Dimension(140, 20);
        Dimension fieldSize = new Dimension(250, 35);
        Font labelFont = new Font("arial", Font.BOLD, 14);
        Font fieldFont = new Font("arial", Font.PLAIN, 14);

        //Label properties
        codeLabel = new JLabel("Product Code");
        codeLabel.setFont(new Font("arial", Font.BOLD, 14));
        codeLabel.setPreferredSize(labelSize);

        nameLabel = new JLabel("Product Name");
        nameLabel.setFont(labelFont);
        nameLabel.setPreferredSize(labelSize);

        shortDescLabel = new JLabel("Short Description");
        shortDescLabel.setFont(labelFont);
        shortDescLabel.setPreferredSize(labelSize);

        longDescLabel = new JLabel("Long Description");
        longDescLabel.setFont(labelFont);
        longDescLabel.setPreferredSize(labelSize);

        inStockLabel = new JLabel("Items in Stock");
        inStockLabel.setFont(labelFont);
        inStockLabel.setPreferredSize(labelSize);

        unitPriceLabel = new JLabel("Unit Price");
        unitPriceLabel.setFont(labelFont);
        unitPriceLabel.setPreferredSize(labelSize);

        //Field properties
        codeField = new JTextField();
        codeField.setFont(fieldFont);
        codeField.setBorder(new RoundedBorder(8));
        codeField.setPreferredSize(fieldSize);

        nameField = new JTextField();
        nameField.setFont(fieldFont);
        nameField.setBorder(new RoundedBorder(8));
        nameField.setPreferredSize(fieldSize);

        shortDescField = new JTextField();
        shortDescField.setFont(fieldFont);
        shortDescField.setBorder(new RoundedBorder(8));
        shortDescField.setPreferredSize(fieldSize);

        longDescField = new JTextField();
        longDescField.setFont(fieldFont);
        longDescField.setBorder(new RoundedBorder(8));
        longDescField.setPreferredSize(fieldSize);

        inStockField = new JTextField();
        inStockField.setFont(fieldFont);
        inStockField.setBorder(new RoundedBorder(8));
        inStockField.setPreferredSize(fieldSize);

        unitPriceField = new JTextField();
        unitPriceField.setFont(fieldFont);
        unitPriceField.setBorder(new RoundedBorder(8));
        unitPriceField.setPreferredSize(fieldSize);

        //Button properties
        confirmButton = new JButton("UPDATE PRODUCT");
        confirmButton.setPreferredSize(new Dimension(200, 30));
        confirmButton.setForeground(Color.BLUE);
        confirmButton.setFont(labelFont);
        confirmButton.setFocusPainted(false);

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setFont(labelFont);
        cancelButton.setFocusPainted(false);
    }

    private void setComponentValues() {
        codeField.setText(product.getCode());
        nameField.setText(product.getName());
        shortDescField.setText(product.getShortDescription());
        longDescField.setText(product.getLongDescription());
        inStockField.setText(String.valueOf(product.getItemInStock()));
        unitPriceField.setText(String.valueOf(product.getUnitPrice()));
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
        setSize(430, 320);
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
