/**
 * BaseScreen.java
 * To be inherited by other screens, contains a title & buttons for CRUD operations
 * Author (s): Shawn Grant
 */
package view;

import javax.swing.*;
import java.awt.*;

public class BaseScreen extends JPanel {

    private final Color headerColor = new Color(27, 73, 142);
    protected JButton addButton; // add a new item
    protected JButton updateButton; // update existing item
    protected JButton deleteButton; // delete item
    protected JButton refreshButton; // get all items
    protected JButton searchButton; // find an item
    protected JPanel buttonPanel;
    private JLabel titleLabel;
    private JPanel mainContent; // where the content is shown, set it child class

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
        addButton = new JButton("Add", new ImageIcon(getClass().getResource("/res/add_icon.png")));
        updateButton = new JButton("Update", new ImageIcon(getClass().getResource("/res/update_icon.png")));
        deleteButton = new JButton("Delete", new ImageIcon(getClass().getResource("/res/delete_icon.png")));
        searchButton = new JButton("Search", new ImageIcon(getClass().getResource("/res/search_icon.png")));
        refreshButton = new JButton("Refresh", new ImageIcon(getClass().getResource("/res/reload_icon.png")));

        // Set button size
        addButton.setPreferredSize(new Dimension(120, 30));
        updateButton.setPreferredSize(new Dimension(120, 30));
        deleteButton.setPreferredSize(new Dimension(120, 30));
        searchButton.setPreferredSize(new Dimension(120, 30));
        refreshButton.setPreferredSize(new Dimension(120, 30));

        // Set button font
        addButton.setFont(new Font("arial", Font.PLAIN, 15));
        updateButton.setFont(new Font("arial", Font.PLAIN, 15));
        deleteButton.setFont(new Font("arial", Font.PLAIN, 15));
        searchButton.setFont(new Font("arial", Font.PLAIN, 15));
        refreshButton.setFont(new Font("arial", Font.PLAIN, 15));

        // Set focus paint
        addButton.setFocusPainted(false);
        updateButton.setFocusPainted(false);
        deleteButton.setFocusPainted(false);
        searchButton.setFocusPainted(false);
        refreshButton.setFocusPainted(false);

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
