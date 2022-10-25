package view.dialogs.customer;

import client.Client;
import models.Customer;
import view.components.RoundedBorder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Malik Heron
 */
public class SearchDialog extends JDialog implements ActionListener {

    private final DefaultTableModel model;
    private JLabel idLabel;
    private JTextField idField;
    private JButton confirmButton;

    public SearchDialog(DefaultTableModel model) {
        this.model = model;
        initializeComponents();
        addComponentsToWindow();
        registerListeners();
        setWindowProperties();
    }

    private void initializeComponents() {
        //Label properties
        idLabel = new JLabel("Customer ID");
        idLabel.setFont(new Font("arial", Font.BOLD, 14));
        idLabel.setPreferredSize(new Dimension(100, 20));

        //Field properties
        idField = new JTextField();
        idField.setFont(new Font("arial", Font.PLAIN, 14));
        idField.setBorder(new RoundedBorder(8));
        idField.setPreferredSize(new Dimension(90, 35));

        //Button properties
        confirmButton = new JButton("SEARCH");
        confirmButton.setPreferredSize(new Dimension(100, 30));
        confirmButton.setForeground(Color.BLUE);
        confirmButton.setFont(new Font("arial", Font.BOLD, 14));

        //Additional properties
        confirmButton.setFocusPainted(false);
    }

    private void addComponentsToWindow() {
        add(idLabel);
        add(idField);
        add(confirmButton);
    }

    private void setWindowProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setTitle("Search Customers");
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
        return !(idField.getText().isEmpty());
    }

    private void setCustomer(Customer customer) {
        int count = 0;
        int rowCount = model.getRowCount();
        int counter = 0;

        while (counter < rowCount) {
            model.removeRow(count);
            counter++;
        }

        model.insertRow(count, new Object[]{
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getDOB(),
                customer.getEmail(),
                customer.getTelephone(),
                customer.getAddress(),
                customer.getMembershipDate(),
                customer.getMembershipExpiryDate()
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(confirmButton)) {
            if (validateFields()) {
                //Request to find a customer
                Client client = new Client();
                client.sendAction("Find Customer");
                client.sendCustomerId(idField.getText());
                Customer customer = client.receiveFindCustomerResponse();
                client.closeConnections();
                if (customer != null) {
                    setCustomer(customer);
                    dispose();
                }
            }
        }
    }
}
