package view.dialogs.staff;

import client.Client;
import models.Employee;
import view.RoundedBorder;
import view.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JLabel idLabel, firstNameLabel, lastNameLabel, dobLabel;
    private JLabel addressLabel, telephoneLabel, emailLabel;
    private JLabel typeLabel, departmentLabel;
    private JTextField idField, firstNameField, lastNameField;
    private JTextField addressField, telephoneField, emailField;
    private DatePicker dobPicker;
    private JButton cancelButton, confirmButton;
    private JComboBox<String> typeBox, departmentBox;
    private final String[] employeeTypes = {"Manger", "Supervisor", "Line Worker"};
    private final String[] departments = {"Management", "Inventory", "Accounting & sales"};

    public UpdateDialog() {
        initializeComponents();
        addComponentsToPanels();
        registerListeners();
        setWindowProperties();
    }

    private void initializeComponents() {
        Dimension labelSize = new Dimension(140, 20);
        Dimension fieldSize = new Dimension(250, 35);
        Font labelFont = new Font("arial", Font.BOLD, 14);
        Font fieldFont = new Font("arial", Font.PLAIN, 14);

        //Label properties
        idLabel = new JLabel("ID");
        idLabel.setFont(labelFont);
        idLabel.setPreferredSize(labelSize);

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setFont(labelFont);
        firstNameLabel.setPreferredSize(labelSize);

        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setFont(labelFont);
        lastNameLabel.setPreferredSize(labelSize);

        dobLabel = new JLabel("Date of Birth");
        dobLabel.setFont(labelFont);
        dobLabel.setPreferredSize(labelSize);

        emailLabel = new JLabel("Email");
        emailLabel.setFont(labelFont);
        emailLabel.setPreferredSize(labelSize);

        addressLabel = new JLabel("Address");
        addressLabel.setFont(labelFont);
        addressLabel.setPreferredSize(labelSize);

        telephoneLabel = new JLabel("Phone #");
        telephoneLabel.setFont(labelFont);
        telephoneLabel.setPreferredSize(labelSize);

        typeLabel = new JLabel("Type");
        typeLabel.setFont(labelFont);
        typeLabel.setPreferredSize(labelSize);

        departmentLabel = new JLabel("Department");
        departmentLabel.setFont(labelFont);
        departmentLabel.setPreferredSize(labelSize);

        //DatePicker properties
        dobPicker = new DatePicker();

        //Field properties
        idField = new JTextField(generateId());
        idField.setBorder(new RoundedBorder(8));
        idField.setPreferredSize(fieldSize);
        idField.setFont(fieldFont);

        firstNameField = new JTextField();
        firstNameField.setBorder(new RoundedBorder(8));
        firstNameField.setPreferredSize(fieldSize);
        firstNameField.setFont(fieldFont);

        lastNameField = new JTextField();
        lastNameField.setBorder(new RoundedBorder(8));
        lastNameField.setPreferredSize(fieldSize);
        lastNameField.setFont(fieldFont);

        emailField = new JTextField();
        emailField.setBorder(new RoundedBorder(8));
        emailField.setPreferredSize(fieldSize);
        emailField.setFont(fieldFont);

        addressField = new JTextField();
        addressField.setBorder(new RoundedBorder(8));
        addressField.setPreferredSize(fieldSize);
        addressField.setFont(fieldFont);

        telephoneField = new JTextField();
        telephoneField.setBorder(new RoundedBorder(8));
        telephoneField.setPreferredSize(fieldSize);
        telephoneField.setFont(fieldFont);

        //Box properties
        typeBox = new JComboBox<>(employeeTypes);
        typeBox.setSelectedItem(null);
        typeBox.setFont(labelFont);
        typeBox.setPreferredSize(fieldSize);
        typeBox.setBorder(new RoundedBorder(8));

        departmentBox = new JComboBox<>(departments);
        departmentBox.setSelectedItem(null);
        departmentBox.setFont(labelFont);
        departmentBox.setPreferredSize(fieldSize);
        departmentBox.setBorder(new RoundedBorder(8));

        //Button properties
        confirmButton = new JButton("UPDATE EMPLOYEE");
        confirmButton.setPreferredSize(new Dimension(200, 30));
        confirmButton.setForeground(Color.BLUE);
        confirmButton.setFont(new Font("arial", Font.BOLD, 14));
        confirmButton.setFocusPainted(false);

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setFocusPainted(false);
    }

    private void addComponentsToPanels() {
        add(idLabel);
        add(idField);
        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(dobLabel);
        add(dobPicker);
        add(emailLabel);
        add(emailField);
        add(addressLabel);
        add(addressField);
        add(telephoneLabel);
        add(telephoneField);
        add(typeLabel);
        add(typeBox);
        add(departmentLabel);
        add(departmentBox);
        add(confirmButton);
        add(cancelButton);
    }

    private void setWindowProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setTitle("Update Employee");
        setSize(450, 485);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }

    private void registerListeners() {
        cancelButton.addActionListener(this);
        confirmButton.addActionListener(this);
    }

    private boolean validateFields() {
        return !(idField.getText().isEmpty() || firstNameField.getText().isEmpty()
                || lastNameField.getText().isEmpty() || addressField.getText().isEmpty() || emailField.getText().isEmpty()
                || employeeTypes[typeBox.getSelectedIndex()].isEmpty() || departments[departmentBox.getSelectedIndex()].isEmpty());
    }

    private String generateId() {
        String id = "E";
        int num = (int) ((Math.random() * (2000 - 100)) + 100);
        return id + num;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(confirmButton)) {
            if (validateFields()) {
                Client client = new Client();
                Employee employee = new Employee(
                        idField.getText(),
                        firstNameField.getText(),
                        lastNameField.getText(),
                        dobPicker.getSelectedDate(),
                        addressField.getText(),
                        telephoneField.getText(),
                        emailField.getText(),
                        employeeTypes[typeBox.getSelectedIndex()],
                        departments[departmentBox.getSelectedIndex()]
                );
                client.sendAction("Update Employee");
                client.sendEmployee(employee);
                client.receiveResponse();
                client.closeConnections();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "One or more fields empty",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }

        if (e.getSource().equals(cancelButton)) {
            dispose();
        }
    }
}
