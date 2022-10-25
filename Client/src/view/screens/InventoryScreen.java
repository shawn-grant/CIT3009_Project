package view.screens;

import client.Client;
import models.Product;
import view.dialogs.inventory.InsertDialog;
import view.dialogs.inventory.RemoveDialog;
import view.dialogs.inventory.SearchDialog;
import view.dialogs.inventory.UpdateDialog;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Malik Heron
 */
public class InventoryScreen extends BaseScreen implements ActionListener {

    private final String[] tableHeaders = {
            "Product Code",
            "Product Name",
            "Short Description",
            "Long Description",
            "Items in Stock",
            "Unit Price"
    };
    private JTable table;
    private DefaultTableModel model; 

    public InventoryScreen() {
        super("Inventory");

        initializeComponents();
        setupListeners();
        setContentView();
        getInventory();
    }

    private void initializeComponents() {
        //Table properties
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

    // retrieve all product information
    private void getInventory() {
        Client client = new Client();
        client.sendAction("View Products");
        List<Product> productList = client.receiveViewProductsResponse();
        client.closeConnections();

        int count = 0;
        int rowCount = model.getRowCount();
        int counter = 0;

        while (counter < rowCount) {
            model.removeRow(count);
            counter++;
        }

        for (Product product : productList) {
            System.out.println(product);

            model.insertRow(count, new Object[]{
                    product.getCode(),
                    product.getName(),
                    product.getShortDescription(),
                    product.getLongDescription(),
                    product.getItemInStock(),
                    product.getUnitPrice()
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
                    "Remove this product?",
                    "Remove prompt",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (choice == JOptionPane.YES_OPTION) {
                Client client = new Client();
                client.sendAction("Remove Product");
                client.sendProductCode((String) model.getValueAt(table.getSelectedRow(), 0));
                client.receiveResponse();
                client.closeConnections();
            }
        }
        return isSelected;
    }

    // update item at selected row
    private boolean updateItem() {
        boolean isSelected = false;
        //auto populate if a row is selected
        if (table.getSelectedRow() != -1) {
            isSelected = true;
            Product product = new Product(
                    model.getValueAt(table.getSelectedRow(), 0).toString(),
                    model.getValueAt(table.getSelectedRow(), 1).toString(),
                    model.getValueAt(table.getSelectedRow(), 2).toString(),
                    model.getValueAt(table.getSelectedRow(), 3).toString(),
                    Integer.parseInt(model.getValueAt(table.getSelectedRow(), 4).toString()),
                    Float.parseFloat(model.getValueAt(table.getSelectedRow(), 5).toString() + "f")
            );

            // primary constructor to take a product
            new UpdateDialog(product);
        }
        return isSelected;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(addButton)) {
            new InsertDialog();
            getInventory();
        }
        if (e.getSource().equals(searchButton)) {
            new SearchDialog(model);
        }
        if (e.getSource().equals(updateButton)) {
            if (!updateItem()) {
                new UpdateDialog();
            }
            getInventory();
        }
        if (e.getSource().equals(deleteButton)) {
            if (!removeItem()) {
                new RemoveDialog();
            }
            getInventory();
        }
        if (e.getSource().equals(refreshButton)) {
            getInventory();
        }
    }
}
