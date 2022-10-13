package ui.view;

import javax.swing.JFrame;
import java.awt.*;

public class TestFrame extends JFrame {
    // use to test views
    public TestFrame(){
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        this.setSize(800, 600);
        this.setLocationRelativeTo (null);
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        
        this.add(new AddCustomerScreen());
        this.setVisible(true);
    }
}
