/**
 * BaseScreen.java
 * To be inherited by other screens, contains a title & buttons for CRUD operations
 * Author (s): Shawn Grant
*/
package view;

import javax.swing.*;
import java.awt.*;

public class BaseScreen extends JPanel {

    protected JButton addButton; // add a new item
    protected JButton updateButton; // update existing item
    protected JButton deleteButton; // delete item
    protected JButton refreshButton; // get all items
    protected JButton searchButton; // find an item
    private JLabel titleLabel;
    private JPanel buttonPanel;
    private JPanel mainContent; // where the content is shown, set it child class
    private Color headerColor = new Color(27, 73, 142);

    public BaseScreen(String title) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setBackground(headerColor);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setSize(800, 600);

        initializeComponents(title);
        addComponentsToPanels();
        addPanelsToWindow();
    }

    private void initializeComponents(String title) {
        // titleLabel properties
        titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // initialize buttons
        addButton = new JButton("Add +");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        searchButton = new JButton("Search");
        refreshButton = new JButton("Refresh");

        // Set button size
        addButton.setPreferredSize(new Dimension(100, 30));
        updateButton.setPreferredSize(new Dimension(100, 30));
        deleteButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setPreferredSize(new Dimension(100, 30));
        refreshButton.setPreferredSize(new Dimension(100, 30));

        // Set button font
        addButton.setFont(new Font("arial", Font.PLAIN, 15));
        updateButton.setFont(new Font("arial", Font.PLAIN, 15));
        deleteButton.setFont(new Font("arial", Font.PLAIN, 15));
        searchButton.setFont(new Font("arial", Font.PLAIN, 15));
        refreshButton.setFont(new Font("arial", Font.PLAIN, 15));

        // JPanel properties
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mainContent = new JPanel(new GridLayout(0, 1, 0, 0));

        buttonPanel.setBackground(headerColor);
    }

    private void addComponentsToPanels() {
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(refreshButton);
    }
    
    private void addPanelsToWindow() {
        this.add(Box.createRigidArea(new Dimension(0, 20)));// vertical spacing
        this.add(titleLabel);
        this.add(Box.createRigidArea(new Dimension(0, 20)));// vertical spacing
        this.add(buttonPanel);
        this.add(mainContent);
    }

    public void setMainContent(Component content) {
        mainContent.add(content);
    }
}
