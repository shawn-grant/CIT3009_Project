package view.screens;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.util.Objects;

/**
 * A splash screen shown when the app launches
 * @author Shawn Grant
 */
public class DefaultScreen extends JPanel {

    public DefaultScreen() {
        JLabel jwrIcon = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/JWR_intro_1.png"))));

        this.setSize(800, 600);
        this.setBackground(new Color(0, 100, 205));
        this.add(jwrIcon);
    }
}
