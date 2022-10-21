package view.dialogs.staff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import client.Client;
import models.Department;
import models.Employee;
import view.components.RoundedBorder;
import view.components.DatePicker;
import utils.EmailVerifier;
import utils.GenerateID;

/**
 * @author Malik Heron & Tori Horne
 */
public class InsertDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final String[] employeeTypes = {"", "Manager", "Supervisor", "Line Worker"};
    private final String[] departments = {"", "Management", "Inventory", "Accounting & sales"};
    private JLabel idLabel, firstNameLabel, lastNameLabel, dobLabel;
    private JLabel addressLabel, telephoneLabel, emailLabel;
    private JLabel typeLabel, departmentLabel;
    private JTextField idField, firstNameField, lastNameField;
    private JTextField addressField, telephoneField, emailField;
    private DatePicker dobPicker;
    private JButton cancelButton, confirmButton;
    private JComboBox<String> typeBox, departmentBox;

    public InsertDialog() {
        initializeComponents();
        addComponentsToPanels();
        registerListeners();
        setWindowProperties();
    }

    private void initializeComponents() {
        Dimension labelSize = new Dimension(140, 20);
        Dimension fieldSize = new Dimension(250, 35);
        Dimension boxSize = new Dimension(250, 40);
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
        idField = new JTextField(new GenerateID().getID("E"));
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
        typeBox.setFont(fieldFont);
        typeBox.setPreferredSize(boxSize);
        typeBox.setBorder(new RoundedBorder(8));
        typeBox.setFocusable(false);
        typeBox.setSelectedIndex(0);

        departmentBox = new JComboBox<>(departments);
        departmentBox.setSelectedItem(null);
        departmentBox.setFont(fieldFont);
        departmentBox.setPreferredSize(boxSize);
        departmentBox.setBorder(new RoundedBorder(8));
        departmentBox.setFocusable(false);
        departmentBox.setSelectedIndex(0);

        //Button properties
        confirmButton = new JButton("ADD EMPLOYEE");
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
        setTitle("Add New Employee");
        setSize(450, 495);
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
        return !(idField.getText().isEmpty() || firstNameField.getText().isEmpty()
                || lastNameField.getText().isEmpty() || addressField.getText().isEmpty() || emailField.getText().isEmpty()
                || employeeTypes[typeBox.getSelectedIndex()].equals("") || departments[departmentBox.getSelectedIndex()].equals(""));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(confirmButton)) {
            if (validateFields()) {
                if (EmailVerifier.isValid(emailField.getText())) {
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
                            new Department(departments[departmentBox.getSelectedIndex()]).getCode()
                    );
                    client.sendAction("Add Employee");
                    client.sendEmployee(employee);
                    client.receiveResponse();
                    client.closeConnections();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid email address",
                            "Invalid Field",
                            JOptionPane.WARNING_MESSAGE);
                }
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
