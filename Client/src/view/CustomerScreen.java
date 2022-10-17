/**
 * CustomerScreen.java
 * View for editing and displaying Customer info
 * Author (s): Shawn Grant
*/
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import view.dialogs.customer.CustomerInsertDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public CustomerScreen() {
        super("Customers");

        initComponents();
        setupListeners();
        setContentView();
        getData();
    }

    private void initComponents() {
        model = new DefaultTableModel(tableHeaders, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //Set to not editable
        table.setAutoCreateRowSorter(true); //Enable sorting by columns
    }

    // setup actions for buttons
    private void setupListeners() {
        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        refreshButton.addActionListener(this);
    }

    // set main content view
    private void setContentView() {
        setMainContent(new JScrollPane(table));
    }

    private void getData() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.addButton) {
            new CustomerInsertDialog();
        }
        if (e.getSource() == this.updateButton) {

        }
        if (e.getSource() == this.searchButton) {

        }
        if (e.getSource() == this.deleteButton) {

        }
        if (e.getSource() == this.refreshButton) {
            getData();
        }
    }
}
