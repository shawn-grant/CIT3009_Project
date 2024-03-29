package view.dialogs.invoice;

import client.Client;
import models.Customer;
import models.Employee;
import models.Invoice;
import models.Purchase;
import view.components.RoundedBorder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author Malik Heron
 */
public class ViewDialog extends JDialog implements ActionListener {

    private JLabel invoiceNumLabel, billingDateLabel, tenderedLabel, customerLabel, cashierLabel, grandTotalLabel, discountLabel;
    private JTextField invoiceNumField, billingDateField, tenderedField, customerField, cashierField, grandTotalField;
    private JButton closeButton, confirmButton, printButton;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private Invoice invoice;
    private int invoiceNum = 0;
    private final String[] tableHeaders = {
            "Product Name",
            "Unit Price",
            "Quantity",
            "Total Cost",
    };
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
        JTable table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //Set to not editable
        table.setAutoCreateRowSorter(false); //Enable sorting by columns
        table.getTableHeader().setOpaque(false);//Remove header background
        table.getTableHeader().setBackground(new Color(224, 224, 224));//Setting new background of table headings
        table.setBackground(Color.white);
        table.setForeground(Color.black);

        //TextArea
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(490, 400));
        textArea.setLineWrap(true);
        textArea.setFont(new Font("arial", Font.PLAIN, 14));

        // scrollPane properties
        scrollPane = new JScrollPane(
                table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scrollPane.setPreferredSize(new Dimension(455, 200));

        //Button properties
        closeButton = new JButton("CLOSE");
        closeButton.setPreferredSize(new Dimension(220, 35));
        closeButton.setForeground(Color.RED);
        closeButton.setFont(labelFont);
        closeButton.setFocusPainted(false);

        printButton = new JButton("PRINT");
        printButton.setPreferredSize(new Dimension(220, 35));
        printButton.setForeground(Color.BLUE);
        printButton.setFont(labelFont);
        printButton.setFocusPainted(false);
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
        add(printButton);
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
        printButton.addActionListener(this);
    }

    private boolean validateFields() {
        return !(invoiceNumField.getText().isEmpty());
    }

    private void getInvoiceInfo() {
        //Get list of invoices
        Client client = new Client();
        client.sendAction("Find Invoice");
        client.sendInvoiceNumber(invoiceNum);
        invoice = client.receiveFindInvoiceResponse();
        client.closeConnections();

        invoiceNumField.setText(String.valueOf(invoice.getInvoice_number()));
        billingDateField.setText(String.valueOf(invoice.getBillingDate()));
        grandTotalField.setText("$ " + invoice.getTotalCost());
        tenderedField.setText("$ " + invoice.getAmountTendered());

        Customer customer = null;
        if (!invoice.getCustomerId().equals("C0000")) {
            //Get customer info
            client = new Client();
            client.sendAction("Find Customer");
            client.sendCustomerId(invoice.getCustomerId());
            customer = client.receiveFindCustomerResponse();
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

        //Add to textArea
        textArea.append(
                "121 Old Hope Road\n" +
                        "Kingston 6, Jamaica\n" +
                        "Tel: 993-3454\n\n"
        );
        textArea.append("Invoice Number: " + invoiceNum + "\n\n");
        textArea.append("Billing Date: " + invoice.getBillingDate() + "\n\n");

        //Check if customer exists
        if (customer != null) {
            textArea.append("Issued to: " + customer.getFirstName() + " " + customer.getLastName() + "\n");
            textArea.append("Address: " + customer.getAddress() + "\n\n");
        } else {
            textArea.append("Issued to: Anonymous\n\n");
        }

        //Add employee
        textArea.append("Cashier: " + employee.getFirstName() + " " + employee.getLastName() + "\n\n");
    }

    private void getInvoiceItems() {
        //Get invoice items
        Client client = new Client();
        client.sendAction("View Purchase");
        client.sendInvoiceNumber(invoiceNum);
        List<Purchase> purchaseList = client.receiveViewInvoiceItemResponse();
        client.closeConnections();

        int count = 0;
        int rowCount = model.getRowCount();
        int counter = 0;
        float itemCost;
        float subTotal = 0f;

        while (counter < rowCount) {
            model.removeRow(count);
            counter++;
        }

        //Set headers
        textArea.append("Product Name\t     Unit Price\tQuantity\tTotal Cost\n\n");

        for (Purchase item : purchaseList) {
            //Calculate item cost
            itemCost = (item.getQuantity() * item.getUnitPrice());

            //Add to table
            model.insertRow(count, new Object[]{
                    item.getId().getItemName(),
                    "$ " + item.getUnitPrice(),
                    item.getQuantity(),
                    "$ " + itemCost
            });
            //Add to TextArea
            textArea.append(
                    item.getId().getItemName() + "\t     " +
                            "$ " + item.getUnitPrice() + "\t" +
                            item.getQuantity() + "\t" +
                            "$ " + itemCost + "\n"
            );
            subTotal += itemCost;
            count++;
        }

        //Add tax and total info to textArea
        textArea.append("\n\nSub-Total: $ " + subTotal + "\n\n");

        if (discountLabel.getText().equals("Customer discount received")) {
            float customerDiscount = (float) (subTotal * 0.10);
            float gct = (float) ((subTotal - customerDiscount) * 0.15);

            textArea.append("Customer discount: $ " + customerDiscount + "\n\n");
            textArea.append("GCT: $ " + gct + "\n\n");
        } else {
            textArea.append("GCT: $ " + (subTotal * 0.15) + "\n\n");
        }

        textArea.append("Total Cost: $ " + invoice.getTotalCost());
        textArea.append("\n\nAmount paid: $ " + invoice.getAmountTendered());
    }

    private void printInvoice() {
        try {
            // add spacing between content and header
            textArea.insert("\n\n", 0);
            // trigger system print dialog
            textArea.print(new MessageFormat("INVOICE | JAN'S WHOLESALE & RETAIL"), null);
        } catch (PrinterException e) {
            e.printStackTrace();
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
        if (e.getSource().equals(printButton)) {
            printInvoice();
        }
    }
}
