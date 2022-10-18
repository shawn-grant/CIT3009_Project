package view;

import java.awt.event.ActionEvent;
import  java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import client.Client;
import models.Employee;
import view.dialogs.staff.StaffDeleteDialog;
import view.dialogs.staff.StaffInsertDialog;
import view.dialogs.staff.StaffSearchDialog;
import view.dialogs.staff.StaffUpdateDialog;
 

public class StaffScreen extends BaseScreen implements ActionListener{

    private final String[] tableHead = {"Employee ID", "First Name", "Last Name", "D.O.B",
            "Address", "Tel", "Emails", "Employee Type","Department"};
    private JTable table;
    private DefaultTableModel model;
    
    
    public StaffScreen() {
        super("Employee");

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
                employee.getDOB() ,
                employee.getAddress(),
                employee.getTelephone(),
                employee.getEmail(),
                employee.getType(),
                employee.getDepartment()
            });
            count++;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource().equals(addButton)) {
            StaffInsertDialog insertDialog = new StaffInsertDialog();
           // insertDialog.setVisible(true);
            getStaff();
        }
        if (e.getSource().equals(searchButton)) {
            StaffSearchDialog searchDialog = new StaffSearchDialog();
            //searchDialog.setVisible(true);
        }
        if (e.getSource().equals(updateButton)) {
            StaffUpdateDialog updateDialog = new StaffUpdateDialog();
            //updateDialog.setVisible(true);
        }
        if (e.getSource().equals(deleteButton)) {
            StaffDeleteDialog deleteDialog = new StaffDeleteDialog();
            //deleteDialog.setVisible(true);
        }
        if (e.getSource().equals(refreshButton)) {
            getStaff();
        }
        
    }

    
}