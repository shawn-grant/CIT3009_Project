package view.screens;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * A tabbed layout to switch between different sections of the app
 * @author Shawn Grant
 * @author Malik Heron
 */
public class MainScreen extends JFrame implements ActionListener {

    private JPanel leftPanel;
    private JPanel rightPanel;
    private JToggleButton customerButton;
    private JToggleButton staffButton;
    private JToggleButton inventoryButton;
    private JToggleButton checkOutButton;
    private JToggleButton invoiceButton;
    private JToggleButton reportButton;
    private JToggleButton exitButton;

    public MainScreen() {
        super("Jan's Wholesale and Retail Management System");

        initializeComponents();
        addComponentsToPanels();
        addPanelsToWindow();
        setWindowProperties();
    }

    private void initializeComponents() {
        // initialize toggleButtons
        customerButton = new JToggleButton(" Customers",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/customer_icon.png"))));
        staffButton = new JToggleButton(" Staff",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/staff_icon.png"))));
        inventoryButton = new JToggleButton(" Products",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/product_icon.png"))));
        checkOutButton = new JToggleButton(" Checkout",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/checkout_icon.png"))));
        invoiceButton = new JToggleButton(" Invoices",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/invoice_icon.png"))));
        reportButton = new JToggleButton(" Reports",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/report_icon.png"))));
        exitButton = new JToggleButton(" Exit",
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/exit_icon.png"))));

        // used to create hover effect on toggleButtons
        MouseAdapter btnHover = new MouseAdapter() {
            Color startColor;

            public void mouseEntered(MouseEvent evt) {
                Component c = evt.getComponent();
                startColor = c.getBackground();
                c.setBackground(new Color(0, 0, 179));
            }

            public void mouseExited(MouseEvent evt) {
                Component c = evt.getComponent();
                c.setBackground(startColor);
            }
        };

        JToggleButton[] toggleButtons = {
                customerButton,
                staffButton,
                inventoryButton,
                checkOutButton,
                invoiceButton,
                reportButton,
                exitButton
        };
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(customerButton);
        buttonGroup.add(staffButton);
        buttonGroup.add(inventoryButton);
        buttonGroup.add(checkOutButton);
        buttonGroup.add(invoiceButton);
        buttonGroup.add(reportButton);

        for (JToggleButton button : toggleButtons) {
            // setup all the toggleButtons properties
            button.setForeground(Color.WHITE);
            button.setBackground(null);
            button.setFocusPainted(false);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setPreferredSize(new Dimension(200, 65));
            button.setFont(new Font("arial", Font.BOLD, 18));
            button.setBorderPainted(false);
            button.addMouseListener(btnHover);
            button.addActionListener(this);
        }

        // Left Panel properties
        leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setSize(200, 600);
        leftPanel.setBackground(new Color(0, 0, 230));

        // Right Panel properties
        rightPanel = new JPanel(new GridLayout(1, 1));
        rightPanel.setBounds(200, 0, 1100, 600);
    }

    private void addComponentsToPanels() {
        // Add components to panel
        leftPanel.add(customerButton);
        leftPanel.add(staffButton);
        leftPanel.add(inventoryButton);
        leftPanel.add(checkOutButton);
        leftPanel.add(invoiceButton);
        leftPanel.add(reportButton);
        leftPanel.add(exitButton);

        // Add default/splash screen
        rightPanel.add(new DefaultScreen());
    }

    private void addPanelsToWindow() {
        add(leftPanel);
        add(rightPanel);
    }

    private void setWindowProperties() {
        setLayout(null);
        setSize(1300, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/icon.png"))).getImage());
        setVisible(true);
    }

    // SWITCH TO THE APPROPRIATE SCREEN WHEN A TAB BUTTON IS CLICKED
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(customerButton)) {
            rightPanel.removeAll();
            rightPanel.add(new CustomerScreen());
            rightPanel.repaint();
            rightPanel.revalidate();
        }
        if (e.getSource().equals(staffButton)) {
            rightPanel.removeAll();
            rightPanel.add(new StaffScreen());
            rightPanel.repaint();
            rightPanel.revalidate();
        }
        if (e.getSource().equals(inventoryButton)) {
            rightPanel.removeAll();
            rightPanel.add(new InventoryScreen());
            rightPanel.repaint();
            rightPanel.revalidate();
        }
        if (e.getSource().equals(checkOutButton)) {
            rightPanel.removeAll();
            rightPanel.add(new CheckoutScreen());
            rightPanel.repaint();
            rightPanel.revalidate();
        }
        if (e.getSource().equals(invoiceButton)) {
            rightPanel.removeAll();
            rightPanel.add(new InvoiceScreen());
            rightPanel.repaint();
            rightPanel.revalidate();
        }
        if (e.getSource().equals(reportButton)) {
            rightPanel.removeAll();
            rightPanel.add(new ReportScreen());
            rightPanel.repaint();
            rightPanel.revalidate();
        }
        if (e.getSource().equals(exitButton)) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            int selection = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure ?",
                    "Exit Application",
                    JOptionPane.YES_NO_OPTION
            );

            if (selection == JOptionPane.YES_OPTION)
                System.exit(0);
        }
    }
}
