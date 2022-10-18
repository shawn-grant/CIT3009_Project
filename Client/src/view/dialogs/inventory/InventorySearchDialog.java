package view.dialogs.inventory;

import client.Client;
import view.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventorySearchDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private final Client client;
    private JLabel codeLabel;
    private JTextField codeField;
    private JButton confirmButton;

    public InventorySearchDialog(Client client) {
        this.client = client;
        initializeComponents();
        addComponentsToWindow();
        registerListeners();
        setWindowProperties();
    }

    private void initializeComponents() {
        //Label properties
        codeLabel = new JLabel("Product Code");
        codeLabel.setFont(new Font("arial", Font.BOLD, 12));
        codeLabel.setPreferredSize(new Dimension(100, 20));

        //Field properties
        codeField = new JTextField();
        codeField.setFont(new Font("times new roman", Font.PLAIN, 16));
        codeField.setBorder(new RoundedBorder(8));
        codeField.setPreferredSize(new Dimension(90, 30));

        //Button properties
        confirmButton = new JButton("SEARCH");
        confirmButton.setPreferredSize(new Dimension(100, 30));
        confirmButton.setForeground(Color.BLUE);
        confirmButton.setFont(new Font("arial", Font.BOLD, 12));

        //Additional properties
        confirmButton.setFocusPainted(false);
    }

    private void addComponentsToWindow() {
        add(codeLabel);
        add(codeField);
        add(confirmButton);
    }

    private void setWindowProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setTitle("Search Inventory");
        setSize(350, 90);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setVisible(true);
    }

    private void registerListeners() {
        confirmButton.addActionListener(this);
    }

    private boolean validateFields() {
        return !(codeField.getText().isEmpty());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(confirmButton)) {
            if (validateFields()) {
                client.sendAction("Find Product");
                client.sendProductCode(codeField.getText());
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
    }
}
