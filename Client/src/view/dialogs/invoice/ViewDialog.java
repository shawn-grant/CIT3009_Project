package view.dialogs.invoice;

import client.Client;
import models.Customer;
import models.Employee;
import models.Invoice;
import models.InvoiceItem;
import view.components.RoundedBorder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Malik Heron
 */
public class ViewDialog extends JDialog implements ActionListener {

    private JLabel invoiceNumLabel, billingDateLabel, tenderedLabel, customerLabel, cashierLabel, grandTotalLabel, discountLabel;
    private JTextField invoiceNumField, billingDateField, tenderedField, customerField, cashierField, grandTotalField;
    private JButton closeButton, confirmButton;
    private JScrollPane scrollPane;
    private int invoiceNum = 0;
    private final String[] tableHeaders = {
            "Product Name",
            "Unit Price",
            "Quantity",
            "Total Cost",
    };
    private JTable table;
    private DefaultTableModel model;

    public ViewDialog() {
        initializeComponents();
        addComponentsToWindow();
        registerListeners();
        setWindowProperties();
    }

    public ViewDialog(int invoiceNum) {
        this.invoiceNum = invoiceNum;
        initializeFormComponents();
        getInvoiceInfo();
        getInvoiceItems();
        addComponentsToFormWindow();
        registerFormListeners();
        setFormWindowProperties();
    }

    private void initializeFormComponents() {
        Dimension labelSize = new Dimension(140, 20);
        Dimension fieldSize = new Dimension(250, 35);
        Font labelFont = new Font("arial", Font.BOLD, 14);
        Font fieldFont = new Font("arial", Font.PLAIN, 14);

        //Label properties
        invoiceNumLabel = new JLabel("Invoice Number");
        invoiceNumLabel.setFont(labelFont);
        invoiceNumLabel.setPreferredSize(labelSize);

        billingDateLabel = new JLabel("Billing Date");
        billingDateLabel.setFont(new Font("arial", Font.BOLD, 14));
        billingDateLabel.setPreferredSize(labelSize);

        customerLabel = new JLabel("Customer");
        customerLabel.setFont(labelFont);
        customerLabel.setPreferredSize(labelSize);

        cashierLabel = new JLabel("Cashier");
        cashierLabel.setFont(labelFont);
        cashierLabel.setPreferredSize(labelSize);

        tenderedLabel = new JLabel("Amount Tendered");
        tenderedLabel.setFont(labelFont);
        tenderedLabel.setPreferredSize(labelSize);

        grandTotalLabel = new JLabel("Grand Total");
        grandTotalLabel.setFont(labelFont);
        grandTotalLabel.setPreferredSize(labelSize);

        discountLabel = new JLabel("No customer discount received");
        discountLabel.setFont(labelFont);
        discountLabel.setPreferredSize(new Dimension(400, 30));

        //Field properties
        invoiceNumField = new JTextField();
        invoiceNumField.setFont(fieldFont);
        invoiceNumField.setBorder(null);
        invoiceNumField.setPreferredSize(fieldSize);
        invoiceNumField.setEditable(false);

        billingDateField = new JTextField();
        billingDateField.setFont(fieldFont);
        billingDateField.setBorder(null);
        billingDateField.setPreferredSize(fieldSize);
        billingDateField.setEditable(false);

        customerField = new JTextField();
        customerField.setFont(fieldFont);
        customerField.setBorder(null);
        customerField.setPreferredSize(fieldSize);
        customerField.setEditable(false);

        cashierField = new JTextField();
        cashierField.setFont(fieldFont);
        cashierField.setBorder(null);
        cashierField.setPreferredSize(fieldSize);
        cashierField.setEditable(false);

        tenderedField = new JTextField();
        tenderedField.setFont(fieldFont);
        tenderedField.setBorder(null);
        tenderedField.setPreferredSize(fieldSize);
        tenderedField.setEditable(false);

        grandTotalField = new JTextField();
        grandTotalField.setFont(fieldFont);
        grandTotalField.setBorder(null);
        grandTotalField.setPreferredSize(fieldSize);
        grandTotalField.setEditable(false);

        //Table properties
        model = new DefaultTableModel(tableHeaders, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //Set to not editable
        table.setAutoCreateRowSorter(false); //Enable sorting by columns
        table.getTableHeader().setOpaque(false);//Remove header background
        table.getTableHeader().setBackground(new Color(224, 224, 224));//Setting new background of table headings
        table.setBackground(Color.white);
        table.setForeground(Color.black);

        // scrollPane properties
        scrollPane = new JScrollPane(
                table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scrollPane.setPreferredSize(new Dimension(455, 200));

        //Button properties
        closeButton = new JButton("CLOSE");
        closeButton.setPreferredSize(new Dimension(200, 35));
        closeButton.setForeground(Color.BLUE);
        closeButton.setFont(labelFont);
        closeButton.setFocusPainted(false);
    }

    private void initializeComponents() {
        //Label properties
        invoiceNumLabel = new JLabel("Invoice Number");
        invoiceNumLabel.setFont(new Font("arial", Font.BOLD, 14));
        invoiceNumLabel.setPreferredSize(new Dimension(120, 20));

        //Field properties
        invoiceNumField = new JTextField();
        invoiceNumField.setFont(new Font("times new roman", Font.PLAIN, 14));
        invoiceNumField.setBorder(new RoundedBorder(8));
        invoiceNumField.setPreferredSize(new Dimension(90, 35));

        //Button properties
        confirmButton = new JButton("VIEW");
        confirmButton.setPreferredSize(new Dimension(100, 30));
        confirmButton.setForeground(Color.BLUE);
        confirmButton.setFont(new Font("arial", Font.BOLD, 14));

        //Additional properties
        confirmButton.setFocusPainted(false);
    }

    private void addComponentsToWindow() {
        add(invoiceNumLabel);
        add(invoiceNumField);
        add(confirmButton);
    }

    private void addComponentsToFormWindow() {
        add(invoiceNumLabel);
        add(invoiceNumField);
        add(billingDateLabel);
        add(billingDateField);
        add(customerLabel);
        add(customerField);
        add(cashierLabel);
        add(cashierField);
        add(scrollPane);
        add(discountLabel);
        add(tenderedLabel);
        add(tenderedField);
        add(grandTotalLabel);
        add(grandTotalField);
        add(closeButton);
    }

    private void setWindowProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setTitle("View Invoice");
        setSize(350, 90);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setVisible(true);
    }

    private void setFormWindowProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setTitle("Invoice Details");
        setSize(480, 560);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setVisible(true);
    }

    private void registerListeners() {
        confirmButton.addActionListener(this);
    }

    private void registerFormListeners() {
        closeButton.addActionListener(this);
    }

    private boolean validateFields() {
        return !(invoiceNumField.getText().isEmpty());
    }

    private void getInvoiceInfo() {
        //Get list of invoices
        Client client = new Client();
        client.sendAction("Find Invoice");
        client.sendInvoiceNumber(invoiceNum);
        Invoice invoice = client.receiveFindInvoiceResponse();
        client.closeConnections();

        invoiceNumField.setText(String.valueOf(invoice.getInvoice_number()));
        billingDateField.setText(String.valueOf(invoice.getBillingDate()));
        grandTotalField.setText("$ " + invoice.getTotalCost());
        tenderedField.setText("$ " + invoice.getAmountTendered());

        if (!invoice.getCustomerId().equals("C0000")) {
            //Get customer info
            client = new Client();
            client.sendAction("Find Customer");
            client.sendCustomerId(invoice.getCustomerId());
            Customer customer = client.receiveFindCustomerResponse();
            client.closeConnections();
            //Set value in customerField
            customerField.setText(customer.getFirstName() + " " + customer.getLastName());
            discountLabel.setText("Customer discount received");
        } else {
            customerField.setText("Anonymous");
        }

        //Get employee info
        client = new Client();
        client.sendAction("Find Employee");
        client.sendCustomerId(invoice.getEmployeeId());
        Employee employee = client.receiveFindEmployeeResponse();
        client.closeConnections();
        //Set value in cashierField
        cashierField.setText(employee.getFirstName() + " " + employee.getLastName());
    }

    private void getInvoiceItems() {
        //Get invoice items
        Client client = new Client();
        client.sendAction("View Invoice Item");
        client.sendInvoiceNumber(invoiceNum);
        List<InvoiceItem> invoiceItemList = client.receiveViewInvoiceItemResponse();
        client.closeConnections();

        int count = 0;
        int rowCount = model.getRowCount();
        int counter = 0;

        while (counter < rowCount) {
            model.removeRow(count);
            counter++;
        }

        for (InvoiceItem item : invoiceItemList) {
            model.insertRow(count, new Object[]{
                    item.getId().getItemName(),
                    "$ " + item.getUnitPrice(),
                    item.getQuantity(),
                    "$ " + (item.getQuantity() * item.getUnitPrice())
            });
            count++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(confirmButton)) {
            if (validateFields()) {
                new ViewDialog(Integer.parseInt(invoiceNumField.getText()));
                dispose();
            }
        }
        if (e.getSource().equals(closeButton)) {
            dispose();
        }
    }
}
