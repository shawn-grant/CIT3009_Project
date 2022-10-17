/// INCOMPLETE
package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BaseScreen extends JPanel {
    private JLabel titleLabel;
    protected JButton addButton; // add a new item
    protected JButton updateButton; // update existing item
    protected JButton deleteButton; // delete item
    protected JButton refreshButton; // get all items
    private JPanel buttonPanel;
    private Component mainContent; // where the content is shown, set it child class

    public BaseScreen(String title){
        this.setLayout(new GridLayout(0, 1));
        this.setSize(800, 600);

        initializeComponents(title);
        addComponentsToPanels();
        // addPanelsToWindow();
        
        this.add(titleLabel);
    }

    private void initializeComponents(String title){
        this.setBackground(Color.YELLOW);

        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("arial", Font.BOLD, 20));
        
        addButton = new JButton("Add +");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainContent = new JPanel();
    }
    
    private void addComponentsToPanels() {
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
    }

    // private void addPanelsToWindow() {
    //     this.add(buttonPanel);
    //     // this.add(mainContent);
    // }

    public void setMainContent(Component content) {
        // this.mainContent = content;
        // this.repaint();
    }

}
