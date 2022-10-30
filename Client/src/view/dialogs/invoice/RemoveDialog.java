package view.dialogs.invoice;

import client.Client;
import view.components.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Malik Heron
 */
public class RemoveDialog extends JDialog implements ActionListener {

    private JLabel invoiceNumLabel;
    private JTextField invoiceNumField;
    private JButton confirmButton;

    public RemoveDialog() {
        initializeComponents();
        addComponentsToWindow();
        registerListeners();
        setWindowProperties();
    }

    private void initializeComponents() {
        //Label properties
        invoiceNumLabel = new JLabel("Invoice Number");
        invoiceNumLabel.setFont(new Font("arial", Font.BOLD, 14));
        invoiceNumLabel.setPreferredSize(new Dimension(120, 20));

        //Field properties
        invoiceNumField = new JTextField();
        invoiceNumField.setFont(new Font("times new roman", Font.PLAIN, 14));
        invoiceNumField.setBorder(new RoundedBorder(8));
        invoiceNumField.setPreferredSize(new Dimension(90, 35));

        //Button properties
        confirmButton = new JButton("REMOVE");
        confirmButton.setPreferredSize(new Dimension(100, 30));
        confirmButton.setForeground(Color.BLUE);
        confirmButton.setFont(new Font("arial", Font.BOLD, 14));

        //Additional properties
        confirmButton.setFocusPainted(false);
    }

    private void addComponentsToWindow() {
        add(invoiceNumLabel);
        add(invoiceNumField);
        add(confirmButton);
    }

    private void setWindowProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setTitle("Remove Invoice");
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
        return !(invoiceNumField.getText().isEmpty());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(confirmButton)) {
            if (validateFields()) {
                //Request to remove a product
                Client client = new Client();
                client.sendAction("Remove Invoice");
                client.sendProductCode(invoiceNumField.getText());
                client.receiveResponse();
                client.closeConnections();
                dispose();
            }
        }
    }
}
