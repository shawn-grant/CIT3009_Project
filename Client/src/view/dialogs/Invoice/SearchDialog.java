package view.dialogs.Invoice;
//============================================================================
//Name        : CheckoutScreen.java
//Author      : Tyrien Gilpin
//Version     : 1
//Description : Invoice search dialog class for finding an invoice by number or product 
//============================================================================

import client.Client;
import models.Invoice;
import models.Product;
import view.components.RoundedBorder;

import javax.swing.table.DefaultTableModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private final DefaultTableModel model;
    private JLabel searchLabel;
    private JTextField searchTxtValue;
    private String searchBy;
    private JButton confirmButton;

    public SearchDialog(DefaultTableModel model, String searchBy) {
        this.model = model;
        this.searchBy = searchBy;
        initializeComponents();
        addComponentsToWindow();
        registerListeners();
        setWindowProperties();
    }

    private void initializeComponents() {
        
    	String label = "";
    	if(searchBy.equalsIgnoreCase("Invoice #")){
    		label = "Invoice Number";
    	}else if(searchBy.equalsIgnoreCase("Product Code")){
    		label = "Product Code";
    	}
    	
    	//Label properties
        searchLabel = new JLabel(label);
        searchLabel.setFont(new Font("arial", Font.BOLD, 14));
        searchLabel.setPreferredSize(new Dimension(100, 20));

        //Field properties
        searchTxtValue = new JTextField();
        searchTxtValue.setFont(new Font("times new roman", Font.PLAIN, 14));
        searchTxtValue.setBorder(new RoundedBorder(8));
        searchTxtValue.setPreferredSize(new Dimension(90, 35));

        //Button properties
        confirmButton = new JButton("SEARCH");
        confirmButton.setPreferredSize(new Dimension(100, 30));
        confirmButton.setForeground(Color.BLUE);
        confirmButton.setFont(new Font("arial", Font.BOLD, 14));

        //Additional properties
        confirmButton.setFocusPainted(false);
    }

    private void addComponentsToWindow() {
        add(searchLabel);
        add(searchTxtValue);
        add(confirmButton);
    }

    private void setWindowProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setTitle("Search Invoices");
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
        return !(searchTxtValue.getText().isEmpty());
    }

    private void setInvoice(Invoice invoice) {
        int count = 0;
        int rowCount = model.getRowCount();
        int counter = 0;

        while (counter < rowCount) {
            model.removeRow(count);
            counter++;
        }

        model.insertRow(count, new Object[]{
        		invoice.getInvoiceNumber(),
        		invoice.getBillingDate(),
        		invoice.getItemName(),
        		invoice.getQuantity(),
        		invoice.getTotalCost(),
        		invoice.getEmployee(),
        		invoice.getCustomer()
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(confirmButton)) {
            if (validateFields()) {
	            if(searchBy.equalsIgnoreCase("Invoice #")){
	            	Client client = new Client();
	                client.sendAction("Find Invoice");
	                client.sendProductCode(searchTxtValue.getText());
	                Invoice invoice = client.receiveFindInvoiceResponse();
	                client.closeConnections();
	                if (invoice != null) {
	                    setInvoice(invoice);
	                    dispose();
	                }
               }else {
            	   //search invoice table for rows containing search values
               }
            }
        }
    }
    
}
