/*package view.screens;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvoiceScreen extends BaseScreen implements ActionListener {
    private final String[] tableHeaders = {
            "Invoice Number",
            "Billing Date",
            "Item Name",
            "Quantity",
            "Total Billing",
            "Employee ID",
            "Customer ID"
    };
    private JTable table;
    private DefaultTableModel model;

    public InvoiceScreen() {
        super("Invoices");
        buttonPanel.setVisible(false);

        initializeComponents();
        setupListeners();
        setContentView();
        // getData();
    }

    private void initializeComponents() {
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

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(addButton)) {
            //new InsertDialog();
        }
        if (e.getSource().equals(searchButton)) {
            //new SearchDialog(model);
        }
        if (e.getSource().equals(updateButton)) {
            //new UpdateDialog();
        }
        if (e.getSource().equals(deleteButton)) {
            // new RemoveDialog();
        }
        if (e.getSource().equals(refreshButton)) {
        }
    }
}*/


package view.screens;

//import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

//import view.components.RoundedBorder;
import view.dialogs.invoice.SearchDialog;

//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvoiceScreen extends BaseScreen implements ActionListener {

  
	private static final long serialVersionUID = 1L;
	private final GridBagConstraints gbc = new GridBagConstraints();
	private JTextArea textArea;
	private JButton viewButton;
	private JPanel bottomPanel,mainContent, panTop;
	
	private final String[] tableHeaders = {
        "Invoice Number",
        "Billing Date",
        "Item Name",
        "Total Quantity",
        "Total Cost",
        "Employee ID",
        "Customer ID"
    };
    private JTable table;
    private DefaultTableModel model;

    public InvoiceScreen() {
        super("Invoices");
        buttonPanel.setVisible(false);

        initializeComponents();
        setupListeners();
        setContentView();
        addComponentsToPanels();
        addPanelsToWindow();
        setUpListeners();
        // getData();
    }

    private void initializeComponents() {
    	textArea = new JTextArea(
    			"Jan Wholesale & Retail " +
    			"121 Old Hope Road \n"+
    			"Kingston 6, Jamaica  " +
    			"Tel: 993-3454"
    	);
    	textArea.setFont(new Font("arial", Font.BOLD, 14));
    	textArea.setLineWrap(true);
    	textArea.setSize(175, 50);
    	textArea.setForeground(Color.WHITE);
    	textArea.setBackground(new Color(0, 100, 205));
    	textArea.setLayout(new FlowLayout(FlowLayout.RIGHT));
    	textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
    	textArea.setWrapStyleWord(true);
	
        model = new DefaultTableModel(tableHeaders, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null); //Set to not editable
        table.setAutoCreateRowSorter(true); //Enable sorting by columns
        
        viewButton= new JButton("View");
        viewButton.setSize(25, 20);
        viewButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        viewButton.setFont(new Font("arial", Font.PLAIN, 15));
        viewButton.setFocusPainted(false);
      //viewButton.setBorder(new RoundedBorder(8));
        //viewButton.setPreferredSize(new Dimension(25, 25));
        
        searchButton.setAlignmentX(LEFT_ALIGNMENT);
        refreshButton.setAlignmentX(LEFT_ALIGNMENT);
        
       /* buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setPreferredSize(new Dimension(0,20));
        buttonPanel.setBackground(Color.GREEN);
        */
        panTop= new JPanel(new GridBagLayout());
        panTop.setPreferredSize(new Dimension(35,20));
        panTop.setAlignmentX(Component.CENTER_ALIGNMENT);
        panTop.setBackground(Color.GREEN);
        
        //buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mainContent = new JPanel(new GridLayout(0, 1, 0, 70));
        
        bottomPanel = new JPanel(new GridLayout(3,3,1,0));
        bottomPanel.setBackground(Color.red);
    }

    public void addComponentsToPanels() {
    	gbc.insets = new Insets(30, 20, 30, 20);
    	gbc.gridwidth = 1;
    	gbc.gridheight = 4;
    	gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
    	gbc.gridx = 1;
        gbc.gridy = 3;
        panTop.add(textArea, gbc);
        
        gbc.gridwidth = 1;
    	gbc.gridheight = 3;
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panTop.add(searchButton, gbc);

        gbc.gridx = 4;
        gbc.gridy = 3;
        panTop.add(refreshButton, gbc);
        
    	bottomPanel.add(viewButton);
    }
    
    public void addPanelsToWindow(){
    	//this.add(Box.createRigidArea(new Dimension(0, 20)));
    	this.add(panTop);
    	//this.add(buttonPanel);
    	this.add(mainContent);
    	this.add(bottomPanel);	
    }
    public void setMainContent(Component content) {
        mainContent.add(content);
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
    private void setUpListeners() {
    	
    	viewButton.addActionListener(this);
    }
    
    @Override
    
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(addButton)) {
        	//addButton.setVisible(false);
            //new InsertDialog();
        }
        if (e.getSource().equals(searchButton)) {
            new SearchDialog(model);
        }
        if (e.getSource().equals(updateButton)) {
        	//updateButton.setVisible(false);
        	//new UpdateDialog();
        }
        if (e.getSource().equals(deleteButton)) {
        	//deleteButton.setVisible(false);
        }
        if (e.getSource().equals(refreshButton)) {
        }
        if (e.getSource().equals(viewButton)) {
        	//new ViewDialog();
        }
        
        
    }
    
}
