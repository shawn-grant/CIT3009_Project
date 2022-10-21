package view.dialogs.staff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.Client;
import models.Employee;

public class StaffInsertDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel idLabel, firstNameLabel, lastNameLabel, DOBLabel,addressLabel,telLabel,
    emailLabel,empTypeLabel, deptLabel;
    private JTextField idTextField, firstNameTextfield, lastNameTextField, DOBTextField,
    addressTextField, telTextField,emailTextField;
    private JButton saveButton,cancelButton;
    private JPanel panTop, panBottom;
    private JComboBox <String> empTypeBox, deptBox;

    public StaffInsertDialog() {
        initializeComponents();
        addComponentsToPanels();
        addPanelsToWindow();
        setWindowProperties();
        registerListeners();
    }


    private void initializeComponents() {

    	idLabel = new JLabel("Employee ID ");
        idLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
        idLabel.setPreferredSize(new Dimension(140, 20));
        idTextField = new JTextField(generateId());
        idTextField.setFont(new Font("Aharoni", Font.BOLD, 14));
        idTextField.setPreferredSize(new Dimension(250, 30));

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
        firstNameLabel.setPreferredSize(new Dimension(140, 20));
        firstNameTextfield = new JTextField();
        firstNameTextfield.setFont(new Font("Aharoni", Font.BOLD, 14));
        firstNameTextfield.setPreferredSize(new Dimension(250, 30));

        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
        lastNameLabel.setPreferredSize(new Dimension(140, 20));
        lastNameTextField = new JTextField();
        lastNameTextField.setFont(new Font("Aharoni", Font.BOLD, 14));
        lastNameTextField.setPreferredSize(new Dimension(250, 30));

        DOBLabel = new JLabel("Date of Birth ");
        DOBLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
        DOBLabel.setPreferredSize(new Dimension(140, 20));
        DOBTextField = new JTextField();
        DOBTextField.setFont(new Font("Aharoni", Font.BOLD, 14));
        DOBTextField.setPreferredSize(new Dimension(250, 30));

        addressLabel= new JLabel("Address");
        addressLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
        addressLabel.setPreferredSize(new Dimension(140, 20));
        addressTextField = new JTextField();
        addressTextField.setFont(new Font("Aharoni", Font.BOLD, 14));
        addressTextField.setPreferredSize(new Dimension(250, 30));

        telLabel= new JLabel("Telephone #");
        telLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
        telLabel.setPreferredSize(new Dimension(140, 20));
        telTextField = new JTextField();
        telTextField.setFont(new Font("Aharoni", Font.BOLD, 14));
        telTextField.setPreferredSize(new Dimension(250, 30));

        emailLabel= new JLabel("Email Address");
        emailLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
        emailLabel.setPreferredSize(new Dimension(140, 20));
        emailTextField = new JTextField();
        emailTextField.setFont(new Font("Aharoni", Font.BOLD, 14));
        emailTextField.setPreferredSize(new Dimension(250, 30));

	 	empTypeLabel= new JLabel("Employee Type");
	 	empTypeLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
	 	empTypeLabel.setPreferredSize(new Dimension(140, 20));
	 	String empType[] = {"","Manger", "Supervisor","Line Worker"};
	    empTypeBox = new JComboBox<>(empType);
		empTypeBox.setSelectedItem(null);
		empTypeBox.setFont(new Font("Aharoni", Font.BOLD, 14));
		empTypeBox.setPreferredSize(new Dimension(250, 30));


	 	deptLabel= new JLabel("Department");
	 	deptLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
	 	deptLabel.setPreferredSize(new Dimension(140, 20));
	 	String  dept []= {"","Mangement","Inventory","Accounting & sales"};
	 	deptBox = new JComboBox<>(dept);
	    deptBox.setSelectedItem(null);
	    deptBox.setFont(new Font("Aharoni", Font.BOLD, 14));
	    deptBox.setPreferredSize(new Dimension(250, 30));

	 	cancelButton = new JButton("Cancel");
	 	cancelButton.setForeground(Color.red);
        cancelButton.setFont(new Font("Aharoni", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(125, 30));
        cancelButton.setFocusPainted(false);

        saveButton = new JButton("Save");
        saveButton.setForeground(Color.blue);
        saveButton.setFont(new Font("Aharoni", Font.BOLD, 14));
        saveButton.setPreferredSize(new Dimension(130, 30));
        saveButton.setFocusPainted(false);
        //Panel properties
        panTop = new JPanel();
        panBottom = new JPanel();
       

    }

    private void addComponentsToPanels() {
    	
    	 add(idLabel);
         add(idTextField);

         add(firstNameLabel);
         add(firstNameTextfield);

         add(lastNameLabel);
         add(lastNameTextField);

         add(DOBLabel);
         add(DOBTextField);

         add(addressLabel);
         add(addressTextField);

         add(telLabel);
         add(telTextField);

         add(emailLabel);
         add(emailTextField);

         add(empTypeLabel);
         add(empTypeBox);

         add(deptLabel);
         add(deptBox);

         add(cancelButton);
         add(saveButton);

      //Add components to Bottom panel
        
        panBottom.add(cancelButton);
        panBottom.add(saveButton);

    }

    private void addPanelsToWindow() {
    	add(panTop);
        add(panBottom);
    
    }

    private void setWindowProperties() {
    	setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setTitle("Add New Employee");
        setSize(450, 475);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }

    private void registerListeners() {
        cancelButton.addActionListener(this);
        saveButton.addActionListener(this);
    }


    private boolean validateFields() {
        return !(idTextField.getText().isEmpty() || firstNameTextfield.getText().isEmpty()
                || lastNameTextField.getText().isEmpty() || DOBTextField.getText().isEmpty()
                || addressTextField.getText().isEmpty() ||telTextField.getText().isEmpty()
                ||emailTextField.getText().isEmpty()|| empTypeBox== null|| deptBox ==null);
    }

    private void resetFields() {

        idTextField.setText("");
        firstNameTextfield.setText("");
        lastNameTextField.setText("");
        DOBTextField.setText("");
        telTextField.setText("");
        emailTextField.setText("");
        empTypeBox.setSelectedItem(null);
        deptBox.setSelectedItem(null);
    }
    private String generateId() {
        String id = "emp";
        int num = (int) ((Math.random() * (2000 - 100)) + 100);

        return id + num;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            if (e.getSource().equals(saveButton)) {
                if (validateFields()) {

                    Client client = new Client();

                    Employee emp = new Employee(
                        idTextField.getText(),firstNameTextfield.getText(),
                        lastNameTextField.getText(),new models.Date(),
                        addressTextField.getText(),telTextField.getText(),
                        emailTextField.getText(),
                        empTypeBox.getSelectedItem().toString(),
                        deptBox.getSelectedItem().toString()
                    );

                    client.sendAction("Add Employee");
                    client.sendEmployee(emp);
                    client.receiveResponse();
                    client.closeConnections();

                   //resetFields();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this,"One or more fields empty",
                            "Warning",JOptionPane.WARNING_MESSAGE);
                }
            }

            if (e.getSource().equals(cancelButton)) {
                dispose();
            }

        } catch(HeadlessException e1){
            e1.printStackTrace();
        }

    }

}
