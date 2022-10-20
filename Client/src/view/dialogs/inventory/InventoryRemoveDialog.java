package view.dialogs.inventory;

import client.Client;
import view.RoundedBorder;

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
public class InventoryRemoveDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JLabel codeLabel;
    private JTextField codeField;
    private JButton confirmButton;

    public InventoryRemoveDialog() {
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
        confirmButton = new JButton("REMOVE");
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
        setTitle("Remove Product");
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(confirmButton)) {
            if (validateFields()) {
                Client client = new Client();
                client.sendAction("Remove Product");
                client.sendProductCode(codeField.getText());
                client.receiveResponse();
                client.closeConnections();
                dispose();
            }
        }
    }
}
