/**
 * CustomerInsertDialog.java
 * Popup to add a new customer
 * Author (s): Shawn Grant
*/
package view.dialogs.customer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

import view.RoundedBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class CustomerInsertDialog extends JDialog implements ActionListener {

    private JLabel idLabel, firstNameLabel, lastNameLabel, dobLabel;
    private JLabel addressLabel, telephoneLabel, emailLabel;
    private JTextField idField, firstNameField, lastNameField, addressField, telephoneField, emailField;
    private JFormattedTextField dobField;
    private JButton cancelButton, confirmButton;
    
    public CustomerInsertDialog() {
        initComponents();
        // addComponentsToPanels();
        addPanelsToWindow();
        registerListeners();
        setWindowProperties();
    }

    private void initComponents() {
        idLabel = new JLabel("ID");
        idLabel.setFont(new Font("arial", Font.BOLD, 12));
        idLabel.setPreferredSize(new Dimension(120, 20));

        idField = new JTextField(generateId());
        idField.setBorder(new RoundedBorder(8));
        idField.setPreferredSize(new Dimension(250, 30));

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setFont(new Font("arial", Font.BOLD, 12));
        firstNameLabel.setPreferredSize(new Dimension(120, 20));
        
        firstNameField = new JTextField();
        firstNameField.setBorder(new RoundedBorder(8));
        firstNameField.setPreferredSize(new Dimension(250, 30));
        
        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setFont(new Font("arial", Font.BOLD, 12));
        lastNameLabel.setPreferredSize(new Dimension(120, 20));
        
        lastNameField = new JTextField();
        lastNameField.setBorder(new RoundedBorder(8));
        lastNameField.setPreferredSize(new Dimension(250, 30));

        dobLabel = new JLabel("Date of Birth");
        dobLabel.setFont(new Font("arial", Font.BOLD, 12));
        dobLabel.setPreferredSize(new Dimension(120, 20));
        
        dobField = new JFormattedTextField(new Date());
        dobField.setBorder(new RoundedBorder(8));
        dobField.setPreferredSize(new Dimension(250, 30));
        
        emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("arial", Font.BOLD, 12));
        emailLabel.setPreferredSize(new Dimension(120, 20));
        
        emailField = new JTextField();
        emailField.setBorder(new RoundedBorder(8));
        emailField.setPreferredSize(new Dimension(250, 30));
        
        addressLabel = new JLabel("Address");
        addressLabel.setFont(new Font("arial", Font.BOLD, 12));
        addressLabel.setPreferredSize(new Dimension(120, 20));
        
        addressField = new JTextField();
        addressField.setBorder(new RoundedBorder(8));
        addressField.setPreferredSize(new Dimension(250, 30));
        
        telephoneLabel = new JLabel("Phone #");
        telephoneLabel.setFont(new Font("arial", Font.BOLD, 12));
        telephoneLabel.setPreferredSize(new Dimension(120, 20));
        
        telephoneField = new JTextField();
        telephoneField.setBorder(new RoundedBorder(8));
        telephoneField.setPreferredSize(new Dimension(250, 30));
        
        confirmButton = new JButton("ADD CUSTOMER");
        confirmButton.setPreferredSize(new Dimension(200, 30));
        confirmButton.setForeground(Color.BLUE);
        confirmButton.setFont(new Font("arial", Font.BOLD, 12));

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 30));
    }

    private void addPanelsToWindow() {
        add(idLabel);
        add(idField);
        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(dobLabel);
        add(dobField);
        add(emailLabel);
        add(emailField);
        add(addressLabel);
        add(addressField);
        add(telephoneLabel);
        add(telephoneField);
        add(confirmButton);
        add(cancelButton);
    }
    
    private void setWindowProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setTitle("Add New Customer");
        setSize(420, 330);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setVisible(true);
    }

    private void registerListeners() {
        confirmButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == confirmButton) {

        }
        if(e.getSource() == cancelButton) {
            dispose();
        }
    }

    private String generateId(){
        String id = "C";
        int num = (int)((Math.random() * (4000 - 100)) + 100);

        return id + num;
    }
}
