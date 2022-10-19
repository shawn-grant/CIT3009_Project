package view;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class InvoiceScreen extends BaseScreen {
    private final String[] tableHeaders = {
        "ID",
        "First Name",
        "Last Name",
        "DOB",
        "Email",
        "Phone",
        "Address",
        "Membership Date",
        "Expiry Date",
    };
    private JTable table;
    private DefaultTableModel model;

    public InvoiceScreen(){
        super("Invoices");

        buttonPanel.setVisible(false);

        initializeComponents();
        // setupListeners();
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
}
