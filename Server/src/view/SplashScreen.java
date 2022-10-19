/**
 * MainScreen.java
 * A loading screen for when the server is starting
 * Author (s): Malik Heron
 */
package view;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {

    private JProgressBar progressBar;
    private JPanel panel;
    private JLabel jwrIcon;

    public SplashScreen() {
        initializeComponents();
        addComponentsToPanels();
        addPanelsToWindow();
        setWindowProperties();
    }

    private void initializeComponents() {
        // progress bar properties
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        // Label properties
        jwrIcon = new JLabel(new ImageIcon(getClass().getResource("/res/JWR_intro.png")));

        // Panel properties
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setSize(600, 415);
        panel.setBackground(new Color(0, 100, 205));
    }

    private void addComponentsToPanels() {
        panel.add(jwrIcon);
        panel.add(progressBar);
    }

    private void addPanelsToWindow() {
        add(panel);
    }

    private void setWindowProperties() {
        setSize(600, 415);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
