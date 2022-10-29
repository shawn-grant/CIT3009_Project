/**
 * MainScreen.java
 * A loading screen for when the server is starting
 * Author (s): Malik Heron
 */
package view;

import javax.swing.JWindow;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.util.Objects;

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
        jwrIcon = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/JWR_intro_1.png"))));

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
