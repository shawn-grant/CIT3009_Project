package view.dialogs.staff;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.Client;
import models.Employee;


public class StaffUpdateDialog extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private JLabel idLabel, firstNameLabel, lastNameLabel, DOBLabel,addressLabel,telLabel,
	emailLabel,empTypeLabel, deptLabel; 
	
	private JTextField idTextField, firstNameTextfield, lastNameTextField, DOBTextField,
						addressTextField, telTextField,emailTextField;
	
	private JButton saveButton,cancelButton;
	private JComboBox empTypebox, deptBox;
    private JPanel panTop, panBottom;
    
    public StaffUpdateDialog() {
    	
    	setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        initializeComponents();
        addComponentsToPanels();
        addPanelsToWindow();
        setWindowProperties();
        registerListeners();
    }
    
    
    private void initializeComponents() {
    	idLabel = new JLabel("Employee ID ");
        idLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
 	   	idTextField = new JTextField();
 	   	idLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
 	   	
 	   	firstNameLabel = new JLabel("First Name");
 	   	firstNameLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
 	   	firstNameTextfield = new JTextField();
 	   	firstNameTextfield.setFont(new Font("Aharoni", Font.BOLD, 14));
 	  
 	   	lastNameLabel = new JLabel("Last Name");
 	   	lastNameLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
 	   	lastNameTextField = new JTextField();
 	   	lastNameTextField.setFont(new Font("Aharoni", Font.BOLD, 14));
 	   	
 	   DOBLabel = new JLabel("Date of Birth ");
 	   DOBLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
 	   DOBTextField = new JTextField();
 	   DOBTextField.setFont(new Font("Aharoni", Font.BOLD, 14));
 	   
 	   addressLabel= new JLabel("Address");
 	   addressLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
 	   addressTextField = new JTextField();
 	   addressTextField.setFont(new Font("Aharoni", Font.BOLD, 14));
 	
 	   telLabel= new JLabel("Telephone #");
 	   telLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
 	   telTextField = new JTextField();
 	   telTextField.setFont(new Font("Aharoni", Font.BOLD, 14));
 	   
 	   emailLabel= new JLabel("Email Address");
 	   emailLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
 	   emailTextField = new JTextField();
 	   emailTextField.setFont(new Font("Aharoni", Font.BOLD, 14));
	 	
 	empTypeLabel= new JLabel("Employee Type");
 	empTypeLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
 	String empType[] = {"","Manger", "Supervisor","Line Worker"};
 	JComboBox<String> empTypeBox = new JComboBox<>(empType);
	empTypeBox.setSelectedItem(null);
 	
 	deptLabel= new JLabel("Department");
 	deptLabel.setFont(new Font("Aharoni", Font.BOLD, 14));
 	String  dept []= {"","Mangement","Inventory","Accounting & sales"};
 	JComboBox<String> deptBox = new JComboBox<>(dept);
	deptBox.setSelectedItem(null);
 	
 	cancelButton = new JButton("Cancel");
    cancelButton.setFont(new Font("Aharoni", Font.BOLD, 14));
    saveButton = new JButton("Save");
    saveButton.setFont(new Font("Aharoni", Font.BOLD, 14));
    
    
    //Panel properties
    panTop = new JPanel();
    panBottom = new JPanel();
    
    saveButton.setFocusPainted(false);
    cancelButton.setFocusPainted(false);
    
    }

    private void addComponentsToPanels() {
    	
    	panTop.setMaximumSize(new Dimension(400, 300));
        panTop.setAlignmentX(Component.CENTER_ALIGNMENT);
        panTop.setLayout(new BoxLayout(panTop, BoxLayout.Y_AXIS));
        panTop.add(idLabel);
        panTop.add(idTextField);
        panTop.add(firstNameLabel);
        panTop.add(firstNameTextfield);
        panTop.add(lastNameLabel);
        panTop.add(lastNameTextField);
        panTop.add(DOBLabel);
        panTop.add(DOBTextField);
        panTop.add(addressLabel);
        panTop.add(addressTextField);
        panTop.add(telLabel);
        panTop.add(telTextField);
        panTop.add(emailLabel);
        panTop.add(emailTextField);
        panTop.add(empTypeLabel);
        panTop.add(empTypebox);
        panTop.add(deptLabel);
        panTop.add(deptBox);
        
        
      //Add components to Bottom panel
        panBottom.setMaximumSize(new Dimension(400, 100));
        panBottom.setAlignmentX(Component.CENTER_ALIGNMENT);
        panBottom.setLayout(new BoxLayout(panBottom, BoxLayout.X_AXIS));
        panBottom.add(cancelButton);
        panBottom.add(saveButton);
        
    }

    private void addPanelsToWindow() {
    	add(panTop);
        add(panBottom);
    }

    private void setWindowProperties() {
    	  setTitle("Add New Employee");
          setSize(500, 400);
          setLocationRelativeTo(null);// maybe 153
         // setVisible(true);
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
                ||emailTextField.getText().isEmpty()|| empTypebox== null|| deptBox ==null);
    }

    private void resetFields() {
    	
    	idTextField.setText("");
    	firstNameTextfield.setText("");
    	lastNameTextField.setText("");
    	DOBTextField.setText("");
    	telTextField.setText("");
    	emailTextField.setText("");
    	empTypebox.setSelectedItem(null);
    	deptBox.setSelectedItem(null);
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		
	   try {
		if (e.getSource().equals(saveButton)) {
		        if (validateFields()) {
		        	
		            Client client = new Client();
		            client.sendAction("update Employee");
		            
		            Employee emp = new Employee(
		            		idTextField.getText(),firstNameTextfield.getText(),
		            		lastNameTextField.getText(),new models.Date(),
		            		addressTextField.getText(),telTextField.getText(),
		            		emailTextField.getText(),
		            		empTypebox.getSelectedItem().toString(),
		            		deptBox.getSelectedItem().toString()
		            		);
		            
		            client.sendEmployee(emp);
		            client.receiveResponse();
		            client.closeConnections();
		            
		            resetFields();
		            dispose();
		        } else {
		            JOptionPane.showMessageDialog(this,"One or more fields empty",
		                    "Warning",JOptionPane.WARNING_MESSAGE);
		        }
		    }

		    if (e.getSource().equals(cancelButton)) {
		        dispose();
		    }
	} catch (HeadlessException e1) {
		e1.printStackTrace();
	}
    }	
    
}
