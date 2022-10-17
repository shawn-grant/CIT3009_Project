package view.dialogs.inventory;

import client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryRemoveDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JLabel codeLabel;
    private JTextField codeField;
    private JButton confirmButton;
    private JPanel panel;

    public InventoryRemoveDialog() {
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
        confirmButton = new JButton("Confirm");
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
        setTitle("Remove Product");
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
                Client client = new Client();
                client.sendAction("Remove Product");
                client.sendProductCode(codeField.getText());
                client.receiveResponse();
                client.closeConnections();
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
