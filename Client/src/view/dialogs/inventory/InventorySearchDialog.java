package view.dialogs.inventory;

import client.Client;
import models.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventorySearchDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JLabel codeLabel;
    private JTextField codeField;
    private JButton confirmButton;
    private JPanel panel;
    private final Client client;

    public InventorySearchDialog(Client client) {
        this.client = client;
        setLayout(new FlowLayout(FlowLayout.TRAILING));
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

        //Field properties
        codeField = new JTextField();
        codeField.setFont(new Font("times new roman", Font.PLAIN, 16));
        codeField.setPreferredSize(new Dimension(70, 30));

        //Button properties
        confirmButton = new JButton("Search");
        confirmButton.setFont(new Font("times new roman", Font.PLAIN, 16));

        //Panel properties
        panel = new JPanel();

        //Additional properties
        codeField.setSize(80, 25);
        confirmButton.setFocusPainted(false);
    }

    private void addComponentsToPanels() {
        panel.add(codeLabel);
        panel.add(codeField);
        panel.add(confirmButton);
    }

    private void addPanelsToWindow() {
        add(panel);
    }

    private void setWindowProperties() {
        setTitle("Search Inventory");
        setSize(270, 80);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }

    private void registerListeners() {
        confirmButton.addActionListener(this);
    }

    private boolean validateFields() {
        return !(codeField.getText().isEmpty());
    }

    private void resetFields() {
        codeField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(confirmButton)) {
            if (validateFields()) {
                client.sendAction("Find Product");
                client.sendProductCode(codeField.getText());
                resetFields();
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "One or more fields empty",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
