package view.dialogs.staff;

import client.Client;
import view.components.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Malik Heron & Tori Horne
 */
public class RemoveDialog extends JDialog implements ActionListener {

    private JLabel idLabel;
    private JTextField idField;
    private JButton confirmButton;

    public RemoveDialog() {
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
        confirmButton = new JButton("REMOVE");
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
        setTitle("Remove Employee");
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(confirmButton)) {
            if (validateFields()) {
                Client client = new Client();
                client.sendAction("Remove Employee");
                client.sendEmployeeId(idField.getText());
                client.receiveResponse();
                client.closeConnections();
                dispose();
            }
        }
    }
}
