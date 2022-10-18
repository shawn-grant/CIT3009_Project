package view;

import client.Client;
import models.Employee;
import view.dialogs.staff.StaffDeleteDialog;
import view.dialogs.staff.StaffInsertDialog;
import view.dialogs.staff.StaffSearchDialog;
import view.dialogs.staff.StaffUpdateDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class StaffScreen extends BaseScreen implements ActionListener {

    private final String[] tableHead = {"ID", "First Name", "Last Name", "D.O.B",
            "Address", "Telephone", "Email", "Employee Type", "Department"};
    private JTable table;
    private DefaultTableModel model;


    public StaffScreen() {
        super("Employees");

        initializeComponents();
        setupListeners();
        setContentView();
        getStaff();
    }

    private void initializeComponents() {

        //Table properties
        model = new DefaultTableModel(tableHead, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //Set to not editable
        table.setAutoCreateRowSorter(true); //Enable sorting by columns 
    }

    private void setupListeners() {
        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        refreshButton.addActionListener(this);
    }

    //set main content view
    private void setContentView() {
        setMainContent(new JScrollPane(table));
    }

    private void getStaff() {

        try{
            Client client = new Client();
        client.sendAction("View Staff");
        List<Employee> empList = client.receiveViewEmployeeResponse();
        client.closeConnections();

			int count = 0;
			int rowCount = model.getRowCount();
			int counter = 0;

			while (counter < rowCount) {
			    model.removeRow(count);
			    counter++;
			}

			for (Employee employee : empList) {
			    System.out.println(employee);

            model.insertRow(count, new Object[]{
                    employee.getId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getDOB(),
                    employee.getAddress(),
                    employee.getTelephone(),
                    employee.getEmail(),
                    employee.getType(),
                    employee.getDepartment()
            });
            count++;
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(addButton)) {
            StaffInsertDialog insertDialog = new StaffInsertDialog();
             //insertDialog.setVisible(true);
            getStaff();
        }
        if (e.getSource().equals(updateButton)) {
            StaffUpdateDialog updateDialog = new StaffUpdateDialog();
            updateDialog.setVisible(true);
            getStaff();
        }
        if (e.getSource().equals(searchButton)) {
            StaffSearchDialog searchDialog = new  StaffSearchDialog(null);
            searchDialog.setVisible(true);
        }
        if (e.getSource().equals(deleteButton)) {
            StaffDeleteDialog deleteDialog = new StaffDeleteDialog(null);
            deleteDialog.setVisible(true);
            getStaff();
        }
        if (e.getSource().equals(refreshButton)) {
            getStaff();
        }
    }
}
