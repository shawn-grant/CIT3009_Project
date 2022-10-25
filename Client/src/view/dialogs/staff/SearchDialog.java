package view.dialogs.staff;

import client.Client;
import models.Employee;
import view.components.RoundedBorder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Malik Heron & Tori Horne
 */
public class SearchDialog extends JDialog implements ActionListener {

    private final DefaultTableModel model;
    private JLabel idLabel;
    private JTextField idField;
    private JButton confirmButton;

    public SearchDialog(DefaultTableModel model) {
        this.model = model;
        initializeComponents();
        addComponentsToWindow();
        registerListeners();
        setWindowProperties();
    }

    private void initializeComponents() {
        //Label properties
        idLabel = new JLabel("Employee ID");
        idLabel.setFont(new Font("arial", Font.BOLD, 14));
        idLabel.setPreferredSize(new Dimension(100, 20));

        //Field properties
        idField = new JTextField();
        idField.setFont(new Font("times new roman", Font.PLAIN, 14));
        idField.setBorder(new RoundedBorder(8));
        idField.setPreferredSize(new Dimension(90, 35));

        //Button properties
        confirmButton = new JButton("SEARCH");
        confirmButton.setPreferredSize(new Dimension(100, 30));
        confirmButton.setForeground(Color.BLUE);
        confirmButton.setFont(new Font("arial", Font.BOLD, 14));

        //Additional properties
        confirmButton.setFocusPainted(false);
    }

    private void addComponentsToWindow() {
        add(idLabel);
        add(idField);
        add(confirmButton);
    }

    private void setWindowProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setTitle("Search Employees");
        setSize(350, 90);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setVisible(true);
    }

    private void registerListeners() {
        confirmButton.addActionListener(this);
    }

    private boolean validateFields() {
        return !(idField.getText().isEmpty());
    }

    private void setEmployee(Employee employee) {
        int count = 0;
        int rowCount = model.getRowCount();
        int counter = 0;

        while (counter < rowCount) {
            model.removeRow(count);
            counter++;
        }

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(confirmButton)) {
            if (validateFields()) {
                //Request to find an employee record
                Client client = new Client();
                client.sendAction("Find Employee");
                client.sendEmployeeId(idField.getText());
                Employee employee = client.receiveFindEmployeeResponse();
                client.closeConnections();
                if (employee != null) {
                    setEmployee(employee);
                    dispose();
                }
            }
        }
    }
}
