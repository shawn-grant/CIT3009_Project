package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CustomerScreen extends BaseScreen implements ActionListener {
    private final String[] tableHeaders = {
        "ID", 
        "First Name", 
        "Last Name",
        "Email",
        "Phone",
        "DOB",
        "Address",
        "Signed Up",
    };
    private JTable table;
    private DefaultTableModel model;

    public CustomerScreen(){
        super("Customers");

        initComponents();
        setupListeners();
        setContentView();
        getData();
    }
    
    private void initComponents(){
        model = new DefaultTableModel(tableHeaders, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //Set to not editable
        table.setAutoCreateRowSorter(true); //Enable sorting by columns
    }
    
    // setup actions for buttons
    private void setupListeners() {
        this.addButton.addActionListener(this);
        this.updateButton.addActionListener(this);
        this.deleteButton.addActionListener(this);
        this.searchButton.addActionListener(this);
        this.refreshButton.addActionListener(this);
    }

    // set main content view
    private void setContentView() {
        this.setMainContent(new JScrollPane(table));
    }

    private void getData(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.addButton) {

        }
        else if (e.getSource() == this.updateButton) {

        }
        else if (e.getSource() == this.searchButton) {

        }
        else if (e.getSource() == this.deleteButton) {

        }
        else if (e.getSource() == this.refreshButton) {
            getData();
        }
    }
}
