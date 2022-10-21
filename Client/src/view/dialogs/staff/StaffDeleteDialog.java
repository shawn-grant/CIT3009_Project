package view.dialogs.staff;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.Client;

public class StaffDeleteDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

    private JLabel idLabel;
    private JTextField idTextField;
    private JButton saveButton;
    private JPanel panel;
    private final Client client;

  public StaffDeleteDialog(Client client) {

        this.client = client;
        setLayout(new FlowLayout(FlowLayout.TRAILING));
        initializeComponents();
        addComponentsToPanels();
        addPanelsToWindow();
        setWindowProperties();
        registerListeners();
    }

     private void initializeComponents() {
        //Label properties
        idLabel = new JLabel("Employee ID Number:");
        idLabel.setFont(new Font("Aharoni", Font.BOLD, 14));

        //Field properties
        idTextField = new JTextField();
        idTextField.setFont(new Font("Aharoni", Font.BOLD, 14));
        idTextField.setPreferredSize(new Dimension(70, 30));

        //Button properties
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Aharoni", Font.BOLD, 14));

        //Panel properties
        panel = new JPanel();

        //Additional properties
        saveButton.setFocusPainted(false);
    }

	 private void addComponentsToPanels() {
        panel.add(idLabel);
        panel.add(idTextField);
        panel.add(saveButton);

    }

    private void addPanelsToWindow() {
        add(panel);
    }

    private void setWindowProperties() {

        setTitle("Search Employee List");
        setSize(375, 100);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
    }

    private void registerListeners() {
        saveButton.addActionListener(this);
    }


	    private boolean validateFields() {
        return !(idTextField.getText().isEmpty());
    }

    private void resetFields() {
        idTextField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
			if (e.getSource().equals(saveButton)) {
			    if (validateFields()) {

			        client.sendAction("Delete Employee");
			        client.sendEmployeeId(idTextField.getText());
			        resetFields();
			        dispose();

			    } else {
			        JOptionPane.showMessageDialog(this,"One or more fields empty",
			                "Warning",JOptionPane.WARNING_MESSAGE);
			    }
			}
		} catch (HeadlessException e1) {
			e1.printStackTrace();
		}
    }
}
