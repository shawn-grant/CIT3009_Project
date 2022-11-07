package view.screens;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import client.Client;
import models.Invoice;
import view.dialogs.invoice.RemoveDialog;
import view.dialogs.invoice.SearchDialog;
import view.dialogs.invoice.ViewDialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * View for displaying Invoice info
 * @author Tori Horne
 */
public class InvoiceScreen extends BaseScreen implements ActionListener {

    private final GridBagConstraints gbc = new GridBagConstraints();
    private JTextArea textArea;
    private JButton viewButton;
    private JPanel mainContent, panTop;

    private final String[] tableHeaders = {
            "Invoice Number",
            "Billing Date",
            "Total Cost",
            "Amount Tendered",
            "Customer ID",
            "Employee ID"
    };
    private JTable table;
    private DefaultTableModel model;

    public InvoiceScreen() {
        super("Invoices");
        buttonPanel.setVisible(false);

        initializeComponents();
        setupListeners();
        setContentView();
        addComponentsToPanels();
        addPanelsToWindow();
        getInvoices();
    }

    private void initializeComponents() {
        textArea = new JTextArea(
                "Jan Wholesale & Retail " +
                        "121 Old Hope Road \n" +
                        "Kingston 6, Jamaica  " +
                        "Tel: 993-3454"
        );
        textArea.setFont(new Font("arial", Font.BOLD, 14));
        textArea.setLineWrap(true);
        textArea.setSize(175, 50);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(new Color(0, 100, 205));
        textArea.setLayout(new FlowLayout(FlowLayout.RIGHT));
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        textArea.setWrapStyleWord(true);

        model = new DefaultTableModel(tableHeaders, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //Set to not editable
        table.setAutoCreateRowSorter(true); //Enable sorting by columns
        table.getTableHeader().setOpaque(false);//Remove header background
        table.getTableHeader().setBackground(new Color(224, 224, 224));//Setting new background of table headings
        table.setBackground(Color.white);
        table.setForeground(Color.black);

        viewButton = new JButton("View");
        viewButton.setPreferredSize(new Dimension(120, 30));
        viewButton.setFont(new Font("arial", Font.PLAIN, 15));
        viewButton.setFocusPainted(false);

        searchButton.setAlignmentX(LEFT_ALIGNMENT);
        refreshButton.setAlignmentX(LEFT_ALIGNMENT);

        panTop = new JPanel(new GridBagLayout());
        panTop.setPreferredSize(new Dimension(25, 10));
        panTop.setAlignmentX(Component.CENTER_ALIGNMENT);
        panTop.setBackground(getBackground());

        mainContent = new JPanel(new GridLayout(0, 1, 0, 70));
    }

    public void addComponentsToPanels() {
        gbc.insets = new Insets(30, 20, 30, 20);
        gbc.gridwidth = 1;
        gbc.gridheight = 4;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 3;
        panTop.add(textArea, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panTop.add(viewButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panTop.add(deleteButton, gbc);

        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panTop.add(searchButton, gbc);

        gbc.gridx = 5;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        panTop.add(refreshButton, gbc);
    }

    public void addPanelsToWindow() {
        this.add(panTop);
        this.add(mainContent);
    }

    public void setMainContent(Component content) {
        mainContent.add(content);
    }

    // set main content view
    private void setContentView() {
        setMainContent(new JScrollPane(table));
    }

    // setup actions for buttons
    private void setupListeners() {
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        refreshButton.addActionListener(this);
        viewButton.addActionListener(this);
    }

    private void getInvoices() {
        //Get list of invoices
        Client client = new Client();
        client.sendAction("View Invoices");
        List<Invoice> invoiceList = client.receiveViewInvoiceResponse();
        client.closeConnections();
        System.out.println(invoiceList);

        int count = 0;
        int rowCount = model.getRowCount();
        int counter = 0;

        while (counter < rowCount) {
            model.removeRow(count);
            counter++;
        }

        for (Invoice invoice : invoiceList) {
            System.out.println(invoice);

            model.insertRow(count, new Object[]{
                    invoice.getInvoice_number(),
                    invoice.getBillingDate(),
                    "$" + invoice.getTotalCost(),
                    "$" + invoice.getAmountTendered(),
                    invoice.getCustomerId(),
                    invoice.getEmployeeId()
            });
            count++;
        }
    }

    // remove item at selected row
    private boolean removeItem() {
        boolean isSelected = false;
        if (table.getSelectedRow() != -1) {
            isSelected = true;
            int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Remove this invoice?",
                    "Remove prompt",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (choice == JOptionPane.YES_OPTION) {
                Client client = new Client();
                client.sendAction("Remove Invoice");
                client.sendInvoiceNumber((int) model.getValueAt(table.getSelectedRow(), 0));
                client.receiveResponse();
                client.closeConnections();
            }
        }
        return isSelected;
    }

    //view item at selected row
    private boolean viewItem() {
        boolean isSelected = false;
        if (table.getSelectedRow() != -1) {
            isSelected = true;
            new ViewDialog((int) model.getValueAt(table.getSelectedRow(), 0));
        }
        return isSelected;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(searchButton)) {
            new SearchDialog(model);
        }
        if (e.getSource().equals(deleteButton)) {
            if (!removeItem()) {
                new RemoveDialog();
            }
            getInvoices();
        }
        if (e.getSource().equals(refreshButton)) {
            getInvoices();
        }
        if (e.getSource().equals(viewButton)) {
            if (!viewItem()) {
                new ViewDialog();
            }
        }
    }
}
