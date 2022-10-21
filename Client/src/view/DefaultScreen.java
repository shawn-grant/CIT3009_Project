/**
 * DefaultScreen.java
 * A splash screen shown when the app launches
 * Author (s): Shawn Grant
 */
package view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DefaultScreen extends JPanel {
    private final JLabel jwrIcon;

    DefaultScreen() {
        jwrIcon = new JLabel(new ImageIcon(getClass().getResource("/res/JWR_intro.png")));

        this.setSize(800, 600);
        this.setBackground(new Color(0, 100, 205));
        this.add(jwrIcon);
    }
}
