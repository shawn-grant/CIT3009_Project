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

/**
 * @author Malik Heron
 */
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
		// Label properties
		invoiceCodeLabel = new JLabel("Invoice Number");
		invoiceCodeLabel.setFont(new Font("arial", Font.BOLD, 14));
		invoiceCodeLabel.setPreferredSize(new Dimension(120, 20));

		// Field properties
		InvoiceField = new JTextField();
		InvoiceField.setFont(new Font("times new roman", Font.PLAIN, 14));
		InvoiceField.setBorder(new RoundedBorder(8));
		InvoiceField.setPreferredSize(new Dimension(90, 35));

		// Button properties
		confirmButton = new JButton("SEARCH");
		confirmButton.setPreferredSize(new Dimension(100, 30));
		confirmButton.setForeground(Color.BLUE);
		confirmButton.setFont(new Font("arial", Font.BOLD, 14));

		// Additional properties
		confirmButton.setFocusPainted(false);
	}

	private void addComponentsToWindow() {
		add(invoiceCodeLabel);
		add(InvoiceField);
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

		model.insertRow(count, new Object[] { invoice.getInvoice_number(), invoice.getBillingDate(),
				invoice.getInvoice_number(),
				invoice.getBillingDate(),
				invoice.getTotalCost(),
				invoice.getAmountTendered(),
				invoice.getEmployeeId(),
				invoice.getCustomerId()
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(confirmButton)) {
			if (validateFields()) {
				Client client = new Client();
				client.sendAction("Find Invoice");
				client.sendInvoiceNumber(Integer.parseInt(InvoiceField.getText()));
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
