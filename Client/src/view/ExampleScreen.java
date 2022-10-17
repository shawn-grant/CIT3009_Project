package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class ExampleScreen extends BaseScreen implements ActionListener {

    // JPanel or table, however you want to display the data
    private JPanel mainContent;

    public ExampleScreen() {
        super("Example Screen");

        initializeComponents();
        setupListeners();
        setContentView();
    }

    private void initializeComponents(){
        mainContent = new JPanel();
    }

    /// set the main content
    /// typically a table or list of the data
    private void setContentView(){
        this.setMainContent(mainContent);
    }

    // setup actions for buttons
    private void setupListeners(){
        this.addButton.addActionListener(this);
        this.updateButton.addActionListener(this);
        this.deleteButton.addActionListener(this);
        this.refreshButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // setup the buttons to display the appropriate dialog
        // and update the right database tables
    }
    
}
