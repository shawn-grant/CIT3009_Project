/*package view.dialogs.invoice;
//============================================================================
//Name        : CheckoutScreen.java
//Author      : Tyrien Gilpin
//Version     : 1
//Description : Invoice search dialog class for finding an invoice by number or product 
//============================================================================

import client.Client;
import models.Invoice;
import view.components.RoundedBorder;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.PatternSyntaxException;

public class SearchDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private JLabel searchLabel;
    private JTextField searchTxtValue;
    private String searchBy;
    private JButton confirmButton;
    private TableRowSorter<TableModel> rowSorter;
    private DefaultTableModel model;
    
    public SearchDialog(JTable table, String searchBy) {
        this.table = table;
        this.searchBy = searchBy;
        this.model = (DefaultTableModel) table.getModel();
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
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
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
        		invoice.getInvoice_number(),
        		invoice.getBillingDate(),
        		invoice.getTotalCost(),
        		invoice.getCustomerId(),
        		invoice.getEmployeeId()
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
               }else if(searchBy.equalsIgnoreCase("Product Code")){
            	   //search invoice table for rows containing search values
            	   if(searchTxtValue.getText().trim().length() == 0) {
            		  rowSorter.setRowFilter(null); 
            	   }else {
            		   try {
						rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" +searchTxtValue.getText().trim()));
            		   } catch (PatternSyntaxException e1) {
            			   System.out.println("Faults in regex pattern");
            		   }
            	   }
               }
            }
        }
    }
    
}*/

package view.dialogs.invoice;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import client.Client;
import models.Invoice;
import view.components.RoundedBorder;

public class SearchDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	 private final DefaultTableModel model;
	    private JLabel invoiceCodeLabel;
	    private JTextField InvoiceField;
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
	    	invoiceCodeLabel = new JLabel("Invoice Code");
	    	invoiceCodeLabel.setFont(new Font("arial", Font.BOLD, 14));
	    	invoiceCodeLabel.setPreferredSize(new Dimension(100, 20));

	        //Field properties
	    	InvoiceField = new JTextField();
	    	InvoiceField.setFont(new Font("times new roman", Font.PLAIN, 14));
	    	InvoiceField.setBorder(new RoundedBorder(8));
	    	InvoiceField.setPreferredSize(new Dimension(90, 35));

	        //Button properties
	        confirmButton = new JButton("SEARCH");
	        confirmButton.setPreferredSize(new Dimension(100, 30));
	        confirmButton.setForeground(Color.BLUE);
	        confirmButton.setFont(new Font("arial", Font.BOLD, 14));

	        //Additional properties
	        confirmButton.setFocusPainted(false);
	    }

	    private void addComponentsToWindow() {
	        add(invoiceCodeLabel);
	        add(InvoiceField);
	        add(confirmButton);
	    }

	    private void setWindowProperties() {
	        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
	        setTitle("Search Invoice");
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
	        return !(InvoiceField.getText().isEmpty());
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
	        		invoice.getInvoice_number(),
	                invoice.getBillingDate(),
	               // invoice.getItemName(),
	                //invoice.getTotalQuantity(),
	                //invoice.getTotalCost(),
	                //invoice.getEmployee(),
	                //invoice.getCustomer()
	        		
	        });
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        if (e.getSource().equals(confirmButton)) {
	            if (validateFields()) {
	                Client client = new Client();
	                client.sendAction("Find Invoice");
	              //  client.sendInvoiceNumber(InvoiceField.getText());
	                Invoice invoice = client.receiveFindInvoiceResponse();
	                client.closeConnections();
	                if (invoice != null) {
	                    setInvoice(invoice);
	                    dispose();
	                }
	            }
	        }
	    }
   

}

