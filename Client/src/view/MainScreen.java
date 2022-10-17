package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class MainScreen extends JFrame implements ActionListener{
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JToggleButton customerButton;
    private JToggleButton staffButton;
    private JToggleButton inventoryButton;
    private JToggleButton checkOutButton;
    private JToggleButton reportButton;
    private JToggleButton exitButton;    

    public MainScreen(){
        initializeComponents();
        addComponentsToPanels();
        addPanelsToWindow();
        setWindowProperties();
    }
    
    private void initializeComponents() {
        // init Buttons
        customerButton = new JToggleButton("Customers", new ImageIcon("./images/customer_icon.png"));
        staffButton = new JToggleButton("Staff", new ImageIcon("./images/staff_icon.png"));
        inventoryButton = new JToggleButton("Products", new ImageIcon("./images/product_icon.png"));
        checkOutButton = new JToggleButton("Checkout", new ImageIcon("./images/checkout_icon.png"));
        reportButton = new JToggleButton("Reports", new ImageIcon("./images/report_icon.png"));
        exitButton = new JToggleButton("Exit", new ImageIcon("./images/exit_icon.png"));

        JToggleButton[] buttons = {customerButton, staffButton, inventoryButton, checkOutButton, reportButton, exitButton};
        ButtonGroup grp = new ButtonGroup();
        grp.add(customerButton);
        grp.add(staffButton);
        grp.add(inventoryButton);
        grp.add(checkOutButton);
        grp.add(reportButton);

        for(JToggleButton btn : buttons) {
            // setup all the buttons properties
            btn.setForeground(Color.WHITE);
            btn.setBackground(null);
            btn.setFocusPainted(false);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setPreferredSize(new Dimension(200, 65));
            btn.setFont(new Font("arial", Font.BOLD, 18));
            btn.setBorderPainted(false);
            btn.addMouseListener(btnHover);
            btn.addActionListener(this);
        }
        
        // Left Panel properties        
        leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setSize(200, 600);
        leftPanel.setBackground(new Color(0, 0, 230));
        
        // Right Panel properties
        rightPanel = new JPanel(new GridLayout(0,1));
        rightPanel.setBounds(200, 0, 800, 600);
    }

    private void addComponentsToPanels() {
        // Add components to panel
        leftPanel.add(customerButton);
        leftPanel.add(staffButton);
        leftPanel.add(inventoryButton);
        leftPanel.add(checkOutButton);
        leftPanel.add(reportButton);
        leftPanel.add(exitButton);

        // Add default/ splash screen
        rightPanel.add(new DefaultScreen());
    }

    private void addPanelsToWindow() {
        this.add(leftPanel);
        this.add(rightPanel);
    }

    private void setWindowProperties() {
        this.setLayout(null);
        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Jan's Wholesale and Retail");
        this.setVisible(true);
    }

    // SWITCH TO THE APPROPRIATE SREEN WHEN A TAB BUTTON IS CLICKED
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == customerButton) {
            rightPanel.removeAll();
            rightPanel.add(new ExampleScreen()); //testing
            rightPanel.repaint();
        }
        else if(e.getSource() == staffButton) {
            rightPanel.removeAll();
            // rightPanel.add(new StaffScreen());
            rightPanel.repaint();
        }
        else if(e.getSource() == inventoryButton) {
            rightPanel.removeAll();
            // rightPanel.add(new InventoryScreen());
            rightPanel.repaint();
        }
        else if(e.getSource() == checkOutButton) {
            rightPanel.removeAll();
            // rightPanel.add(new CheckoutScreen());
            rightPanel.repaint();
        }
        else if(e.getSource() == reportButton) {
            rightPanel.removeAll();
            // rightPanel.add(new ReportScreen());
            rightPanel.repaint();
        }
        else if(e.getSource() == exitButton) {
            int selection = JOptionPane.showConfirmDialog(
                null, 
                "Are you sure ?",
                "Exit Application", 
                JOptionPane.YES_NO_OPTION
            );

            if (selection == JOptionPane.YES_OPTION)
                System.exit(1);
        }
    }

    /// used to create hover effect on buttons
    MouseAdapter btnHover = new MouseAdapter() {
        Color startColor;

        public void mouseEntered(java.awt.event.MouseEvent evt)
        {            
            Component c = evt.getComponent();
            startColor = c.getBackground();    
            c.setBackground(new Color(0, 0, 179));
        }                                      

        public void mouseExited(java.awt.event.MouseEvent evt)
        {                                      
            Component c = evt.getComponent();
            c.setBackground(startColor);
        }
    };
}
