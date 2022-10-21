package view.dialogs.inventory;

import client.Client;
import models.Product;
import view.components.RoundedBorder;

import javax.swing.table.DefaultTableModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Malik Heron
 */
public class SearchDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private final DefaultTableModel model;
    private JLabel codeLabel;
    private JTextField codeField;
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
        codeLabel = new JLabel("Product Code");
        codeLabel.setFont(new Font("arial", Font.BOLD, 14));
        codeLabel.setPreferredSize(new Dimension(100, 20));

        //Field properties
        codeField = new JTextField();
        codeField.setFont(new Font("times new roman", Font.PLAIN, 14));
        codeField.setBorder(new RoundedBorder(8));
        codeField.setPreferredSize(new Dimension(90, 35));

        //Button properties
        confirmButton = new JButton("SEARCH");
        confirmButton.setPreferredSize(new Dimension(100, 30));
        confirmButton.setForeground(Color.BLUE);
        confirmButton.setFont(new Font("arial", Font.BOLD, 14));

        //Additional properties
        confirmButton.setFocusPainted(false);
    }

    private void addComponentsToWindow() {
        add(codeLabel);
        add(codeField);
        add(confirmButton);
    }

    private void setWindowProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setTitle("Search Inventory");
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
        return !(codeField.getText().isEmpty());
    }

    private void setProduct(Product product) {
        int count = 0;
        int rowCount = model.getRowCount();
        int counter = 0;

        while (counter < rowCount) {
            model.removeRow(count);
            counter++;
        }

        model.insertRow(count, new Object[]{
                product.getCode(),
                product.getName(),
                product.getShortDescription(),
                product.getLongDescription(),
                product.getItemInStock(),
                product.getUnitPrice()
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(confirmButton)) {
            if (validateFields()) {
                Client client = new Client();
                client.sendAction("Find Product");
                client.sendProductCode(codeField.getText());
                Product product = client.receiveFindProductResponse();
                client.closeConnections();
                if (product != null) {
                    setProduct(product);
                    dispose();
                }
            }
        }
    }
}
