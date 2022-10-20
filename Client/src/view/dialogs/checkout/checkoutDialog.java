package view.dialogs.checkout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import view.RoundedBorder;

public class checkoutDialog extends JDialog implements ActionListener {//NTS: Move class into seperate file
	private static final long serialVersionUID = 1L;
	private JLabel totalItemsLbl, totalCostlbl, staffIDLabel, customerIDLabel, tenderedLabel;
    private JTextField totalItemsTxtValue, totalCostTxtValue, staffTxtValue, customerTxtValue;
    private JTextField tenderedTxtValue;
    private JButton cashoutButton, cancelButton;
	private DefaultTableModel model;
    
    public checkoutDialog(DefaultTableModel model) {
    	this.model = model;
        initializeComponents();
        addPanelsToWindow();
        registerListeners();
        setWindowProperties();
    }

    private void initializeComponents() {
    	staffIDLabel = new JLabel("Staff ID");
        staffIDLabel.setFont(new Font("arial", Font.BOLD, 12));
        staffIDLabel.setPreferredSize(new Dimension(120, 20));

        staffTxtValue = new JTextField();
        staffTxtValue.setBorder(new RoundedBorder(8));
        staffTxtValue.setPreferredSize(new Dimension(250, 30));
        
        customerIDLabel = new JLabel("Customer ID");
        customerIDLabel.setFont(new Font("arial", Font.BOLD, 12));
        customerIDLabel.setPreferredSize(new Dimension(120, 20));

        customerTxtValue = new JTextField("N/A");
        customerTxtValue.setBorder(new RoundedBorder(8));
        customerTxtValue.setPreferredSize(new Dimension(250, 30));
        customerTxtValue.setText("C000");

        totalItemsLbl = new JLabel("Total Items");
        totalItemsLbl.setFont(new Font("arial", Font.BOLD, 12));
        totalItemsLbl.setPreferredSize(new Dimension(120, 20));

        totalItemsTxtValue = new JTextField();
        totalItemsTxtValue.setBorder(new RoundedBorder(8));
        totalItemsTxtValue.setPreferredSize(new Dimension(250, 30));
        totalItemsTxtValue.setEditable(false);
        totalItemsTxtValue.setText(Integer.toString(getTotalQuantity()));
        
        totalCostlbl = new JLabel("Total Cost");
        totalCostlbl.setFont(new Font("arial", Font.BOLD, 12));
        totalCostlbl.setPreferredSize(new Dimension(120, 20));

        totalCostTxtValue = new JTextField();
        totalCostTxtValue.setBorder(new RoundedBorder(8));
        totalCostTxtValue.setPreferredSize(new Dimension(250, 30));
        totalCostTxtValue.setEditable(false);
        totalCostTxtValue.setText(Float.toString(getTotalCost()));
        
        tenderedLabel = new JLabel("Tendered");
        tenderedLabel.setFont(new Font("arial", Font.BOLD, 12));
        tenderedLabel.setPreferredSize(new Dimension(120, 20));
        
        tenderedTxtValue = new JTextField();
        tenderedTxtValue.setBorder(new RoundedBorder(8));
        tenderedTxtValue.setPreferredSize(new Dimension(250, 30));
        
        cashoutButton = new JButton("Cash Out");
        cashoutButton.setPreferredSize(new Dimension(150, 30));
        cashoutButton.setForeground(Color.BLUE);
        cashoutButton.setFont(new Font("arial", Font.BOLD, 12));
        
        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 30));
    }

    private void addPanelsToWindow() {
    	add(staffIDLabel);
    	add(staffTxtValue);
    	add(customerIDLabel);
    	add(customerTxtValue);
    	add(totalItemsLbl);
    	add(totalItemsTxtValue);
    	add(totalCostlbl);
    	add(totalCostTxtValue);
    	add(tenderedLabel);
    	add(tenderedTxtValue);
    	add(cashoutButton);
    	add(cancelButton);
    }

    private void setWindowProperties() {
    	setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
    	setTitle("Cashout Items");
    	setSize(420, 330);
    	setLocationRelativeTo(null);
    	setResizable(false);
    	setModal(true);
    	setVisible(true);
    }
    
    private boolean validateFields() {
        return !(staffTxtValue.getText().isEmpty() || customerTxtValue.getText().isEmpty()
        		|| tenderedTxtValue.getText().isEmpty());
    }
    
    private int generateInvoiceNum() {
    	int results[] = {};
    	int value = (int) ((Math.random() * (70000 - 100)) + 100);
    	results[0] = value * 1; 
    	results[1] = value + 2;
    	results[2] = value / 2;
    	results[3] = value - 5;
    	
    	return results[new Random().nextInt(results.length)];
    }
   
    public float calculateChange() {
    	float tendered = Float.parseFloat(tenderedTxtValue.getText());
    	float cost = getTotalCost();
    	return tendered - cost;
    }
    
    public void registerListeners() {
        cashoutButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }
    
    public int getTotalQuantity() {
    	int sum = 0;
    	for(int i = 0; i< model.getRowCount(); i++) {
    		sum = sum + Integer.parseInt(model.getValueAt(i, 2).toString());
    	}
    	return sum;
    }
    
    public float getTotalCost(){
    	float sum = 0;
    	for(int i = 0; i< model.getRowCount(); i++) {
    		sum = sum + Float.parseFloat(model.getValueAt(i, 4).toString());
    	}
    	return sum;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cashoutButton) {
        	if(validateFields()){
	        	float change = calculateChange();
	            if(change < 0.0) {
	            	JOptionPane.showMessageDialog(null,  "Insufficient amount tendered", 
	                		"Customer Change", JOptionPane.INFORMATION_MESSAGE);
	            }else {
	            	/*String date = String.valueOf(java.time.LocalDate.now());
		        	LocalDate currentDate = LocalDate.parse(date);
		        	int invoiceNumber = generateInvoiceNum();
		        	Client client = new Client();
		        	client.sendAction("Add Invoice");
		        			Invoice invoice = new Invoice(
			        			invoiceNumber,
			            		new Date(currentDate.getDayOfMonth(), 
			            				 currentDate.getMonthValue(), 
			            				 currentDate.getYear()),
			            		totalCostTxtValue.getText(),
			            		Integer.parseInt(totalItemsTxtValue.getText()),
			            		staffTxtValue.getText().trim(),
			                    customerTxtValue.getText().trim()
			            );
			        	client.sendInvoice(invoice);
			            client.receiveResponse();
			            client.closeConnections();
			            */
			            JOptionPane.showMessageDialog(null,  "Customer should recive $"+ change, 
                		"Customer Change", JOptionPane.INFORMATION_MESSAGE); 
	            	dispose();
	            }
        	}else {
        		JOptionPane.showMessageDialog(null,  "One or more fields empty", 
                		"Missing information", JOptionPane.WARNING_MESSAGE); 
        	}
        	
        }
        if (e.getSource() == cancelButton) {
        	dispose();
        }
        
    }

}