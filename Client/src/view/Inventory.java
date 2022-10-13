package view;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inventory implements ActionListener, TableModelListener {

    private JButton refreshButton, backButton;
    private final String[] TableHead = {"Student ID", "Student Name", "Assignment ID", "Assignment Grade"};
    private JPanel leftPanel, rightPanel;
    private JTable table;
    private DefaultTableModel model;

    public Inventory() {
        initializeComponents();
        addComponentsToPanel();
        registerListeners();
    }

    private void initializeComponents() {
        //Button properties
        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(140,90,100,50);
        refreshButton.setFocusPainted(false);

        backButton = new JButton("Back");
        backButton.setBounds(140,150,100,50);
        backButton.setFocusPainted(false);

        //Table properties
        model = new DefaultTableModel(TableHead, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //Set to not editable
        table.setAutoCreateRowSorter(true); //Enable sorting by columns

        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setSize(new Dimension(350,500));

        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(1,1,5,5));
        rightPanel.setSize(new Dimension(880,500));
    }

    private void addComponentsToPanel() {
        rightPanel.add(new JScrollPane(table));
        leftPanel.add(refreshButton);
        leftPanel.add(backButton);
    }

    private void registerListeners() {
        refreshButton.addActionListener(this);
        model.addTableModelListener(this);
    }

    public JComponent[] getComponent() {
        return new JPanel[]{leftPanel, rightPanel};
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(refreshButton)) {
            /*List<Product> productList = //select();
            int count = 0;
            int rowCount = model.getRowCount();
            int counter = 0;

            while (counter < rowCount) {
                model.removeRow(count);
                counter++;
            }

            for(Product product: productList) {
                System.out.println(product);

                model.insertRow(count, new Object[]{product.getCode(), product.getName(),
                        product.getShortDescription(), product.getLongDescription(),
                        product.getInStock(), product.getUnitPrice()});
                count++;
            }*/
        }
    }

    @Override
    public void tableChanged(TableModelEvent tableModelEvent) {

    }
}
