package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import client.Client;
import models.Employee;
import view.dialogs.staff.RemoveDialog;
import view.dialogs.staff.InsertDialog;
import view.dialogs.staff.SearchDialog;
import view.dialogs.staff.UpdateDialog;

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

    // remove item at selected row
    private boolean removeItem() {
        boolean isSelected = false;
        if (table.getSelectedRow() != -1) {
            isSelected = true;
            int choice = JOptionPane.showConfirmDialog(
                    null,
                    "Remove this employee?",
                    "Remove prompt",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (choice == JOptionPane.YES_OPTION) {
                Client client = new Client();
                client.sendAction("Remove Employee");
                client.sendEmployeeId((String) model.getValueAt(table.getSelectedRow(), 0));
                client.receiveResponse();
                client.closeConnections();
            }
        }
        return isSelected;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(addButton)) {
            new InsertDialog();
            getStaff();
        }
        if (e.getSource().equals(updateButton)) {
            new UpdateDialog();
            getStaff();
        }
        if (e.getSource().equals(searchButton)) {
            new SearchDialog(model);
        }
        if (e.getSource().equals(deleteButton)) {
            if (!removeItem()) {
                new RemoveDialog();
            }
            getStaff();
        }
        if (e.getSource().equals(refreshButton)) {
            getStaff();
        }
    }
}
