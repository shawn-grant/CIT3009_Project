package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddCustomerScreen extends JPanel implements ActionListener {
    private JLabel nameLabel;
    private JTextField nameField;
    // private CustomerController controller;

    public AddCustomerScreen(){
        // this.controller = _controller;
        // super(new FlowLayout(FlowLayout.LEADING, 10, 10));
        super(new GridLayout(4, 1, 20, 10));

        initComponents();
        addComponentsToPanel();
    }

    
    private void initComponents(){
        ImageIcon icon = new ImageIcon("./images/personal.png");
        nameLabel = new JLabel("NAME:", icon, JLabel.LEADING);
        nameField = new JTextField(20);
        nameField.setBorder(new RoundedBorder(8));
    }
    
    private void addComponentsToPanel() {
        this.add(nameLabel);
        this.add(nameField);
    }
    
    public JPanel getComponent(){
        return this;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
}
