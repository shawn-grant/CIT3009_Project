package view.screens;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import client.Client;
import models.Product;
import view.components.DatePicker;
import view.components.RoundedBorder;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportScreen extends BaseScreen implements ActionListener {

    private JPanel container, leftPanel, rightPanel;
    private JLabel productLabel;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JTextArea reportTextArea;
    private JComboBox<String> productSelect;
    private DatePicker startDateField;
    private DatePicker endDateField;
    private JButton generateButton, printButton;

    public ReportScreen(){
        super("Generate Reports");

        buttonPanel.setVisible(false);

        initializeComponents();
        addComponentsToPanels();
        setupListeners();
        setContentView();
    }

    private void initializeComponents() {
        Dimension labelSize = new Dimension(250, 20);
        Dimension fieldSize = new Dimension(250, 35);
        Font labelFont = new Font("arial", Font.BOLD, 14);
        Font fieldFont = new Font("arial", Font.PLAIN, 14);

        container = new JPanel(new FlowLayout(FlowLayout.CENTER));
        leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setPreferredSize(new Dimension(300, 450));
        leftPanel.setBorder(new RoundedBorder(8));
        leftPanel.setBackground(Color.WHITE);
        
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(400, 450));
        rightPanel.setBorder(new RoundedBorder(8));
        rightPanel.setBackground(Color.WHITE);

        productLabel = new JLabel("Product");
        productLabel.setFont(labelFont);
        productLabel.setPreferredSize(labelSize);
        
        startDateLabel = new JLabel("Start Date");
        startDateLabel.setFont(labelFont);
        startDateLabel.setPreferredSize(labelSize);

        endDateLabel = new JLabel("End Date");
        endDateLabel.setFont(labelFont);
        endDateLabel.setPreferredSize(labelSize);

        reportTextArea = new JTextArea("Your Report will show here...");
        reportTextArea.setEditable(false);
        reportTextArea.setPreferredSize(new Dimension(390, 400));

        productSelect = new JComboBox<>(getProducts());
        productSelect.setBorder(new RoundedBorder(8));
        productSelect.setPreferredSize(fieldSize);
        productSelect.setFont(fieldFont);
        
        startDateField = new DatePicker();
        endDateField = new DatePicker();

        generateButton = new JButton("GENERATE REPORT");
        generateButton.setPreferredSize(new Dimension(250, 40));
        generateButton.setFont(new Font("arial", Font.BOLD, 14));
        generateButton.setForeground(Color.WHITE);
        generateButton.setBackground(Color.BLUE);
        generateButton.setOpaque(true);
        generateButton.setBorderPainted(false);

        printButton = new JButton("Print");
        printButton.setPreferredSize(new Dimension(250, 40));
        printButton.setFont(new Font("arial", Font.BOLD, 14));
        printButton.setEnabled(false);
    }
    
    private void addComponentsToPanels(){
        leftPanel.add(productLabel);
        leftPanel.add(productSelect);

        leftPanel.add(startDateLabel);
        leftPanel.add(startDateField);

        leftPanel.add(endDateLabel);
        leftPanel.add(endDateField);
        leftPanel.add(Box.createRigidArea(new Dimension(250, 50))); // vertical spacing
        
        leftPanel.add(generateButton);
        leftPanel.add(printButton);

        rightPanel.add(reportTextArea);

        container.add(leftPanel);
        container.add(rightPanel);
    }

    // setup actions for buttons
    private void setupListeners() {
        generateButton.addActionListener(this);
        printButton.addActionListener(this);
    }

    // set main content view
    private void setContentView() {
        setMainContent(container);
    }

    private String[] getProducts(){
        String[] list = {"P0028 - Test"};

        // Client client = new Client();
        // client.sendAction("View Inventory");
        // List<Product> products = client.receiveViewInventoryResponse();
        // client.closeConnections();

        // list = new String[products.size()];

        // for(int i = 0; i < products.size(); i++){
        //     list[i] = products.get(i).getCode() + " - " + products.get(i).getName();
        // }

        return list;
    }

    private void generateReport(){
        // TODO: pull the data and display the report in the textArea

        // set printButton to enabled once a report has been generated
        // printButton.setEnabled(true);
    }

    private void printReport(){
        // Save the text from the textArea to a .txt file
        // trigger system print dialog if possible
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(generateButton)) {
            generateReport();
        }
        if (e.getSource().equals(printButton)) {
            printReport();
        }
    }
}
