package view.screens;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvoiceScreen extends BaseScreen implements ActionListener {
    private final String[] tableHeaders = {
        "Invoice Number",
        "Billing Date",
        "Item Name",
        "Quantity",
        "Total Billing",
        "Employee ID",
        "Customer ID"
    };
    private JTable table;
    private DefaultTableModel model;

    public InvoiceScreen(){
        super("Invoices");
        buttonPanel.setVisible(false);

        initializeComponents();
        setupListeners();
        setContentView();
        // getData();
    }
    
    private void initializeComponents() {
        model = new DefaultTableModel(tableHeaders, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //Set to not editable
        table.setAutoCreateRowSorter(true); //Enable sorting by columns
    }

    // set main content view
    private void setContentView() {
        setMainContent(new JScrollPane(table));
    }

    // setup actions for buttons
    private void setupListeners() {
        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        refreshButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(addButton)) {
            //new InsertDialog();
        }
        if (e.getSource().equals(searchButton)) {
            //new SearchDialog(model);
        }
        if (e.getSource().equals(updateButton)) {
            //new UpdateDialog();
        }
        if (e.getSource().equals(deleteButton)) {
           // new RemoveDialog();
        }
        if (e.getSource().equals(refreshButton)) {
        }
    }
}
