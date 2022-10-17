/// INCOMPLETE
package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BaseScreen extends JPanel {
    private static int FULL_WIDTH = 800;

    private JPanel titleBar;
    private JLabel titleLabel;
    protected JButton addButton; // add a new item
    protected JButton updateButton; // update existing item
    protected JButton deleteButton; // delete item
    protected JButton refreshButton; // get all items
    private JPanel buttonPanel;
    private Component mainContent; // where the content is shown, set it child class

    public BaseScreen(String title) {
        initializeComponents(title);
        addComponentsToPanels();
        addPanelsToWindow();
        setProperties();
    }
    
    private void initializeComponents(String title) {
        titleBar = new JPanel(new FlowLayout());
        titleBar.setSize(FULL_WIDTH, 40);
        titleBar.setBackground(Color.GRAY);

        titleLabel = new JLabel(title);
        titleLabel.setSize(FULL_WIDTH, 40);
        titleLabel.setFont(new Font("arial", Font.BOLD, 20));
        
        addButton = new JButton("Add +");
        addButton.setSize(FULL_WIDTH/4, 40);

        updateButton = new JButton("Update");
        updateButton.setSize(FULL_WIDTH/4, 40);
        
        deleteButton = new JButton("Delete");
        deleteButton.setSize(FULL_WIDTH/4, 40);
        
        refreshButton = new JButton("Refresh");
        refreshButton.setSize(FULL_WIDTH/4, 40);

        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setSize(FULL_WIDTH, 80);

        mainContent = new JPanel();
    }
    
    private void addComponentsToPanels() {
        titleBar.add(titleLabel);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
    }
    
    private void addPanelsToWindow() {
        this.add(titleBar);
        this.add(buttonPanel);
        this.add(mainContent);
    }

    private void setProperties() {
        this.setLayout(new GridLayout(3, 1, 0, 0));
        this.setBackground(Color.YELLOW);
        this.setSize(FULL_WIDTH, 600);
    }

    public void setMainContent(Component content) {
        this.remove(mainContent);
        this.add(content);
        this.repaint();
    }

}
