package view.screens;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.Client;
import models.Product;
import view.components.RoundedBorder;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ReportScreen extends BaseScreen implements ActionListener {

    private JPanel container, leftPanel, rightPanel;
    private JLabel productLabel;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JTextArea reportTextArea;
    private JComboBox<String> productSelect;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton generateButton;

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
        leftPanel = new JPanel();
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

        reportTextArea = new JTextArea("Report will show here");

        productSelect = new JComboBox<>(getProducts());
        productSelect.setBorder(new RoundedBorder(8));
        productSelect.setPreferredSize(fieldSize);
        productSelect.setFont(fieldFont);
        
        startDateField = new JTextField();
        startDateField.setBorder(new RoundedBorder(8));
        startDateField.setPreferredSize(fieldSize);
        startDateField.setFont(fieldFont);

        endDateField = new JTextField();
        endDateField.setBorder(new RoundedBorder(8));
        endDateField.setPreferredSize(fieldSize);
        endDateField.setFont(fieldFont);

        generateButton = new JButton("GENERATE REPORT");
        generateButton.setPreferredSize(new Dimension(250, 40));
        generateButton.setFont(new Font("arial", Font.BOLD, 14));
        generateButton.setForeground(Color.BLUE);
        generateButton.setBackground(Color.BLUE);
    }
    
    private void addComponentsToPanels(){
        leftPanel.add(productLabel);
        leftPanel.add(productSelect);

        leftPanel.add(startDateLabel);
        leftPanel.add(startDateField);

        leftPanel.add(endDateLabel);
        leftPanel.add(endDateField);
        leftPanel.add(generateButton);

        rightPanel.add(reportTextArea);

        container.add(leftPanel);
        container.add(rightPanel);
    }

    // setup actions for buttons
    private void setupListeners() {
        generateButton.addActionListener(this);
    }

    // set main content view
    private void setContentView() {
        setMainContent(container);
    }

    private String[] getProducts(){
        List<String> list = new ArrayList<String>();
        list.add("select");

        Client client = new Client();
        client.sendAction("View Inventory");
        List<Product> products = client.receiveViewInventoryResponse();
        client.closeConnections();

        for(Product prod : products){
            list.add(prod.getCode() + " - " + prod.getName());
        }

        return (String[]) list.toArray();
    }

    private void generateReport(){
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(generateButton)) {

        }
    }
}
